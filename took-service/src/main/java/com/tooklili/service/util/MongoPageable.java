package com.tooklili.service.util;

import java.io.Serializable;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * mongodb的分页实现
 * 当前页，默认从1开始
 * @author shuai.ding
 * @date 2017年9月23日下午5:18:14
 */
public class MongoPageable implements Pageable,Serializable{
	private static final long serialVersionUID = 7407824419102761198L;

	//当前页
	private final int page;
	//页面大小
	private final int size;
	
	private final Sort sort;
	
	public MongoPageable() {
		this(1, 10, null);		
	}
	
	public MongoPageable(int page,int size) {
		this(page, size, null);		
	}
	
	public MongoPageable(int page,int size,Sort sort) {
		if (page < 1) {
			throw new IllegalArgumentException("Page index must not be less than one!");
		}

		if (size < 1) {
			throw new IllegalArgumentException("Page size must not be less than one!");
		}

		this.page = page;
		this.size = size;
		this.sort=sort;
	}
	
	@Override
	public int getPageNumber() {
		return page;
	}

	@Override
	public int getPageSize() {
		return size;
	}

	@Override
	public int getOffset() {
		return (page-1)*size;
	}

	@Override
	public Sort getSort() {
		return sort;
	}

	@Override
	public Pageable next() {
		return new PageRequest(getPageNumber() + 1, getPageSize(), getSort());
	}

	@Override
	public Pageable previousOrFirst() {
		return getPageNumber() == 1 ? this : new PageRequest(getPageNumber() - 1, getPageSize(), getSort());
	}

	@Override
	public Pageable first() {
		return new PageRequest(1, getPageSize(), getSort());
	}

	@Override
	public boolean hasPrevious() {
		return page > 1;
	}
	
	@Override
	public boolean equals(final Object obj) {

		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MongoPageable)) {
			return false;
		}

		MongoPageable that = (MongoPageable) obj;

		boolean sortEqual = this.sort == null ? that.sort == null : this.sort.equals(that.sort);

		return super.equals(that) && sortEqual;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return 31 * super.hashCode() + (null == sort ? 0 : sort.hashCode());
	}

	/* 
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Page request [number: %d, size %d, sort: %s]", getPageNumber(), getPageSize(),
				sort == null ? null : sort.toString());
	}

}
