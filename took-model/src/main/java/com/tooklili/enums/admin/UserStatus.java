package com.tooklili.enums.admin;

/**
 * 用户状态
 * @author shuai.ding
 * @date 2017年11月21日下午6:00:59
 */
public enum UserStatus {
	normal("正常"), blocked("封禁");

	public final String info;

	private UserStatus( String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}
}
