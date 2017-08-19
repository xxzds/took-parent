package com.tooklili.service.exception;

/**
 * 业务异常，不符合业务逻辑，抛出，引起程序回滚
 *
 * 在service层抛出该异常，会被AOP捕获，并返回一个错误的Result
 * 该Result的ErrorCode为{@code CommonResultCode.BIZ_ERROR},
 * ErrorMessage为创建此BusinessException设置的message（可为空）。
 * 
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 4253539385043331850L;
    private int               code;
    private String            message;
    private static int        DEFAULT_CODE     = 1;
    private static String     DEFAULT_MESSAGE  = "默认异常信息";

    public BusinessException() {
        this.code = DEFAULT_CODE;
        this.message = DEFAULT_MESSAGE;
    }

    public BusinessException(String mesaage) {
        this.code = DEFAULT_CODE;
        this.message = mesaage;
    }

    public BusinessException(int code) {
        this.code = code;
        this.message = DEFAULT_MESSAGE;
    }

    public BusinessException(int code, String mes) {
        this.code = code;
        this.message = mes;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
