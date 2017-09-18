package com.tooklili.enums.tooklili;

/**
 * 商品类别
 * @author shuai.ding
 * @date 2017年9月18日上午10:07:37
 */
public enum ItemTypeEnum {
	TIANMAO("B","天猫"),
	TAOBAO("C"," 淘宝");
	
	private String type;
	private String name;
	
	private ItemTypeEnum(String type,String name) {
		this.type = type;
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}
}
