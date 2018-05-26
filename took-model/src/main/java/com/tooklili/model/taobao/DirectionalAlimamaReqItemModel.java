package com.tooklili.model.taobao;

import com.tooklili.enums.common.ChannelEnum;

import io.swagger.annotations.ApiModelProperty;

/**
 * alimama 定向搜索 请求商品模型
 * @author ding.shuai
 * @date 2018年5月6日下午5:02:21
 */
public class DirectionalAlimamaReqItemModel {
	
	/**
	 * 渠道
	 */
	private ChannelEnum channel;
	
	/**
	 * 第几页
	 */
	private Integer toPage;
	
	/**
	 * 每页大小
	 */
	@ApiModelProperty(hidden=true,value="每页大小")
	private Integer perPageSize;
	
	/**
	 * 分类Id
	 */
	private Integer catIds;
	
	/**
	 * 层级
	 */
	private Integer level;
	
	/**
	 * 优惠券商品（1是）
	 */
	private Integer dpyhq;
	
	/**
	 * 排序类型
	 * 价格从高到低 sortType=3
	 * 价格从低到高 sortType=4
	 * 销量从高到低 sortType=9
	 * 收入比率从高到低 sortType=1
	 * 月推广量从高到低 sortType=5
	 * 月支出佣金从高到低 sortType=7
	 */
	private Integer sortType;
	
	/**
	 * 商品标签
	 * dpyhq 包含店铺优惠券
	 * jpmj  金牌卖家
	 * b2c   天猫旗舰店
	 */
	private String shopTag;
	
	/**
	 * 0、淘宝 1、天猫
	 */
	private Integer userType;
	
	/**
	 * 1、金牌卖家
	 */
	private Integer jpmj;
	
	/**
	 * 1、天猫旗舰店
	 */
	private Integer b2c;
	
	/**
	 * 月销量（）笔及以上
	 */
	private Integer startBiz30day;
	
	/**
	 * 收入比率 开始
	 */
	private Double startTkRate;
	
	/**
	 * 收入比率 结束
	 */
	private Double endTkRate;
	
	/**
	 * 价格 开始
	 */
	private Double startPrice;
	
	/**
	 * 价格 结束
	 */
	private Double endPrice;

	

	public ChannelEnum getChannel() {
		return channel;
	}

	public void setChannel(ChannelEnum channel) {
		this.channel = channel;
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

	public Integer getCatIds() {
		return catIds;
	}

	public void setCatIds(Integer catIds) {
		this.catIds = catIds;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getDpyhq() {
		return dpyhq;
	}

	public void setDpyhq(Integer dpyhq) {
		this.dpyhq = dpyhq;
	}

	public String getShopTag() {
		return shopTag;
	}

	public void setShopTag(String shopTag) {
		this.shopTag = shopTag;
	}

	public Integer getSortType() {
		return sortType;
	}

	public void setSortType(Integer sortType) {
		this.sortType = sortType;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getJpmj() {
		return jpmj;
	}

	public void setJpmj(Integer jpmj) {
		this.jpmj = jpmj;
	}

	public Integer getStartBiz30day() {
		return startBiz30day;
	}

	public void setStartBiz30day(Integer startBiz30day) {
		this.startBiz30day = startBiz30day;
	}

	public Integer getB2c() {
		return b2c;
	}

	public void setB2c(Integer b2c) {
		this.b2c = b2c;
	}

	public Double getStartTkRate() {
		return startTkRate;
	}

	public void setStartTkRate(Double startTkRate) {
		this.startTkRate = startTkRate;
	}

	public Double getEndTkRate() {
		return endTkRate;
	}

	public void setEndTkRate(Double endTkRate) {
		this.endTkRate = endTkRate;
	}

	public Double getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(Double startPrice) {
		this.startPrice = startPrice;
	}

	public Double getEndPrice() {
		return endPrice;
	}

	public void setEndPrice(Double endPrice) {
		this.endPrice = endPrice;
	}
}
