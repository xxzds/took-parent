package com.tooklili.model.admin;

/**
 * 商品搜索关键字
 * @author ding.shuai
 * @date 2018年2月9日上午12:18:43
 */
public class TookItemSearchKeyword {

	/**
	 * 主键id
	 */
	private Long id;
	
	/**
	 * 商品分类id
	 */
	private Long itemCateId;
	
	/**
	 * 搜索关键字
	 */
	private String searchKeyword;
	
	/**
	 * 最大页
	 */
	private Integer maxPage;
	
	/**
	 * 是否可用 1-可用，2-不可用
	 */
	private Integer isAvailable;
	
	/**
	 * 商品分类
	 */
	private TookItemCate tookItemCate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getItemCateId() {
		return itemCateId;
	}

	public void setItemCateId(Long itemCateId) {
		this.itemCateId = itemCateId;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public Integer getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(Integer maxPage) {
		this.maxPage = maxPage;
	}

	public TookItemCate getTookItemCate() {
		return tookItemCate;
	}

	public void setTookItemCate(TookItemCate tookItemCate) {
		this.tookItemCate = tookItemCate;
	}

	public Integer getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
	}
}
