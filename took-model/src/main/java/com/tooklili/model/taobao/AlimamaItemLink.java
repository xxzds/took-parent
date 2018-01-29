package com.tooklili.model.taobao;

import io.swagger.annotations.ApiModelProperty;

/**
 * alimama 商品推广链接实体
 * @author shuai.ding
 *
 * @date 2017年10月15日下午2:41:41
 */
public class AlimamaItemLink {
	
	/**
	 * 商品口令
	 */
	@ApiModelProperty("商品口令")
	private String taoToken;
	
	/**
	 * 领券口令
	 */
	@ApiModelProperty("领券口令")
	private String couponLinkTaoToken;
		
	/**
	 * 二维码链接（300天有效期）
	 */
	@ApiModelProperty("二维码链接（300天有效期）")
	private String qrCodeUrl;
	
	/**
	 * 商品链接
	 */
	@ApiModelProperty("商品链接")
	private String clickUrl;
	
		
	/**
	 * 领券链接
	 */
	@ApiModelProperty("领券链接")
	private String couponLink;
	
	
	/**
	 * 商品短链接（300天有效期）
	 */
	@ApiModelProperty("商品短链接（300天有效期）")
	private String shortLinkUrl;
	
	/**
	 * 领券短链接（300天有效期）
	 */
	@ApiModelProperty("领券短链接（300天有效期）")
	private String couponShortLinkUrl;
	
	
	/***************************custom*******************************/
	/**
	 * 自定义商品短链接
	 */
	@ApiModelProperty("自定义商品短链接")
	private String customShortLinkUrl;
	
	/**
	 * 自定义领券短链接
	 */
	@ApiModelProperty("自定义领券短链接")
	private String customCouponShortLinkUrl;

	public String getTaoToken() {
		return taoToken;
	}

	public void setTaoToken(String taoToken) {
		this.taoToken = taoToken;
	}

	public String getCouponLinkTaoToken() {
		return couponLinkTaoToken;
	}

	public void setCouponLinkTaoToken(String couponLinkTaoToken) {
		this.couponLinkTaoToken = couponLinkTaoToken;
	}

	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

	public String getClickUrl() {
		return clickUrl;
	}

	public void setClickUrl(String clickUrl) {
		this.clickUrl = clickUrl;
	}

	public String getCouponLink() {
		return couponLink;
	}

	public void setCouponLink(String couponLink) {
		this.couponLink = couponLink;
	}

	public String getShortLinkUrl() {
		return shortLinkUrl;
	}

	public void setShortLinkUrl(String shortLinkUrl) {
		this.shortLinkUrl = shortLinkUrl;
	}

	public String getCouponShortLinkUrl() {
		return couponShortLinkUrl;
	}

	public void setCouponShortLinkUrl(String couponShortLinkUrl) {
		this.couponShortLinkUrl = couponShortLinkUrl;
	}

	public String getCustomShortLinkUrl() {
		return customShortLinkUrl;
	}

	public void setCustomShortLinkUrl(String customShortLinkUrl) {
		this.customShortLinkUrl = customShortLinkUrl;
	}

	public String getCustomCouponShortLinkUrl() {
		return customCouponShortLinkUrl;
	}

	public void setCustomCouponShortLinkUrl(String customCouponShortLinkUrl) {
		this.customCouponShortLinkUrl = customCouponShortLinkUrl;
	}
}
