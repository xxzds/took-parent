package com.tooklili.util.result;

import java.util.List;
import com.google.common.collect.Lists;

public class PageResult<T> extends BaseResult {

    private static final long serialVersionUID = 1453224020829563569L;

    private int               totalCount;
    private int               pageSize;
    private int               currentPage;
    
    private List<T>           data             = Lists.newArrayList();

    public PageResult(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}
