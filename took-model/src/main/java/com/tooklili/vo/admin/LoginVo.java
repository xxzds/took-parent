package com.tooklili.vo.admin;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 登录vo
 * @author shuai.ding
 * @date 2017年12月28日下午3:34:42
 */
public class LoginVo {
	
	/**
	 * 用户名
	 */
	@NotEmpty(message="用户名不能为空")
	private String userName;
	
	/**
	 * 密码
	 */
	@NotEmpty(message="密码不能为空")
	private String password;
	
	/**
	 * 记住我
	 */
	private String ifRemember;
	
	/**
	 * 验证码
	 */
	@NotEmpty(message="验证码不能为空")
	private String code;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIfRemember() {
		return ifRemember;
	}

	public void setIfRemember(String ifRemember) {
		this.ifRemember = ifRemember;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
