package com.tooklili.model.tooklili;

/**
 * tooklili 商品模型-所有字段
 * @author shuai.ding
 *
 * @date 2017年10月18日下午5:24:08
 */
public class ItemModel extends Item{
	private Integer ordId = 9999;
	
	/**
	 * 父id
	 */
	private Integer origId = 1;
	
	/**
	 * 卖家昵称
	 */
	private String nick;
	
	/**
	 * 卖家id
	 */
	private String sellerId;
	
	/**
	 * 操作人的用户id
	 */
	private Integer uId=1;
	
	/**
	 * 操作人的用户名
	 */
	private String uName="admin";
	
	/**
	 * 商品链接
	 */
	private String clickUrl;
	
	/**
	 * 是否有券 0 无 1有
	 */
	private Integer isq;
	
	/**
	 * 商品详情地址
	 */
	private String itemUrl;
	
	/**
	 * 佣金
	 */
	private String commission;
	
	/**
	 * 佣金比率
	 */
	private String commissionRate;
	
	/**
	 * ?暂且理解为剩余的券数
	 */
	private String couponRate;
	
	/**
	 * 使用券的条件
	 */
	private String quanCondition;
	
	private String cu ="";
	
	/**
	 * 是否审核通过，默认为1 通过
	 */
	private Integer pass = 1;

	public Integer getOrdId() {
		return ordId;
	}

	public void setOrdId(Integer ordId) {
		this.ordId = ordId;
	}

	public Integer getOrigId() {
		return origId;
	}

	public void setOrigId(Integer origId) {
		this.origId = origId;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public Integer getuId() {
		return uId;
	}

	public void setuId(Integer uId) {
		this.uId = uId;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getClickUrl() {
		return clickUrl;
	}

	public void setClickUrl(String clickUrl) {
		this.clickUrl = clickUrl;
	}

	public Integer getIsq() {
		return isq;
	}

	public void setIsq(Integer isq) {
		this.isq = isq;
	}

	public String getItemUrl() {
		return itemUrl;
	}

	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}

	public String getCommission() {
		return commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}

	public String getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(String commissionRate) {
		this.commissionRate = commissionRate;
	}

	public String getCouponRate() {
		return couponRate;
	}

	public void setCouponRate(String couponRate) {
		this.couponRate = couponRate;
	}

	public String getCu() {
		return cu;
	}

	public void setCu(String cu) {
		this.cu = cu;
	}

	public String getQuanCondition() {
		return quanCondition;
	}

	public void setQuanCondition(String quanCondition) {
		this.quanCondition = quanCondition;
	}

	public Integer getPass() {
		return pass;
	}

	public void setPass(Integer pass) {
		this.pass = pass;
	}
}
