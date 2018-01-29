package com.tooklili.vo.tbk;

import io.swagger.annotations.ApiModelProperty;

/**
 * 淘宝客商品请求模型
 * @author shuai.ding
 * @date 2017年8月4日下午5:18:42
 */
public class TbkItemReqVo {
	
	/**
	 * 商品名称
	 */
	@ApiModelProperty("商品名称")
	private String itemName;
	
	/**
	 * 商品分类
	 */
	@ApiModelProperty("商品分类")
	private String itemCate;
	
	/**
	 * 当前页
	 */
	@ApiModelProperty("当前页")
	private Integer pageNo=1;
	
	/**
	 * 页面大小
	 */
	@ApiModelProperty("页面大小")
	private Integer pageSize = 20;

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCate() {
		return itemCate;
	}

	public void setItemCate(String itemCate) {
		this.itemCate = itemCate;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
