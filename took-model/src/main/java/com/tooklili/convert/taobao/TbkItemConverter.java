package com.tooklili.convert.taobao;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.taobao.api.domain.NTbkItem;
import com.taobao.api.request.TbkDgMaterialOptionalRequest;
import com.taobao.api.request.TbkItemGetRequest;
import com.tooklili.model.tooklili.Item;
import com.tooklili.util.Arith;
import com.tooklili.util.DateUtil;
import com.tooklili.vo.tbk.TbkDgMaterialOptionalRequestVo;
import com.tooklili.vo.tbk.TbkItemReqVo;
import com.tooklili.vo.tbk.TbkItemRespVo;

/**
 * 
 * @author ding.shuai
 * @date 2017年8月5日下午6:21:50
 */
public class TbkItemConverter {
	
	public static TbkItemGetRequest toTbkItemGetRequest(TbkItemReqVo tbkItemReqVo){
		if(tbkItemReqVo==null){
			return null;
		}
		TbkItemGetRequest tbkItemGetRequest = new TbkItemGetRequest();
		tbkItemGetRequest.setQ(tbkItemReqVo.getItemName());
		tbkItemGetRequest.setCat(tbkItemReqVo.getItemCate());
		tbkItemGetRequest.setPageNo(Long.valueOf(tbkItemReqVo.getPageNo()));
		tbkItemGetRequest.setPageSize(Long.valueOf(tbkItemReqVo.getPageSize()));
		//按销量排序
		tbkItemGetRequest.setSort("total_sales");
		return tbkItemGetRequest;
	}
	
	public static TbkItemRespVo toTbkItemRespVo(NTbkItem nTbkItem){
		if(nTbkItem==null){
			return null;
		}
		TbkItemRespVo tbkItemRespVo = new TbkItemRespVo();
		tbkItemRespVo.setNumIid(nTbkItem.getNumIid());
		tbkItemRespVo.setItemUrl(nTbkItem.getItemUrl());
		tbkItemRespVo.setPictUrl(nTbkItem.getPictUrl());
		tbkItemRespVo.setSmallImages(nTbkItem.getSmallImages());
		tbkItemRespVo.setTitle(nTbkItem.getTitle());
		tbkItemRespVo.setZkFinalPrice(nTbkItem.getZkFinalPrice());
		tbkItemRespVo.setReservePrice(nTbkItem.getReservePrice());
		
		//销量格式化，大于10000，格式化成xx.x万
		Long volume = nTbkItem.getVolume();
		if(volume != null){
			if(volume>10000){
				tbkItemRespVo.setVolume(new DecimalFormat("#.#").format(volume / 10000.0)+"万");				
			}else{
				tbkItemRespVo.setVolume(String.valueOf(volume));
			}			
		}
		
		//折扣率
		if(StringUtils.isNotEmpty(nTbkItem.getReservePrice()) && StringUtils.isNotEmpty(nTbkItem.getZkFinalPrice())){
			double reservePrice = Double.parseDouble(nTbkItem.getReservePrice());
			double zkFinalPrice = Double.parseDouble(nTbkItem.getZkFinalPrice());		
			//小数最多显示一位
			tbkItemRespVo.setDiscountRate(new DecimalFormat("#.#").format((zkFinalPrice / reservePrice ) * 10));
		}
		return tbkItemRespVo;
	}
	
	
	public static Item convertItem(com.taobao.api.response.TbkDgOptimusMaterialResponse.MapData mapData) {
		if(mapData == null) {
			return null;
		}
		Item item = new Item();
		item.setNumIid(mapData.getNumIid());
		item.setTitle(mapData.getTitle());
		item.setPicUrl(mapData.getPictUrl());
		//现价
		item.setPrice(mapData.getZkFinalPrice());
		//折扣价
		double couponPrice = Arith.sub(Double.valueOf(mapData.getZkFinalPrice()), Double.valueOf(mapData.getCouponAmount().toString()));
		item.setCouponPrice(String.valueOf(couponPrice));
		//优惠券
		item.setQuan(mapData.getCouponAmount().toString());
		item.setQuanUrl(mapData.getCouponClickUrl());
		item.setQuanSurplus(mapData.getCouponTotalCount());
		item.setQuanReceive(mapData.getCouponRemainCount());
		item.setVolume(String.valueOf(mapData.getVolume()));
		//优惠券开始时间
		if(StringUtils.isNotEmpty(mapData.getCouponStartTime())) {
			item.setCouponStartTime(DateUtil.formatDay(new Date(Long.parseLong(mapData.getCouponStartTime()))));
		}else {
			item.setCouponStartTime(DateUtil.formatDay(new Date()));
		}
		
		//优惠券结束时间
		if(StringUtils.isNotEmpty(mapData.getCouponEndTime())) {
			item.setCouponEndTime(DateUtil.formatDay(new Date(Long.parseLong(mapData.getCouponEndTime()))));
		}else {
			item.setCouponEndTime(DateUtil.formatDay(DateUtil.after(15,TimeUnit.DAYS)));
		}
		
		
		//卖家类型，0表示集市，1表示商城
		Long userType = mapData.getUserType();
		if(userType == 0) {
			item.setShopType("C");
		}else if(userType == 1) {
			item.setShopType("B");
		}
		
		item.setIntro(mapData.getItemDescription());
		//单品淘客链接
		item.setClickUrl(mapData.getClickUrl());
		return item;		
	}
	
	public static Item convertItem(com.taobao.api.response.TbkDgMaterialOptionalResponse.MapData mapData) {
		if(mapData == null) {
			return null;
		}
		Item item = new Item();
		
		item.setNumIid(mapData.getNumIid());
		item.setTitle(mapData.getTitle());
		item.setPicUrl(mapData.getPictUrl());
		//现价
		item.setPrice(mapData.getZkFinalPrice());
		item.setQuanSurplus(mapData.getCouponTotalCount());
		item.setQuanReceive(mapData.getCouponRemainCount());
		item.setVolume(String.valueOf(mapData.getVolume()));
		//优惠券开始时间
		if(StringUtils.isNotEmpty(mapData.getCouponStartTime())) {
			item.setCouponStartTime(mapData.getCouponStartTime());
		}else {
			item.setCouponStartTime(DateUtil.formatDay(new Date()));
		}
		
		//优惠券结束时间
		if(StringUtils.isNotEmpty(mapData.getCouponEndTime())) {
			item.setCouponEndTime(mapData.getCouponEndTime());
		}else {
			item.setCouponEndTime(DateUtil.formatDay(DateUtil.after(15,TimeUnit.DAYS)));
		}
		
		//卖家类型，0表示集市，1表示商城
		Long userType = mapData.getUserType();
		if(userType == 0) {
			item.setShopType("C");
		}else if(userType == 1) {
			item.setShopType("B");
		}
		
		//券二合一页面链接
		item.setQuanUrl(mapData.getCouponShareUrl());
		//商品淘客链接
		item.setClickUrl(mapData.getUrl());
		//优惠券id
		item.setCouponId(mapData.getCouponId());
		
		return item;
		
	}
	
	public static TbkDgMaterialOptionalRequest convertTbkDgMaterialOptionalRequest(TbkDgMaterialOptionalRequestVo tbkDgMaterialOptionalRequestVo) {
		if(tbkDgMaterialOptionalRequestVo == null) {
			return null;
		}
		
		TbkDgMaterialOptionalRequest tbkDgMaterialOptionalRequest = new TbkDgMaterialOptionalRequest();
		
		tbkDgMaterialOptionalRequest.setEndPrice(tbkDgMaterialOptionalRequestVo.getEndPrice());
		tbkDgMaterialOptionalRequest.setHasCoupon(tbkDgMaterialOptionalRequestVo.getHasCoupon());
		tbkDgMaterialOptionalRequest.setIncludeGoodRate(tbkDgMaterialOptionalRequestVo.getIncludeGoodRate());
		tbkDgMaterialOptionalRequest.setIncludePayRate30(tbkDgMaterialOptionalRequestVo.getIncludePayRate30());
		tbkDgMaterialOptionalRequest.setIncludeRfdRate(tbkDgMaterialOptionalRequestVo.getIncludeRfdRate());
		tbkDgMaterialOptionalRequest.setIsTmall(tbkDgMaterialOptionalRequestVo.getIsTmall());
		tbkDgMaterialOptionalRequest.setNeedFreeShipment(tbkDgMaterialOptionalRequest.getNeedFreeShipment());
		tbkDgMaterialOptionalRequest.setNeedPrepay(tbkDgMaterialOptionalRequestVo.getNeedPrepay());
		tbkDgMaterialOptionalRequest.setNpxLevel(tbkDgMaterialOptionalRequestVo.getNpxLevel());
		tbkDgMaterialOptionalRequest.setPageNo(tbkDgMaterialOptionalRequestVo.getPageNo());
		tbkDgMaterialOptionalRequest.setPageSize(tbkDgMaterialOptionalRequestVo.getPageSize());
		tbkDgMaterialOptionalRequest.setPlatform(tbkDgMaterialOptionalRequestVo.getPlatform());
		tbkDgMaterialOptionalRequest.setQ(tbkDgMaterialOptionalRequestVo.getQ());
		tbkDgMaterialOptionalRequest.setSort(tbkDgMaterialOptionalRequestVo.getSort());
		tbkDgMaterialOptionalRequest.setStartDsr(tbkDgMaterialOptionalRequestVo.getStartDsr());
		tbkDgMaterialOptionalRequest.setStartPrice(tbkDgMaterialOptionalRequestVo.getStartPrice());
		return tbkDgMaterialOptionalRequest;
	}
	
	public static Item convertItem(com.taobao.api.response.TbkDgItemCouponGetResponse.TbkCoupon tbkCoupon) {
		if(tbkCoupon == null) return null;
		Item item = new Item();
		item.setNumIid(tbkCoupon.getNumIid());
		item.setTitle(tbkCoupon.getTitle());
		item.setPicUrl(tbkCoupon.getPictUrl());
		//现价
		item.setPrice(tbkCoupon.getZkFinalPrice());
		
		//优惠券价格
		String couponInfo =tbkCoupon.getCouponInfo();			
		String pattern="满(\\d+?)元减(\\d+?)元";			
		Matcher m = Pattern.compile(pattern).matcher(couponInfo);
		 if (m.find()) {			
			 item.setQuan(m.group(2));
		 }	
		 //优惠券url
		 item.setQuanUrl(tbkCoupon.getCouponClickUrl());
		
		//折扣价(使用优惠券后的价格)
		double couponPrice = Arith.sub(Double.valueOf(item.getPrice()), Double.valueOf(item.getQuan()));
		item.setCouponPrice(String.valueOf(couponPrice));
		
		
		item.setQuanSurplus(tbkCoupon.getCouponTotalCount());
		item.setQuanReceive(tbkCoupon.getCouponRemainCount());
		item.setVolume(String.valueOf(tbkCoupon.getVolume()));
		item.setCouponStartTime(tbkCoupon.getCouponStartTime());
		item.setCouponEndTime(tbkCoupon.getCouponEndTime());
		
		//卖家类型，0表示集市，1表示商城
		Long userType = tbkCoupon.getUserType();
		if(userType == 0) {
			item.setShopType("C");
		}else if(userType == 1) {
			item.setShopType("B");
		}
		
		item.setIntro(tbkCoupon.getItemDescription());
		item.setClickUrl(tbkCoupon.getItemUrl());
		
		
		return item;
	}
}
