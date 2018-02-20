package com.tooklili.service.biz.impl.tooklili;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.response.TbkCouponGetResponse.MapData;
import com.taobao.api.response.TbkDgItemCouponGetResponse.TbkCoupon;
import com.tooklili.dao.db.intf.tooklili.ItemDao;
import com.tooklili.model.taobao.AlimamaItem;
import com.tooklili.model.tooklili.Item;
import com.tooklili.model.tooklili.ItemModel;
import com.tooklili.service.biz.intf.taobao.TaobaoService;
import com.tooklili.service.biz.intf.taobao.TbkService;
import com.tooklili.service.biz.intf.tooklili.ItemOperService;
import com.tooklili.util.Arith;
import com.tooklili.util.DateUtil;
import com.tooklili.util.result.BaseResult;
import com.tooklili.util.result.PlainResult;

/**
 * 数据库 更新、插入商品
 * @author shuai.ding
 * @date 2017年10月25日上午11:34:00
 */
@Service
public class ItemDBOperServiceImpl implements ItemOperService{
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemDBOperServiceImpl.class);
	
//	@Resource
//	private AlimamaService alimamaService;
	
	@Resource
	private ItemDao itemDao;
	
	@Resource
	private TaobaoService taobaoService;
	
	@Resource
	private TbkService tbkService;
	
	@Override
	public BaseResult insertOrUpdate(AlimamaItem alimamaItem,Integer itemCateId) throws UnsupportedEncodingException, ParseException {
		BaseResult result = new BaseResult();
		
		Long numIid = alimamaItem.getAuctionId();
		Item item = itemDao.queryItemBynumId(numIid);
		
		ItemModel itemModel = new ItemModel();		
		itemModel.setCouponStartTime(String.valueOf(DateUtil.parseDate(alimamaItem.getCouponEffectiveStartTime(),DateUtil.DEFAULT_DAY_STYLE).getTime()/1000));
		itemModel.setCouponEndTime(String.valueOf(DateUtil.parseDate(alimamaItem.getCouponEffectiveEndTime(),DateUtil.DEFAULT_DAY_STYLE).getTime()/1000));
		itemModel.setQuanSurplus(alimamaItem.getCouponTotalCount());
		itemModel.setQuanReceive(alimamaItem.getCouponTotalCount()-alimamaItem.getCouponLeftCount());
		itemModel.setCouponRate(String.valueOf(alimamaItem.getCouponLeftCount()));
		itemModel.setVolume(alimamaItem.getBiz30day().toString());
		itemModel.setAddTime(String.valueOf(new Date().getTime()/1000));
		String zkFinalPrice = alimamaItem.getZkPrice();
		itemModel.setPrice(zkFinalPrice);
		
		//默认值
		itemModel.setQuanCondition("");
		String couponInfo =alimamaItem.getCouponInfo();			
		String pattern="满(\\d+?)元减(\\d+?)元";			
		Matcher m = Pattern.compile(pattern).matcher(couponInfo);
		 if (m.find()) {
			 if(StringUtils.isNotEmpty(m.group(1))){
				 itemModel.setQuanCondition(m.group(1));
			 }
		 }	
		//优惠券
		itemModel.setQuan(alimamaItem.getCouponAmount().toString());
		double couponPrice = Arith.sub(Double.valueOf(zkFinalPrice),Double.valueOf(itemModel.getQuan()));
		itemModel.setCouponPrice(String.valueOf(couponPrice));
		
		if(item!=null){ //更新
			itemModel.setId(item.getId());
			itemDao.updateItemById(itemModel);
			LOGGER.info("更新数据库的商品主键为：{}",itemModel.getId());
		}else{  //insert
			itemModel.setCateId(itemCateId);
			itemModel.setNumIid(numIid);
			itemModel.setTitle(alimamaItem.getTitle());
			itemModel.setPicUrl(alimamaItem.getPictUrl());
			
//			AlimamaItemLink alimamaItemLink =  alimamaService.generatePromoteLink(numIid.toString()).getData();
//			if(alimamaItemLink == null){
//				LOGGER.info("推广链接生成失败");
//				return result.setErrorMessage("推广链接生成失败");
//			}
//			itemModel.setQuanUrl(alimamaItemLink.getCouponLink());
			
//			itemModel.setIntro(taobaoService.getItemSubTitleByItemId(String.valueOf(numIid)).getData());
			itemModel.setNick(alimamaItem.getNick());
			itemModel.setSellerId(alimamaItem.getSellerId());
//			itemModel.setClickUrl(alimamaItemLink.getCouponLink());
			itemModel.setIsq(1);
			itemModel.setItemUrl(alimamaItem.getAuctionUrl());
			
			itemModel.setCommissionRate(alimamaItem.getTkRate().toString());			
			itemModel.setCommission(alimamaItem.getTkCommFee().toString());
			
			//商品类别
			Integer userType = alimamaItem.getUserType();
			if(userType==0){
				itemModel.setShopType("C");
			}else if(userType==1){
				itemModel.setShopType("B");
			}
				
			itemDao.insertItem(itemModel);
			LOGGER.info("插入数据库的商品主键为：{}",itemModel.getId());
		}
		return result;
	}

	@Override
	public BaseResult insertOrUpdate(TbkCoupon tbkCoupon,Integer itemCateId) throws ApiException, ParseException {
		BaseResult result = new BaseResult();
		
		Long numIid = tbkCoupon.getNumIid();
		Item item = itemDao.queryItemBynumId(numIid);
		
		ItemModel itemModel = new ItemModel();		
		itemModel.setCouponStartTime(String.valueOf(DateUtil.parseDate(tbkCoupon.getCouponStartTime(),DateUtil.DEFAULT_DAY_STYLE).getTime()/1000));
		itemModel.setCouponEndTime(String.valueOf(DateUtil.parseDate(tbkCoupon.getCouponEndTime(),DateUtil.DEFAULT_DAY_STYLE).getTime()/1000));
		itemModel.setQuanSurplus(tbkCoupon.getCouponTotalCount());
		itemModel.setQuanReceive(tbkCoupon.getCouponTotalCount()-tbkCoupon.getCouponRemainCount());
		itemModel.setCouponRate(String.valueOf(tbkCoupon.getCouponRemainCount()));
		itemModel.setVolume(tbkCoupon.getVolume().toString());
		itemModel.setAddTime(String.valueOf(new Date().getTime()/1000));
		String zkFinalPrice = tbkCoupon.getZkFinalPrice();
		itemModel.setPrice(zkFinalPrice);
		
		
		//优惠券点击地址
		String couponClickUrl = tbkCoupon.getCouponClickUrl();
		Map<String, String> params = this.getUrlParam(couponClickUrl);
		String me = params.get("e");
		
		MapData mapData = tbkService.getCouponInfo(me).getData();
		if(mapData==null){
			LOGGER.info("查询优惠券信息失败");
			return result.setErrorMessage("查询优惠券信息失败");
		}
		
		//优惠券门槛金额
		itemModel.setQuanCondition(mapData.getCouponStartFee());
		//优惠券金额
		itemModel.setQuan(mapData.getCouponAmount());
		
		//券后价		
		double couponPrice = Arith.sub(Double.valueOf(zkFinalPrice),Double.valueOf(itemModel.getQuan()));
		itemModel.setCouponPrice(String.valueOf(couponPrice));
		itemModel.setCateId(itemCateId);
		itemModel.setIntro(tbkCoupon.getItemDescription());
		if(item!=null){ //更新
			itemModel.setId(item.getId());
			itemDao.updateItemById(itemModel);
			LOGGER.info("更新数据库的商品主键为：{}",itemModel.getId());
		}else{  //insert			
			itemModel.setNumIid(tbkCoupon.getNumIid());
			itemModel.setTitle(tbkCoupon.getTitle());
			itemModel.setPicUrl(tbkCoupon.getPictUrl());
			
			itemModel.setQuanUrl(tbkCoupon.getCouponClickUrl());
			
			
			itemModel.setNick(tbkCoupon.getNick());
			itemModel.setSellerId(tbkCoupon.getSellerId().toString());
			itemModel.setClickUrl(tbkCoupon.getCouponClickUrl());
			itemModel.setIsq(1);
			itemModel.setItemUrl(tbkCoupon.getItemUrl());
			String commissionRate = tbkCoupon.getCommissionRate();
			itemModel.setCommissionRate(tbkCoupon.getCommissionRate());

			double commission = Arith.mul(Double.parseDouble(zkFinalPrice), Double.parseDouble(commissionRate)/100);
			itemModel.setCommission(String.valueOf(Arith.round(commission, 2)));
			
			//商品类别
			Long userType = tbkCoupon.getUserType();
			if(userType==0L){
				itemModel.setShopType("C");
			}else if(userType==1L){
				itemModel.setShopType("B");
			}
			
			itemDao.insertItem(itemModel);
			LOGGER.info("插入数据库的商品主键为：{}",itemModel.getId());
		}
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
		long count = itemDao.deleteExpiredItem();
		LOGGER.info("删除{}个过期商品",count);
		return result;
	}

	@Override
	public PlainResult<String> insertOrUpdate(List<AlimamaItem> alimamaItems, Integer itemCateId)
			throws UnsupportedEncodingException, ParseException {
		// TODO Auto-generated method stub
		return null;
	}

}
