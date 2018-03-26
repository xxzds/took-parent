package com.tooklili.model.admin;

import io.swagger.annotations.ApiModelProperty;

/**
 * 商品分类
 * @author ding.shuai
 * @date 2018年2月9日上午12:15:00
 */
public class TookItemCate {

	/**
	 * 主键
	 */
	@ApiModelProperty("主键")
	private Long id;
	
	/**
	 * 分类名称
	 */
	@ApiModelProperty("分类名称")
	private String itemCateName;
	
	/**
	 * 排列位置
	 */
	@ApiModelProperty("排列位置")
	private Integer itemCateSort;
	
	/**
	 * 分类图标url
	 */
	@ApiModelProperty("分类图标url")
	private String itemCateIconUrl;
	
	/**
	 * 是否可用 1-可用，2-不可用
	 */
	@ApiModelProperty("是否可用 1-可用，2-不可用")
	private Integer isAvailable;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getItemCateName() {
		return itemCateName;
	}

	public void setItemCateName(String itemCateName) {
		this.itemCateName = itemCateName;
	}

	public Integer getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Integer getItemCateSort() {
		return itemCateSort;
	}

	public void setItemCateSort(Integer itemCateSort) {
		this.itemCateSort = itemCateSort;
	}

	public String getItemCateIconUrl() {
		return itemCateIconUrl;
	}

	public void setItemCateIconUrl(String itemCateIconUrl) {
		this.itemCateIconUrl = itemCateIconUrl;
	}

	@Override
	public String toString() {
		return "TookItemCate [id=" + id + ", itemCateName=" + itemCateName + ", itemCateSort=" + itemCateSort
				+ ", itemCateIconUrl=" + itemCateIconUrl + ", isAvailable=" + isAvailable + "]";
	}
}
