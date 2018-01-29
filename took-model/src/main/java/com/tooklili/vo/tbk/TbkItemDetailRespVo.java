package com.tooklili.vo.tbk;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * 商品详情
 * @author shuai.ding
 *
 * @date 2017年8月20日上午11:20:05
 */
public class TbkItemDetailRespVo {
	/**
	 * 商品ID
	 */
	@ApiModelProperty("商品ID")
	private Long numIid;
	
	/**
	 * 商品地址
	 */
	@ApiModelProperty("商品地址")
	private String itemUrl;
	
	/**
	 * 商品主图
	 */
	@ApiModelProperty("商品主图")
	private String pictUrl;
	
	/**
	 * 商品小图列表
	 */
	@ApiModelProperty("商品小图列表")
	private List<String> smallImages;

	/**
	 * 商品标题
	 */
	@ApiModelProperty("商品标题")
	private String title;
	
	/**
	 * 商品折扣价格
	 */
	@ApiModelProperty("商品折扣价格")
	private String zkFinalPrice;
	
	/**
	 * 商品一口价格
	 */
	@ApiModelProperty("商品一口价格")
	private String reservePrice;
	
	/**
	 * 30天销量
	 */
	@ApiModelProperty("30天销量")
	private String volume;
	
	/**
	 * 折扣率
	 */
	@ApiModelProperty("折扣率")
	private String discountRate;
	

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

	public String getPictUrl() {
		return pictUrl;
	}

	public void setPictUrl(String pictUrl) {
		this.pictUrl = pictUrl;
	}

	public List<String> getSmallImages() {
		return smallImages;
	}

	public void setSmallImages(List<String> smallImages) {
		this.smallImages = smallImages;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getZkFinalPrice() {
		return zkFinalPrice;
	}

	public void setZkFinalPrice(String zkFinalPrice) {
		this.zkFinalPrice = zkFinalPrice;
	}

	public String getReservePrice() {
		return reservePrice;
	}

	public void setReservePrice(String reservePrice) {
		this.reservePrice = reservePrice;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(String discountRate) {
		this.discountRate = discountRate;
	}
}
