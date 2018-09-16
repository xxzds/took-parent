package com.tooklili.service.biz.impl.tooklili;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.response.TbkCouponGetResponse.MapData;
import com.taobao.api.response.TbkDgItemCouponGetResponse.TbkCoupon;
import com.tooklili.dao.es.EsItemRepository;
import com.tooklili.dao.es.intf.ItemRepository;
import com.tooklili.model.taobao.AlimamaItem;
import com.tooklili.model.tooklili.Item;
import com.tooklili.service.biz.intf.taobao.TbkService;
import com.tooklili.service.biz.intf.tooklili.ItemOperService;
import com.tooklili.util.Arith;
import com.tooklili.util.DateUtil;
import com.tooklili.util.result.BaseResult;
import com.tooklili.util.result.PlainResult;

/**
 * es 插入或更新商品
 * @author shuai.ding
 * @date 2018年1月31日下午6:06:20
 */
@Service
public class ItemEsOperServiceImpl implements ItemOperService{
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemEsOperServiceImpl.class);
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private EsItemRepository esItemRepository;
	
//	@Autowired
//	private AlimamaService alimamaService;
	
	@Resource
	private TbkService tbkService;

	@Override
	public BaseResult insertOrUpdate(AlimamaItem alimamaItem, Integer itemCateId) throws UnsupportedEncodingException, ParseException {
		BaseResult result = new BaseResult();
		
		//当商品有优惠券时，判断优惠结束时间，如果小于当前时间，直接返回		
		String couponEffectiveEndTimeStr = alimamaItem.getCouponEffectiveEndTime();
		if(StringUtils.isNotEmpty(couponEffectiveEndTimeStr)) {
			Date couponEffectiveEndTime = DateUtil.parseDate(couponEffectiveEndTimeStr, DateUtil.DEFAULT_DAY_STYLE);
			if(couponEffectiveEndTime.getTime() < new Date().getTime()) {
				LOGGER.info("采集的商品{}，优惠活动已结束",alimamaItem);
				return result;
			}
		}
		
		insertOrUpdateAlimamaItemToEs(alimamaItem, itemCateId);
		return result;
	}
	
	public PlainResult<String> insertOrUpdate(List<AlimamaItem> alimamaItems,Integer itemCateId) throws UnsupportedEncodingException, ParseException{
		PlainResult<String> result = new PlainResult<String>();
		
		Integer insertCount = 0;
		Integer updateCount = 0;
		for(AlimamaItem alimamaItem : alimamaItems){
			boolean isUpdate = insertOrUpdateAlimamaItemToEs(alimamaItem, itemCateId);
			if(isUpdate){
				updateCount += 1;
			}else{
				insertCount += 1;
			}
			result.setData("采集商品成功,录入"+insertCount+"个，更新"+updateCount+"个");
		}
		
		
		return result;
	}
	
	/**
	 * 商品更新或插入到es中
	 * @param alimamaItem   阿里妈妈实体
	 * @param itemCateId    分类id
	 * @return  true 更新 false 插入
	 * @throws UnsupportedEncodingException
	 * @throws ParseException
	 */
	private boolean insertOrUpdateAlimamaItemToEs(AlimamaItem alimamaItem, Integer itemCateId) throws UnsupportedEncodingException, ParseException {
		Long numIid = alimamaItem.getAuctionId();
		Item item = itemRepository.queryItemBynumIid(numIid);
				
		Item itemNew = null;
		boolean isUpdate =false;
		if(item != null){
			itemNew = item;
			isUpdate = true;
		}else{   //插入
			itemNew = new Item();			
			itemNew.setId(new Date().getTime());  //暂时使用毫秒数表示主键			
			itemNew.setNumIid(numIid);
			itemNew.setTitle(alimamaItem.getTitle());
			itemNew.setPicUrl(alimamaItem.getPictUrl());
			itemNew.setIntro("");
//			AlimamaItemLink alimamaItemLink =  alimamaService.generatePromoteLink(numIid.toString()).getData();
//			if(alimamaItemLink == null){
//				LOGGER.info("推广链接生成失败");
//				return result.setErrorMessage("推广链接生成失败");
//			}
//			itemNew.setQuanUrl(alimamaItemLink.getCouponLink());
			
			//商品类别
			Integer userType = alimamaItem.getUserType();
			if(userType==0){
				itemNew.setShopType("C");
			}else if(userType==1){
				itemNew.setShopType("B");
			}
		}
		itemNew.setCateId(itemCateId);
		if(StringUtils.isNotEmpty(alimamaItem.getCouponEffectiveStartTime())){
			itemNew.setCouponStartTime(alimamaItem.getCouponEffectiveStartTime());
		}
		if(StringUtils.isNotEmpty(alimamaItem.getCouponEffectiveEndTime())){
			itemNew.setCouponEndTime(alimamaItem.getCouponEffectiveEndTime());
		}
		itemNew.setQuanSurplus(alimamaItem.getCouponTotalCount());
		itemNew.setQuanReceive(alimamaItem.getCouponTotalCount()-alimamaItem.getCouponLeftCount());
		itemNew.setVolume(alimamaItem.getBiz30day().toString());
		itemNew.setAddTime(DateUtil.formatDate(new Date()));
		String zkFinalPrice = alimamaItem.getZkPrice();
		itemNew.setPrice(zkFinalPrice);
		//优惠券
		itemNew.setQuan(alimamaItem.getCouponAmount().toString());
		double couponPrice = Arith.sub(Double.valueOf(zkFinalPrice),Double.valueOf(itemNew.getQuan()));
		itemNew.setCouponPrice(String.valueOf(couponPrice));
			
		itemRepository.save(itemNew);
		LOGGER.info("{}es的商品主键为：{}",isUpdate == true ? "更新":"插入",itemNew.getId());
		return isUpdate;
	}

	@Override
	public BaseResult insertOrUpdate(TbkCoupon tbkCoupon, Integer itemCateId) throws ApiException, ParseException {
		BaseResult result = new BaseResult();
		
		Long numIid = tbkCoupon.getNumIid();
		Item item = itemRepository.queryItemBynumIid(numIid);
		
		Item itemNew = null;
		boolean isUpdate =false;
		if(item != null){
			itemNew = item;
			isUpdate = true;
		}else{  //插入
			itemNew = new Item();
			itemNew.setId(new Date().getTime());  //暂时使用毫秒数表示主键			
			itemNew.setNumIid(numIid);
			itemNew.setTitle(tbkCoupon.getTitle());
			itemNew.setPicUrl(tbkCoupon.getPictUrl());
			itemNew.setQuanUrl(tbkCoupon.getCouponClickUrl());
			
			//商品类别
			Long userType = tbkCoupon.getUserType();
			if(userType==0L){
				itemNew.setShopType("C");
			}else if(userType==1L){
				itemNew.setShopType("B");
			}
		}
		itemNew.setCateId(itemCateId);	
		itemNew.setCouponStartTime(tbkCoupon.getCouponStartTime());
		itemNew.setCouponEndTime(tbkCoupon.getCouponEndTime());
		itemNew.setQuanSurplus(tbkCoupon.getCouponTotalCount());
		itemNew.setQuanReceive(tbkCoupon.getCouponTotalCount()-tbkCoupon.getCouponRemainCount());
		itemNew.setVolume(tbkCoupon.getVolume().toString());
		itemNew.setAddTime(DateUtil.formatDate(new Date()));
		String zkFinalPrice = tbkCoupon.getZkFinalPrice();
		itemNew.setPrice(zkFinalPrice);
		
		
		//优惠券点击地址
		String couponClickUrl = tbkCoupon.getCouponClickUrl();
		Map<String, String> params = this.getUrlParam(couponClickUrl);
		String me = params.get("e");
		
		MapData mapData = tbkService.getCouponInfo(me).getData();
		if(mapData==null){
			LOGGER.info("查询优惠券信息失败");
			return result.setErrorMessage("查询优惠券信息失败");
		}
		//优惠券金额
		itemNew.setQuan(mapData.getCouponAmount());
		
		//券后价		
		double couponPrice = Arith.sub(Double.valueOf(zkFinalPrice),Double.valueOf(itemNew.getQuan()));
		itemNew.setCouponPrice(String.valueOf(couponPrice));
		itemNew.setCateId(itemCateId);
		itemNew.setIntro(tbkCoupon.getItemDescription() != null ? tbkCoupon.getItemDescription():"");
		
		itemRepository.save(itemNew);
		LOGGER.info("{}es的商品主键为：{}",isUpdate == true ? "更新":"插入",itemNew.getId());
		return result;
	}
	
	/**
	 * 获取get url 的参数
	 * @author shuai.ding
	 * @param url
	 * @return
	 */
	private Map<String, String> getUrlParam(String url){
		Map<String, String> result = new HashMap<String, String>();
		String[] array1 = url.split("\\?");
		if(array1!=null && array1.length==2){
			String param =  array1[1];
			String[] array2 = param.split("&");
			for(String param2:array2){
				String[] array3 = param2.split("=");
				result.put(array3[0], array3[1]);
			}
		}
		return result;
	}

	@Override
	public BaseResult clearExpiredItems() {
		BaseResult result = new BaseResult();
		esItemRepository.delExpiredItems();
		return result;
	}

	@Override
	public BaseResult insertOrUpdate(Item item, Integer itemCateId) {
        BaseResult result = new BaseResult();
		
		Long numIid = item.getNumIid();
		Item itemTemp = itemRepository.queryItemBynumIid(numIid);
		
		boolean isUpdate =false;
		if(itemTemp != null) {  //更新
			isUpdate = true;	
			item.setId(itemTemp.getId());
		}else {  //插入
			item.setId(new Date().getTime());  //暂时使用毫秒数表示主键
		}
		item.setAddTime(DateUtil.formatDate(new Date()));
		item.setCateId(itemCateId);
		itemRepository.save(item);
		LOGGER.info("{}es的商品主键为：{}",isUpdate == true ? "更新":"插入",item.getId());		
		return result;
	}

}
