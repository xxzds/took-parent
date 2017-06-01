package com.tooklili.util.result;

public class PlainResult<T> extends BaseResult {

    private static final long serialVersionUID = -3767132392732612883L;

    /**
     * 调用返回的数据
     */
    private T                 data;

    /**
     * @return the data
     */
    public T getData() {
        return data;
    }

    public PlainResult() {
        super();
    }

    public PlainResult(T data) {
        this.data = data;
    }

    /**
     * @param data the data to set
     */
    public void setData(T data) {
        this.data = data;
    }

}
