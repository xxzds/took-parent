package com.tooklili.util.result;

import java.io.Serializable;

/**
 * 类BaseResult.java的实现描述：接口返回的基础类，通过success判断本次调用在服务器端执行是否成功
 */
@SuppressWarnings("unchecked")
public class BaseResult implements Serializable {

    private static final long serialVersionUID = 7801949203978416054L;

    public static final BaseResult SUCCESS = new BaseResult();

    /**
     * 标识本次调用是否返回
     */
    private boolean           success;

    /**
     * 本次调用返回code，一般为错误代码
     */
    private int               code;

    /**
     * 本次调用返回的消息，一般为错误消息
     */
    private String            message;

    public BaseResult() {
        this.code = CommonResultCode.SUCCESS.code;
        this.success = true;
        this.message = CommonResultCode.SUCCESS.message;
    }

    /**
     * 快速创建错误的BaseResult，如：
     * 
     * <pre>
     * new BaseResult(CommonResultCode.DEFAULT_INTERNAL_ERROR)
     * </pre>
     */
    public BaseResult(CommonResultCode rc, Object... args) {
        setError(rc, args);
    }

    /**
     * 设置错误信息
     *
     * @param code
     * @param message
     */
    public <R extends BaseResult> R setErrorMessage(int code, String message) {
        this.code = code;
        this.success = false;
        this.message = message;
        return (R) this;
    }
    
    
    /**
     * 设置错误信息
     * @param message
     * @return
     */
    public <R extends BaseResult> R setErrorMessage(String message) {
        this.success = false;
        this.message = message;
        return (R) this;
    }

    public <R extends BaseResult> R setErrorMessage(IErrorCode code, Object... args) {
        this.code = code.getCode();
        this.success = false;
        this.message = String.format(code.getMessage(), args);
        return (R) this;
    }

    /**
     * 设置错误信息
     *
     * @param rc
     * @param args
     * @return
     * @see CommonResultCode
     */
    public <R extends BaseResult> R setError(IErrorCode errCode, Object... args) {
        this.code = errCode.getCode();
        this.success = false;
        if (args == null || args.length == 0) {
            this.message = errCode.getMessage();
        } else {
            this.message = String.format(errCode.getMessage(), args);
        }
        return (R) this;
    }

    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
