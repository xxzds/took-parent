package com.tooklili.enums.admin;

/**
 * 用户是否逻辑删除
 * @author shuai.ding
 *
 * @date 2017年11月27日下午1:51:46
 */
public enum UserDelEnum {
	USER_NO_DEL(0,"未删除"),
	USER_DEL(1,"删除");
	
	private final Integer code;
	private final String name;
	
	private UserDelEnum(Integer code,String name) {
		this.code = code;
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	public static UserDelEnum valueOf(Integer code) {
		for (UserDelEnum userDelEnum : values()) {
			if (code != null && userDelEnum.code == code) {
				return userDelEnum;
			}
		}
		return null;
	}
}
