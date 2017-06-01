package com.tooklili.util.result;

import java.io.Serializable;

/**
 *  错误码
 */
public enum CommonResultCode implements IErrorCode, Serializable {
    // 成功
    SUCCESS(200, "successful"),

    // 默认错误
    DEFAULT_INTERNAL_ERROR(000, "internal server error"),

    BIZ_ERROR(900, "%s"),

    // 参数不合法
    ILEEGAL_ARGUMENT(100, "illegal parameter"),
    ILLEGAL_PARAM(101, "illegal parameter, param is %s "),
    ILLEGAL_PARAM_LENGTH(102, "illegal paramter length, param is %s "),
    ILLEGAL_PARAM_BLANK(103, "paramter %s cannot be blank"),

    // 权限不合法
    ILLEGAL_AUTH(301, "illegal auth"),
    ILLEGAL_AUTH_STATUS(302, "illegal auth status, current status is %s "),

    // 异常
    EXCEPTION(500, "server exception, msg is %s "),
    EXCEPTION_RPC(501, "remoting rpc call exception"),
    EXCEPITON_HTTP_CALL(502, "%s"),
    EXCEPTION_TYPE_NOT_MATCH(503, "type is not match, args type is %s"),
    EXCEPTION_TIMEOUT(504, "request timeout"),

    // 错误
    ERROR(-500, "error, msg is %s"),
    ERROR_DB(-501, "db operate error"),
    ERROR_INVOKE_PROXY(-502, "invoke proxy error, proxy result code is %s, msg is %s "),
    ERROR_INVOKE_MESSAGECENTER(-503, "invoke messagecenter error, messagecenter result code is %s, msg is %s "),
    ERROR_DATA_NOT_EXISTS(-504, "%s data not exist "),
    ERROR_DATA_EXISTS(-505, "%s data exist"),

    // 其他
    FAIL(600, "fail, msg is %s "),
    FAIL_BUSY(601, "service is busy"),
    FAIL_HTTP_CALL(602, "%s, status code is %d ");

    public final int    code;
    public final String message;

    CommonResultCode(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
