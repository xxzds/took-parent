package com.tooklili.enums.common;

/**
 * 定向超级搜索，商品分类
 * @author ding.shuai
 * @date 2018年5月13日下午5:39:25
 */
public enum AlimamaItemCateEnum {
	NV_ZHUANG(1,"女装"),
	NAN_ZHUANG(2,"男装"),
	XIE_BAO(3,"鞋包"),
	ZHU_BAO_PEI_SHI(4,"珠宝配饰"),
	YUN_DONG_HU_WAI(5,"运动户外"),
	MEI_ZHUANG(6,"美妆"),
	MU_YING(7,"母婴"),
	SHI_PIN(8,"食品"),
	NEI_YI(9,"内衣"),
	SHU_MA(10,"数码"),
	JIA_ZHUANG(11,"家装"),
	JIA_JU_YONG_PIN(12,"家居用品"),
	JIA_DIAN(13,"家电"),
	QI_CHE(14,"汽车"),
	SHE_HUO_FU_WU(15,"生活服务"),
	TU_SHU_YING_XIANG(16,"图书影像"),
	QI_TA(64,"其它");
	
	
	
	private final Integer cateId;
	private final String cateName;
	
	private AlimamaItemCateEnum(Integer cateId,String cateName) {
		this.cateId = cateId;
		this.cateName = cateName;
	}

	public Integer getCateId() {
		return cateId;
	}

	public String getCateName() {
		return cateName;
	}
}
