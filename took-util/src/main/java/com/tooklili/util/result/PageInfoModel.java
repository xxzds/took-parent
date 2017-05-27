package com.tooklili.util.result;

import com.github.miemiedev.mybatis.paginator.domain.Paginator;

/**
 * 分页信息
 * @author shuai.ding
 *
 * @date 2017年2月3日下午4:44:09
 */
public class PageInfoModel {

	/**
	 * 当前页
	 */
	private Integer currentPage;
	
	/**
	 * 总页数
	 */
	private Integer totalPages;
	
	/**
	 * 每页的记录数
	 */
	private Integer pageRecords;
	
	/**
	 * 总记录数
	 */
	private Integer totalRecords;
	
	
	public PageInfoModel(Integer currentPage,Integer totalPages,Integer pageRecords,Integer totalRecords){
		this.currentPage=currentPage;
		this.totalPages=totalPages;
		this.pageRecords=pageRecords;
		this.totalRecords=totalRecords;
	}
	
	public PageInfoModel(Paginator paginator){
		if(paginator!=null){
			this.currentPage=paginator.getPage();
			this.totalPages=paginator.getTotalPages();
			this.pageRecords=paginator.getLimit();
			this.totalRecords=paginator.getTotalCount();
		}
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Integer getPageRecords() {
		return pageRecords;
	}

	public void setPageRecords(Integer pageRecords) {
		this.pageRecords = pageRecords;
	}

	public Integer getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}
}
