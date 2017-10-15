package com.tooklili.model.taobao;

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
	private Integer couponTotalCount;
	
	/**
	 * 优惠券剩余个数
	 */
	private Integer couponLeftCount;
	
	/**
	 * 原价
	 */
	private Integer reservePrice;
	
	/**
	 * 现价
	 */
	private Integer zkPrice;
	
	/**
	 * 月销
	 */
	private Integer biz30day;
	
	/**
	 * 佣金比率
	 */
	private Integer tkRate;
	
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
	private String auctionId;

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
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPictUrl() {
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

	public Integer getCouponTotalCount() {
		return couponTotalCount;
	}

	public void setCouponTotalCount(Integer couponTotalCount) {
		this.couponTotalCount = couponTotalCount;
	}

	public Integer getCouponLeftCount() {
		return couponLeftCount;
	}

	public void setCouponLeftCount(Integer couponLeftCount) {
		this.couponLeftCount = couponLeftCount;
	}

	public Integer getReservePrice() {
		return reservePrice;
	}

	public void setReservePrice(Integer reservePrice) {
		this.reservePrice = reservePrice;
	}

	public Integer getZkPrice() {
		return zkPrice;
	}

	public void setZkPrice(Integer zkPrice) {
		this.zkPrice = zkPrice;
	}

	public Integer getBiz30day() {
		return biz30day;
	}

	public void setBiz30day(Integer biz30day) {
		this.biz30day = biz30day;
	}

	public Integer getTkRate() {
		return tkRate;
	}

	public void setTkRate(Integer tkRate) {
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

	public String getAuctionId() {
		return auctionId;
	}

	public void setAuctionId(String auctionId) {
		this.auctionId = auctionId;
	}
}
