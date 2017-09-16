package com.tooklili.model.tooklili;

/**
 * tooklili 商品模型
 * @author shuai.ding
 * @date 2017年9月11日下午2:14:07
 */
public class Item {
	/**
	 * 主键id
	 */
	private Long id;
	
	/**
	 * 分类id
	 */
	private Integer cateId;
	
	/**
	 * 淘宝中对应的商品id
	 */
	private String numIid;
	
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
	 * 领券地址
	 */
	private String quanUrl;
	
	/**
	 * 优惠券剩余数
	 */
	private String quanSurplus;
	
	/**
	 * 优惠券已领取数
	 */
	private String quanReceive;
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCateId() {
		return cateId;
	}

	public void setCateId(Integer cateId) {
		this.cateId = cateId;
	}

	public String getNumIid() {
		return numIid;
	}

	public void setNumIid(String numIid) {
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

	public String getQuanUrl() {
		return quanUrl;
	}

	public void setQuanUrl(String quanUrl) {
		this.quanUrl = quanUrl;
	}

	public String getQuanSurplus() {
		return quanSurplus;
	}

	public void setQuanSurplus(String quanSurplus) {
		this.quanSurplus = quanSurplus;
	}

	public String getQuanReceive() {
		return quanReceive;
	}

	public void setQuanReceive(String quanReceive) {
		this.quanReceive = quanReceive;
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
}
