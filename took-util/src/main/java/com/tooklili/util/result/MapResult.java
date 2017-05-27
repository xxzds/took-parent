package com.tooklili.util.result;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * @author shuai.ding
 * @date 2017年2月3日下午5:23:19
 */
public class MapResult<K,V> extends BaseResult{
    private Map<K,V> data =Maps.newHashMap();

	public Map<K, V> getData() {
		return data;
	}

	public void setData(Map<K, V> data) {
		this.data = data;
	}
}
