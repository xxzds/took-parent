package com.tooklili.model.tooklili;

/**
 * 人工选择的商品
 * @author ding.shuai
 * @date 2018年6月24日下午4:21:40
 */
public class TookItemSelect {
	/**
	 * 主键id
	 */
	private Integer id;
	
	/**
	 * 淘宝中对应的商品id
	 */
	private Long numIid;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 图片
	 */
	private String picUrl;
	
	/**
	 *原价
	 */
	private String price;
	
	/**
	 * 折扣价
	 */
	private String couponPrice;
	
	/**
	 * 优惠券金额
	 */
	private String quan;
	
	/**
	 * 销量
	 */
	private String volume;
	
	/**
	 *开始时间
	 */
	private String couponStartTime;
	
	/**
	 * 结束时间
	 */
	private String couponEndTime;
	
	/**
	 * 添加时间
	 */
	private String addTime;
	
	/**
	 * 商品类别(B、天猫 C、淘宝)
	 */	
	private String shopType;
	
	/**
	 * 分类名称
	 */
	private String cateName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getNumIid() {
		return numIid;
	}

	public void setNumIid(Long numIid) {
		this.numIid = numIid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCouponPrice() {
		return couponPrice;
	}

	public void setCouponPrice(String couponPrice) {
		this.couponPrice = couponPrice;
	}

	public String getQuan() {
		return quan;
	}

	public void setQuan(String quan) {
		this.quan = quan;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getCouponStartTime() {
		return couponStartTime;
	}

	public void setCouponStartTime(String couponStartTime) {
		this.couponStartTime = couponStartTime;
	}

	public String getCouponEndTime() {
		return couponEndTime;
	}

	public void setCouponEndTime(String couponEndTime) {
		this.couponEndTime = couponEndTime;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getShopType() {
		return shopType;
	}

	public void setShopType(String shopType) {
		this.shopType = shopType;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
}
