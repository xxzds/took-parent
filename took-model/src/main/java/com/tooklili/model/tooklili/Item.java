package com.tooklili.model.tooklili;

/**
 * tooklili 商品模型
 * @author shuai.ding
 * @date 2017年9月11日下午2:14:07
 */
public class Item {

	private Long id;
	
	private Integer cateId;
	
	private String numIid;
	
	private String title;
	
	private String intro;
	
	private String picUrl;
	
	private String price;
	
	private String quan;
	
	private String quanUrl;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCateId() {
		return cateId;
	}

	public void setCateId(Integer cateId) {
		this.cateId = cateId;
	}

	public String getNumIid() {
		return numIid;
	}

	public void setNumIid(String numIid) {
		this.numIid = numIid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getQuan() {
		return quan;
	}

	public void setQuan(String quan) {
		this.quan = quan;
	}

	public String getQuanUrl() {
		return quanUrl;
	}

	public void setQuanUrl(String quanUrl) {
		this.quanUrl = quanUrl;
	}
}
