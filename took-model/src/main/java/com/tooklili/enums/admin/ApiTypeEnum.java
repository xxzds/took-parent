package com.tooklili.enums.admin;

/**
 * 接口枚举类
 * @author ding.shuai
 * @date 2018年2月11日下午10:48:40
 */
public enum ApiTypeEnum {
	TBK_API(1,"淘宝客"),
	SUPER_SEARCH_API(2,"超级搜");
	
	private final Integer type;
	private final String name;
	
	private ApiTypeEnum(Integer type,String name) {
		this.type = type;
		this.name = name;
	}
	
	public Integer getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public static ApiTypeEnum valueOf(Integer type) {
		for (ApiTypeEnum apiTypeEnum : values()) {
			if (type != null && apiTypeEnum.type == type) {
				return apiTypeEnum;
			}
		}
		return null;
	}
	
	

}
