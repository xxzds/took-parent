package com.tooklili.enums.admin;

/**
 * 是否可用枚举类
 * @author ding.shuai
 * @date 2018年2月10日下午4:41:29
 */
public enum IsAvailableEnum {
	YES_AVAILIABLE(1,"可用"),
	NO_AVAILIABLE(2,"不可用");
	
	private final Integer code;
	private final String name;
	
	private IsAvailableEnum(Integer code,String name) {
		this.code = code;
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	public static IsAvailableEnum valueOf(Integer code) {
		for (IsAvailableEnum isAvailableEnum : values()) {
			if (code != null && isAvailableEnum.code == code) {
				return isAvailableEnum;
			}
		}
		return null;
	}

}
