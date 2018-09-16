package com.tooklili.vo.tbk;

/**
 * 搜索商品 by 淘宝客“通用物料搜索API（导购）”接口
 * @author ding.shuai
 * @date 2018年7月7日上午11:21:58
 */
public class TbkDgMaterialOptionalRequestVo {
	/** 
	* 折扣价范围上限，单位：元
	 */
	private Long endPrice;
	
	/** 
	* 是否有优惠券，设置为true表示该商品有优惠券，设置为false或不设置表示不判断这个属性
	 */
	private Boolean hasCoupon;
	
	/** 
	* 好评率是否高于行业均值
	 */
	private Boolean includeGoodRate;
	
	/** 
	* 成交转化是否高于行业均值
	 */
	private Boolean includePayRate30;
	
	/** 
	* 退款率是否低于行业均值
	 */
	private Boolean includeRfdRate;
	
	/** 
	* 是否商城商品，设置为true表示该商品是属于淘宝商城商品，设置为false或不设置表示不判断这个属性
	 */
	private Boolean isTmall;
	
	/** 
	* 是否包邮，true表示包邮，空或false表示不限
	 */
	private Boolean needFreeShipment;
	
	/** 
	* 是否加入消费者保障，true表示加入，空或false表示不限
	 */
	private Boolean needPrepay;

	/** 
	* 牛皮癣程度，取值：1:不限，2:无，3:轻微
	 */
	private Long npxLevel;
	
	/** 
	* 第几页，默认：１
	 */
	private Long pageNo;

	/** 
	* 页大小，默认20，1~100
	 */
	private Long pageSize;
	
	/** 
	* 链接形式：1：PC，2：无线，默认：１
	 */
	private Long platform;
	
	/** 
	* 查询词
	 */
	private String q;

	/** 
	* 排序_des（降序），排序_asc（升序），销量（total_sales），淘客佣金比率（tk_rate）， 累计推广量（tk_total_sales），总支出佣金（tk_total_commi），价格（price）
	 */
	private String sort;
	
	/** 
	* 店铺dsr评分，筛选高于等于当前设置的店铺dsr评分的商品0-50000之间
	 */
	private Long startDsr;

	/** 
	* 折扣价范围下限，单位：元
	 */
	private Long startPrice;

	public Long getEndPrice() {
		return endPrice;
	}

	public void setEndPrice(Long endPrice) {
		this.endPrice = endPrice;
	}

	public Boolean getHasCoupon() {
		return hasCoupon;
	}

	public void setHasCoupon(Boolean hasCoupon) {
		this.hasCoupon = hasCoupon;
	}

	public Boolean getIncludeGoodRate() {
		return includeGoodRate;
	}

	public void setIncludeGoodRate(Boolean includeGoodRate) {
		this.includeGoodRate = includeGoodRate;
	}

	public Boolean getIncludePayRate30() {
		return includePayRate30;
	}

	public void setIncludePayRate30(Boolean includePayRate30) {
		this.includePayRate30 = includePayRate30;
	}

	public Boolean getIncludeRfdRate() {
		return includeRfdRate;
	}

	public void setIncludeRfdRate(Boolean includeRfdRate) {
		this.includeRfdRate = includeRfdRate;
	}

	public Boolean getIsTmall() {
		return isTmall;
	}

	public void setIsTmall(Boolean isTmall) {
		this.isTmall = isTmall;
	}

	public Boolean getNeedFreeShipment() {
		return needFreeShipment;
	}

	public void setNeedFreeShipment(Boolean needFreeShipment) {
		this.needFreeShipment = needFreeShipment;
	}

	public Boolean getNeedPrepay() {
		return needPrepay;
	}

	public void setNeedPrepay(Boolean needPrepay) {
		this.needPrepay = needPrepay;
	}

	public Long getNpxLevel() {
		return npxLevel;
	}

	public void setNpxLevel(Long npxLevel) {
		this.npxLevel = npxLevel;
	}

	public Long getPageNo() {
		return pageNo;
	}

	public void setPageNo(Long pageNo) {
		this.pageNo = pageNo;
	}

	public Long getPageSize() {
		return pageSize;
	}

	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}

	public Long getPlatform() {
		return platform;
	}

	public void setPlatform(Long platform) {
		this.platform = platform;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Long getStartDsr() {
		return startDsr;
	}

	public void setStartDsr(Long startDsr) {
		this.startDsr = startDsr;
	}

	public Long getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(Long startPrice) {
		this.startPrice = startPrice;
	}
}
