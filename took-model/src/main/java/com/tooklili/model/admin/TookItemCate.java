package com.tooklili.model.admin;

/**
 * 商品分类
 * @author ding.shuai
 * @date 2018年2月9日上午12:15:00
 */
public class TookItemCate {

	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 分类名称
	 */
	private String itemCateName;
	
	/**
	 * 排列位置
	 */
	private Integer itemCateSort;
	
	/**
	 * 是否可用 1-可用，2-不可用
	 */
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

	@Override
	public String toString() {
		return "TookItemCate [id=" + id + ", itemCateName=" + itemCateName + ", itemCateSort=" + itemCateSort
				+ ", isAvailable=" + isAvailable + "]";
	}
}
