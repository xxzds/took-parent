package com.tooklili.util.result;

import java.util.List;
import com.google.common.collect.Lists;

public class PageResult<T> extends BaseResult {

    private static final long serialVersionUID = 1453224020829563569L;

    private Long               totalCount;
    private Long               pageSize;
    private Long               currentPage;
    
    private List<T>           data             = Lists.newArrayList();

    public PageResult(Long currentPage, Long pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}
