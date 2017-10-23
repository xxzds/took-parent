package com.tooklili.model.taobao;

import org.apache.commons.lang.StringUtils;

/**
 * alimama商品实体
 * @author shuai.ding
 *
 * @date 2017年10月15日下午1:50:31
 */
public class AlimamaItem {
	
	/**
	 * 优惠券金额
	 */
	private Integer couponAmount;
	
	/**
	 * 优惠券有效开始时间
	 */
	private String couponEffectiveStartTime;
	
	/**
	 * 优惠券有效结束时间
	 */
	private String couponEffectiveEndTime;
	
	
	/**
	 * 商品标题
	 */
	private String title;
	
	/**
	 * 主图
	 */
	private String pictUrl;
	
	/**
	 * 淘宝网链接地址
	 */
	private String auctionUrl;
	
	/**
	 * 优惠券信息
	 */
	private String couponInfo;
	
	/**
	 * 优惠券总个数
	 */
	private Long couponTotalCount;
	
	/**
	 * 优惠券剩余个数
	 */
	private Long couponLeftCount;
	
	/**
	 * 原价
	 */
	private String reservePrice;
	
	/**
	 * 现价
	 */
	private String zkPrice;
	
	/**
	 * 月销
	 */
	private Integer biz30day;
	
	/**
	 * 佣金比率
	 */
	private Double tkRate;
	
	/**
	 * 佣金
	 */
	private Double tkCommFee;
	
	/**
	 * 商店名称
	 */
	private String shopTitle;
	
	/**
	 * 商品id
	 */
	private Long auctionId;
	
	/**
	 * 卖家昵称
	 */
	private String nick;
	
	/**
	 * 卖家id
	 */
	private String sellerId;
	
	/**
	 * 用户类型  0、淘宝  1、天猫
	 */
	private Integer userType;

	public Integer getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(Integer couponAmount) {
		this.couponAmount = couponAmount;
	}

	public String getCouponEffectiveStartTime() {
		return couponEffectiveStartTime;
	}

	public void setCouponEffectiveStartTime(String couponEffectiveStartTime) {
		this.couponEffectiveStartTime = couponEffectiveStartTime;
	}

	public String getCouponEffectiveEndTime() {
		return couponEffectiveEndTime;
	}

	public void setCouponEffectiveEndTime(String couponEffectiveEndTime) {
		this.couponEffectiveEndTime = couponEffectiveEndTime;
	}

	public String getTitle() {
		//清除html标签
	    if(StringUtils.isNotEmpty(this.title)){
	    	title = title.replaceAll("<[.[^>]]*>","");
	    }		
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPictUrl() {
		if(pictUrl!=null && pictUrl.startsWith("//")){
			pictUrl="http:"+pictUrl;
		}
		return pictUrl;
	}

	public void setPictUrl(String pictUrl) {
		this.pictUrl = pictUrl;
	}

	public String getAuctionUrl() {
		return auctionUrl;
	}

	public void setAuctionUrl(String auctionUrl) {
		this.auctionUrl = auctionUrl;
	}

	public String getCouponInfo() {
		return couponInfo;
	}

	public void setCouponInfo(String couponInfo) {
		this.couponInfo = couponInfo;
	}

	public Long getCouponTotalCount() {
		return couponTotalCount;
	}

	public void setCouponTotalCount(Long couponTotalCount) {
		this.couponTotalCount = couponTotalCount;
	}

	public Long getCouponLeftCount() {
		return couponLeftCount;
	}

	public void setCouponLeftCount(Long couponLeftCount) {
		this.couponLeftCount = couponLeftCount;
	}

	public String getReservePrice() {
		return reservePrice;
	}

	public void setReservePrice(String reservePrice) {
		this.reservePrice = reservePrice;
	}

	public String getZkPrice() {
		return zkPrice;
	}

	public void setZkPrice(String zkPrice) {
		this.zkPrice = zkPrice;
	}

	public Integer getBiz30day() {
		return biz30day;
	}

	public void setBiz30day(Integer biz30day) {
		this.biz30day = biz30day;
	}

	public Double getTkRate() {
		return tkRate;
	}

	public void setTkRate(Double tkRate) {
		this.tkRate = tkRate;
	}

	public Double getTkCommFee() {
		return tkCommFee;
	}

	public void setTkCommFee(Double tkCommFee) {
		this.tkCommFee = tkCommFee;
	}

	public String getShopTitle() {
		return shopTitle;
	}

	public void setShopTitle(String shopTitle) {
		this.shopTitle = shopTitle;
	}

	public Long getAuctionId() {
		return auctionId;
	}

	public void setAuctionId(Long auctionId) {
		this.auctionId = auctionId;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}
}
