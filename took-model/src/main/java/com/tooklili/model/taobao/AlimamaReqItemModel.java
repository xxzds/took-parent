package com.tooklili.model.taobao;

/**
 * alimam超级搜索请求模型
 * @author shuai.ding
 *
 * @date 2017年10月15日上午11:46:03
 */
public class AlimamaReqItemModel {
	
	/**
	 * 查询条件
	 */
	private String q;
	
	/**
	 * 第几页
	 */
	private Integer toPage;
	
	/**
	 * 每页大小
	 */
	private Integer perPageSize;
	
	/**
	 * 查询类型
	 * 0 默认排序 2人气
	 */
	private Integer queryType;
	
	/**
	 * 排序类型
	 * 价格从高到低 queryType=0 ； sortType=3
	 * 价格从低到高 queryType=0 ； sortType=4
	 * 销量从高到低 queryType=0 ； sortType=9
	 * 收入比率从高到低 queryType=0 ； sortType=1
	 * 月推广量从高到低 queryType=0 ； sortType=5
	 * 月支出佣金从高到低 queryType=0 ； sortType=7
	 */
	private Integer sortType;
	
	/**
	 * 商品标签
	 * b2c 天猫旗舰店
	 * yxjh 包含营销计划
	 * dpyhq 包含店铺优惠券
	 */
	private String shopTag;
	
	/**
	 * 营销和定向计划(选中为1)
	 */
	private Integer yxjh;
	
	/**
	 * 月成交转化率高于行业均值（选中为1）
	 */
	private Integer hPayRate30;
	
	/**
	 * 包含店铺优惠券（选中为1）
	 */
	private Integer dpyhq;
	
	/**
	 * 天猫旗舰店（选中为1）
	 */
	private Integer b2c;
	
	/**
	 * 包邮（选中为1）
	 */
	private Integer freeShipment;
	
	/**
	 * 1 、天猫商品
	 */
	private Integer userType;

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public Integer getToPage() {
		return toPage;
	}

	public void setToPage(Integer toPage) {
		this.toPage = toPage;
	}

	public Integer getPerPageSize() {
		return perPageSize;
	}

	public void setPerPageSize(Integer perPageSize) {
		this.perPageSize = perPageSize;
	}

	public Integer getQueryType() {
		return queryType;
	}

	public void setQueryType(Integer queryType) {
		this.queryType = queryType;
	}

	public Integer getSortType() {
		return sortType;
	}

	public void setSortType(Integer sortType) {
		this.sortType = sortType;
	}

	public String getShopTag() {
		return shopTag;
	}

	public void setShopTag(String shopTag) {
		this.shopTag = shopTag;
	}

	public Integer gethPayRate30() {
		return hPayRate30;
	}

	public void sethPayRate30(Integer hPayRate30) {
		this.hPayRate30 = hPayRate30;
	}

	public Integer getDpyhq() {
		return dpyhq;
	}

	public void setDpyhq(Integer dpyhq) {
		this.dpyhq = dpyhq;
	}

	public Integer getB2c() {
		return b2c;
	}

	public void setB2c(Integer b2c) {
		this.b2c = b2c;
	}

	public Integer getFreeShipment() {
		return freeShipment;
	}

	public void setFreeShipment(Integer freeShipment) {
		this.freeShipment = freeShipment;
	}

	public Integer getYxjh() {
		return yxjh;
	}

	public void setYxjh(Integer yxjh) {
		this.yxjh = yxjh;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}
}
