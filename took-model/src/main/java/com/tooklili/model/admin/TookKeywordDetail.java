package com.tooklili.model.admin;

/**
 * 关键字信息
 * @author ding.shuai
 * @date 2018年2月11日上午10:47:34
 */
public class TookKeywordDetail {
	/**
	 * 当前页
	 */
	private Integer currentPage;
	
	/**
	 * 关键字信息（商品分类id_关键字_最大页）
	 */
	private String keyword;

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
