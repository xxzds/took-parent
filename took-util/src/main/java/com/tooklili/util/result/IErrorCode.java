package com.tooklili.util.result;

/**
 * 类IErrorCode.java的实现描述：错误代码错误信息获取接口
 */
public interface IErrorCode {
    /**
     * 获取错误码
     *
     * @return
     */
    public int getCode();

    /**
     * 获取错误消息
     *
     * @return
     */
    public String getMessage();
}
