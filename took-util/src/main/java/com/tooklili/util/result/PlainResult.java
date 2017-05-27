package com.tooklili.util.result;

/**
 * @author shuai.ding
 * @date 2017年2月3日下午5:22:03
 */
public class PlainResult<T> extends BaseResult{

	/**
	 * 对象数据
	 */
	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
