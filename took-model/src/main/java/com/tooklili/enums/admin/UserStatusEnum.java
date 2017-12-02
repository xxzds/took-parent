package com.tooklili.enums.admin;

/**
 * 用户状态
 * @author shuai.ding
 * @date 2017年11月21日下午6:00:59
 */
public enum UserStatusEnum {
	normal("正常"), blocked("封禁");

	public final String info;

	private UserStatusEnum( String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}
}
