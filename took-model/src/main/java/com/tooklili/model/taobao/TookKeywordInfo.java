package com.tooklili.model.taobao;

/**
 * 关键字搜索信息
 * @author ding.shuai
 * @date 2018年2月12日下午5:43:21
 */
public class TookKeywordInfo {
	
	/**
	 * 分类id
	 */
	private Integer cateId;
	
	/**
	 * 当前页
	 */
	private Integer currentPage;
	
	/**
	 * 关键字
	 */
	private String keyword;

	public Integer getCateId() {
		return cateId;
	}

	public void setCateId(Integer cateId) {
		this.cateId = cateId;
	}

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
