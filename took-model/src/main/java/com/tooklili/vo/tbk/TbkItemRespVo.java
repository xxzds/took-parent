package com.tooklili.vo.tbk;

/**
 * 淘宝客商品响应模型
 * @author shuai.ding
 * @date 2017年8月4日下午5:23:54
 */
public class TbkItemRespVo {
	/**
	 * 商品ID
	 */
	private Long numIid;
	
	/**
	 * 商品地址
	 */
	private String itemUrl;
	
	/**
	 * 淘客地址
	 */
	private String clickUrl;

	public Long getNumIid() {
		return numIid;
	}

	public void setNumIid(Long numIid) {
		this.numIid = numIid;
	}

	public String getItemUrl() {
		return itemUrl;
	}

	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}

	public String getClickUrl() {
		return clickUrl;
	}

	public void setClickUrl(String clickUrl) {
		this.clickUrl = clickUrl;
	}
}
