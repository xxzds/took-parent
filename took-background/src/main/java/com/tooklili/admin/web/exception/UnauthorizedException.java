package com.tooklili.admin.web.exception;

/**
 * 权限认证失败异常
 * @author shuai.ding
 * @date 2017年11月22日上午9:43:47
 */
public class UnauthorizedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnauthorizedException(String message) {
		super(message);
	}

}
