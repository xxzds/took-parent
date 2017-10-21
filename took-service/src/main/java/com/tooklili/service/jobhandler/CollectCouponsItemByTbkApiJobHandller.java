package com.tooklili.service.jobhandler;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.taobao.api.ApiException;
import com.taobao.api.request.TbkDgItemCouponGetRequest;
import com.taobao.api.response.TbkDgItemCouponGetResponse.TbkCoupon;
import com.tooklili.dao.intf.tooklili.ItemDao;
import com.tooklili.enums.tooklili.ItemCateEnum;
import com.tooklili.model.tooklili.Item;
import com.tooklili.model.tooklili.ItemModel;
import com.tooklili.service.biz.intf.taobao.TbkService;
import com.tooklili.util.Arith;
import com.tooklili.util.DateUtil;
import com.tooklili.util.JsonFormatTool;
import com.tooklili.util.result.PageResult;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;

/**
 * 通过淘宝客采券接口，采集优惠券商品
 * @author ding.shuai
 * @date 2017年10月21日下午7:55:01
 */
@JobHander(value="collectCouponsItemByTbkApiJobHandller")
@Service
public class CollectCouponsItemByTbkApiJobHandller extends IJobHandler{
	private static final Logger LOGGER = LoggerFactory.getLogger(CollectCouponsItemByTbkApiJobHandller.class);

	@Resource
	private ItemDao itemDao;
	
	@Resource
	private TbkService tbkService;
	
	private final Integer PAGEMAX=100;
	
	@Override
	public ReturnT<String> execute(String... arg0) throws Exception {
		List<Map<Integer,String[]>> itemCateList= Lists.newArrayList();
		
		//服装
		String[] constume = new String[]{"女装上衣","女装裙装","女装裤装","男装上衣","男装裤装","潮流女装","精品男装"};
		Map<Integer, String[]> constumeMap = Maps.newHashMap();
		constumeMap.put(ItemCateEnum.CONSTUME.getCode(), constume);
		itemCateList.add(constumeMap);
		
		//母婴
		String[] montherBoby = new String[]{"孕妇","儿童","玩具","母婴生活"};
		Map<Integer, String[]> montherBobyMap = Maps.newHashMap();
		montherBobyMap.put(ItemCateEnum.MONTHER_BOBY.getCode(), montherBoby);
		itemCateList.add(montherBobyMap);
		
		//化妆品
		String[] cosmetics = new String[]{"化妆品"};
		Map<Integer, String[]> cosmeticsMap = Maps.newHashMap();
		cosmeticsMap.put(ItemCateEnum.COSMETICS.getCode(), cosmetics);
		itemCateList.add(cosmeticsMap);
		//居家
		String[] occupyHome = new String[]{"居家","生活用品","生活小神器","创意家居"};
		Map<Integer, String[]> occupyHomeMap = Maps.newHashMap();
		occupyHomeMap.put(ItemCateEnum.OCCUPY_HOME.getCode(), occupyHome);
		itemCateList.add(occupyHomeMap);
		//鞋包配饰
		String[] shoeBagAccessories = new String[]{"男鞋","女鞋","男包","女包","鞋包运动"};
		Map<Integer, String[]> shoeBagAccessoriesMap = Maps.newHashMap();
		shoeBagAccessoriesMap.put(ItemCateEnum.SHOE_BAG_ACCESSORIES.getCode(), shoeBagAccessories);
		itemCateList.add(shoeBagAccessoriesMap);
		
		//美食
		String[] gastronomy = new String[]{"美食","零食","零食三只松鼠","零食良品铺子"};
		Map<Integer, String[]> gastronomyMap = Maps.newHashMap();
		gastronomyMap.put(ItemCateEnum.GASTRONOMY.getCode(), gastronomy);
		itemCateList.add(gastronomyMap);
		
		//文体车品
		String[] productStyleCar=new String[]{"文体","车配饰","文体车品","家装车品"};
		Map<Integer, String[]> productStyleCarMap = Maps.newHashMap();
		productStyleCarMap.put(ItemCateEnum.PRODUCT_STYLE_CAR.getCode(), productStyleCar);
		itemCateList.add(productStyleCarMap);
		
		//数码家电
		String[] digitalHomeAppliances=new String[]{"数码","电器"};
		Map<Integer, String[]> digitalHomeAppliancesMap = Maps.newHashMap();
		digitalHomeAppliancesMap.put(ItemCateEnum.DIGITAL_HOME_APPLIANCES.getCode(), digitalHomeAppliances);
		itemCateList.add(digitalHomeAppliancesMap);
		
		//每次调用采集10次
		for(int i=0;i<10;i++){
			this.collectCouponItemByTbkApi(itemCateList);
		}
		return ReturnT.SUCCESS;
	}
	
	/**
	 * 调用采券接口，将采集的券存入数据库
	 * @param itemCateList   关键词集合
	 * @throws ApiException
	 * @throws ParseException
	 */
	private void collectCouponItemByTbkApi(List<Map<Integer,String[]>> itemCateList) throws ApiException, ParseException{
		int random = (int)(Math.random() * itemCateList.size());
		Map<Integer, String[]> map =  itemCateList.get(random);
		LOGGER.info("选择的关键词集合:{}",JSON.toJSONString(map));
		
		Integer itemCateId = map.keySet().iterator().next();
		String[] keyWords = map.get(itemCateId);
		int keyWordsIndex = (int)(Math.random() * keyWords.length);
		String keyWord = keyWords[keyWordsIndex];
		
		//调用淘宝客采券接口
		TbkDgItemCouponGetRequest req = new TbkDgItemCouponGetRequest();
		Long pageNo = (long)(Math.random() * (PAGEMAX-1) +1);
		req.setPageNo(100L);
		req.setPageSize(1L);
		req.setQ(keyWord);
		LOGGER.info("选择的关键词：{}，采集第{}页",keyWord,pageNo);
		PageResult<TbkCoupon> result = tbkService.getCouponItems(req);
		LOGGER.info("调用tbk采券接口,返回的内容：{}",JsonFormatTool.formatJson(JSON.toJSONString(result)));
		
		List<TbkCoupon> tbkCoupons = result.getData();
		if(tbkCoupons==null || tbkCoupons.size()==0){
			LOGGER.info("通过关键词[{}]查询第{}页的商品没有查到",keyWord,pageNo);
			return;
		}		
		TbkCoupon tbkCoupon = tbkCoupons.get(0);
		
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
		
		//默认值
		itemModel.setQuanCondition("");
		String couponInfo =tbkCoupon.getCouponInfo();			
		String pattern="满(\\d+?)元减(\\d+?)元";			
		Matcher m = Pattern.compile(pattern).matcher(couponInfo);
		 if (m.find()) {
			 if(StringUtils.isNotEmpty(m.group(1))){
				 itemModel.setQuanCondition(m.group(1));
			 }
			 itemModel.setQuan(m.group(2));
		 }	
		double couponPrice = Arith.sub(Double.valueOf(zkFinalPrice),Double.valueOf(itemModel.getQuan()));
		itemModel.setCouponPrice(String.valueOf(couponPrice));
		itemModel.setCateId(itemCateId);
		if(item!=null){ //更新
			itemModel.setId(item.getId());
			itemDao.updateItemById(itemModel);
			LOGGER.info("更新数据库的商品主键为：{}",itemModel.getId());
		}else{  //insert			
			itemModel.setNumIid(tbkCoupon.getNumIid());
			itemModel.setTitle(tbkCoupon.getTitle());
			itemModel.setPicUrl(tbkCoupon.getPictUrl());
			
			itemModel.setQuanUrl(tbkCoupon.getCouponClickUrl());
			
			itemModel.setIntro(tbkCoupon.getItemDescription());
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
	}

}
