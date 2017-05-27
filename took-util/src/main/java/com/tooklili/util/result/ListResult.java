package com.tooklili.util.result;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * @author shuai.ding
 * @date 2017年2月3日下午5:22:51
 */
public class ListResult<T> extends BaseResult {

	private List<T> data = Lists.newArrayList();

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}	
}
