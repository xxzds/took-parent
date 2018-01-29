package com.tooklili.model.tooklili;

import io.swagger.annotations.ApiModelProperty;

/**
 * tooklili 商品模型-部分字段
 * @author shuai.ding
 * @date 2017年9月11日下午2:14:07
 */
public class Item {
	/**
	 * 主键id
	 */
	@ApiModelProperty("主键id")
	private Long id;
	
	/**
	 * 分类id
	 */
	@ApiModelProperty("分类id")
	private Integer cateId;
	
	/**
	 * 淘宝中对应的商品id
	 */
	@ApiModelProperty("淘宝中对应的商品id")
	private Long numIid;
	
	/**
	 * 标题
	 */
	@ApiModelProperty("标题")
	private String title;
	
	/**
	 * 图片
	 */
	@ApiModelProperty("图片")
	private String picUrl;
	
	/**
	 *原价
	 */
	@ApiModelProperty("原价")
	private String price;
	
	/**
	 * 折扣价
	 */
	@ApiModelProperty("折扣价")
	private String couponPrice;
	
	/**
	 * 优惠券金额
	 */
	@ApiModelProperty("优惠券金额")
	private String quan;
	
	/**
	 * 领券地址
	 */
	@ApiModelProperty("领券地址")
	private String quanUrl;
	
	/**
	 * 优惠券总数？
	 */
	@ApiModelProperty("优惠券总数")
	private Long quanSurplus;
	
	/**
	 * 优惠券剩余数？
	 */
	@ApiModelProperty("优惠券剩余数")
	private Long quanReceive;
	
	/**
	 * 销量
	 */
	@ApiModelProperty("销量")
	private String volume;
	
	/**
	 *开始时间
	 */
	@ApiModelProperty("开始时间")
	private String couponStartTime;
	
	/**
	 * 结束时间
	 */
	@ApiModelProperty("结束时间")
	private String couponEndTime;
	
	/**
	 * 添加时间
	 */
	@ApiModelProperty("添加时间")
	private String addTime;
	
	/**
	 * 商品类别(B、天猫 C、淘宝)
	 */
	@ApiModelProperty("商品类别(B、天猫 C、淘宝)")
	private String shopType;
	
	/**
	 * 商品简单描述
	 */
	@ApiModelProperty("商品简单描述")
	private String intro;

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

	public String getQuanUrl() {
		return quanUrl;
	}

	public void setQuanUrl(String quanUrl) {
		this.quanUrl = quanUrl;
	}

	public Long getQuanSurplus() {
		return quanSurplus;
	}

	public void setQuanSurplus(Long quanSurplus) {
		this.quanSurplus = quanSurplus;
	}

	public Long getQuanReceive() {
		return quanReceive;
	}

	public void setQuanReceive(Long quanReceive) {
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

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}
}
