package com.tooklili.service.biz.impl.admin.took;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tooklili.dao.db.intf.admin.TookItemTwentyDao;
import com.tooklili.model.taobao.AlimamaItem;
import com.tooklili.model.tooklili.Item;
import com.tooklili.service.biz.intf.admin.took.ItemTwentyService;
import com.tooklili.service.exception.BusinessException;
import com.tooklili.util.Arith;
import com.tooklili.util.DateUtil;
import com.tooklili.util.result.BaseResult;
import com.tooklili.util.result.PlainResult;

/**
 * 20元封顶商品服务
 * @author ding.shuai
 * @date 2018年5月20日下午3:42:31
 */
@Service
public class ItemTwentyServiceImpl implements ItemTwentyService{
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemTwentyServiceImpl.class);
	
	@Autowired
	private TookItemTwentyDao tookItemTwentyDao;

	@Override
	public BaseResult insertItemTwenty(Item item) {
		BaseResult result = new BaseResult();
		long count = tookItemTwentyDao.insert(item);
		if(count <= 0) {
			throw new BusinessException("插入20元封顶失败");
		}		
		return result;
	}

	@Override
	public PlainResult<String> insertOrUpdate(List<AlimamaItem> alimamaItems,Integer itemCateId) {
		PlainResult<String> result = new PlainResult<String>();
		
		Integer insertCount = 0;
		Integer updateCount = 0;
		
		for(AlimamaItem alimamaItem : alimamaItems){
			boolean isUpdate = insertOrUpdateAlimamaItemToItemTwenty(alimamaItem, itemCateId);
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
	 * 
	 * @param alimamaItem  阿里妈妈商品
	 * @param itemCateId   商品分类id
	 * @return  是否更新商品
	 */
	private boolean insertOrUpdateAlimamaItemToItemTwenty(AlimamaItem alimamaItem,Integer itemCateId) {
		Long numIid = alimamaItem.getAuctionId();
		Item item = tookItemTwentyDao.queryItemByNumiid(numIid);
		
		Item itemNew = new Item();
		boolean isUpdate = false;
		
		itemNew.setCateId(itemCateId);
		//如果是无优惠券的商品，券的开始时间为当前时间、结束时间为当前时间+15天，主要为清除过期商品做准备
		if(StringUtils.isNotEmpty(alimamaItem.getCouponEffectiveStartTime())){
			itemNew.setCouponStartTime(alimamaItem.getCouponEffectiveStartTime());
		}else {
			itemNew.setCouponStartTime(DateUtil.formatDay(new Date()));
		}	
		
		if(StringUtils.isNotEmpty(alimamaItem.getCouponEffectiveEndTime())){
			itemNew.setCouponEndTime(alimamaItem.getCouponEffectiveEndTime());
		}else {
			itemNew.setCouponEndTime(DateUtil.formatDay(DateUtil.after(15,TimeUnit.DAYS)));
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
		
		if(item != null) {
			itemNew.setId(item.getId());
			tookItemTwentyDao.updateByIdSelective(itemNew);
			LOGGER.info("更新20元封顶商品主键为：{}",item.getId());
		}else {
			itemNew.setNumIid(numIid);
			itemNew.setTitle(alimamaItem.getTitle());
			itemNew.setPicUrl(alimamaItem.getPictUrl());
			itemNew.setIntro("");
			//商品类别
			Integer userType = alimamaItem.getUserType();
			if(userType==0){
				itemNew.setShopType("C");
			}else if(userType==1){
				itemNew.setShopType("B");
			}
			tookItemTwentyDao.insertSelective(itemNew);
			LOGGER.info("插入20元封顶商品主键为：{}",itemNew.getId());
		}		
		return isUpdate;
	}

	@Override
	public PlainResult<Integer> delExpiredItems() {
		PlainResult<Integer> result = new PlainResult<Integer>();
		int count = tookItemTwentyDao.delExpiredItems();
		if(count<0) {
			throw new BusinessException("删除20元过期商品失败");
		}
		result.setData(count);
		return result;
	}
}
