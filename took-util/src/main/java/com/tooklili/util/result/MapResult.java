package com.tooklili.util.result;

import com.google.common.collect.Maps;

import java.util.Map;


public class MapResult<T, K> extends BaseResult {
    private static final long serialVersionUID = -1044729443054115388L;

    private Map<T, K> data = Maps.newHashMap();

    public Map<T, K> getData() {
        return data;
    }

    public void setData(Map<T, K> data) {
        this.data = data;
    }


}
