package com.tooklili.model.taobao;

/**
 * 淘口令和短链接实体
 * @author shuai.ding
 *
 * @date 2017年10月31日下午3:36:22
 */
public class TpwdAndShortUrlModel {
	
	/**
	 * 领券口令
	 */
	private String couponLinkTaoToken;
	
	/**
	 * 领券短链接
	 */
	private String couponShortLinkUrl;

	public String getCouponLinkTaoToken() {
		return couponLinkTaoToken;
	}

	public void setCouponLinkTaoToken(String couponLinkTaoToken) {
		this.couponLinkTaoToken = couponLinkTaoToken;
	}

	public String getCouponShortLinkUrl() {
		return couponShortLinkUrl;
	}

	public void setCouponShortLinkUrl(String couponShortLinkUrl) {
		this.couponShortLinkUrl = couponShortLinkUrl;
	}
}
