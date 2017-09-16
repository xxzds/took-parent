package com.tooklili.enums.tooklili;

/**
 * 商品分类枚举类
 * @author shuai.ding
 * @date 2017年9月15日下午2:10:09
 */
public enum ItemCateEnum{
	CONSTUME(35,"服装"),
	MONTHER_BOBY(36,"母婴"),
	COSMETICS(37,"化妆品"),
	OCCUPY_HOME(38,"居家"),
	SHOE_BAG_ACCESSORIES(39,"鞋包配饰"),
	GASTRONOMY(40,"美食"),
	PRODUCT_STYLE_CAR(41,"文体车品"),
	DIGITAL_HOME_APPLIANCES(42,"数码家电");
	
	private Integer code;
	private String name;
	
	private ItemCateEnum(Integer code,String name) {
		this.code = code;
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	public static ItemCateEnum valueOf(Integer code) {
		for (ItemCateEnum itemCateEnum : values()) {
			if (code != null && itemCateEnum.code == code) {
				return itemCateEnum;
			}
		}
		return null;
	}
}
