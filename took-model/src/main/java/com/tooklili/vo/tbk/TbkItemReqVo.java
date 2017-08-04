package com.tooklili.vo.tbk;

/**
 * 淘宝客商品请求模型
 * @author shuai.ding
 * @date 2017年8月4日下午5:18:42
 */
public class TbkItemReqVo {
	
	/**
	 * 商品名称
	 */
	private String itemName;
	
	/**
	 * 商品分类
	 */
	private String itemCate;
	
	/**
	 * 当前页
	 */
	private Long pageNo=1L;
	
	/**
	 * 页面大小
	 */
	private Long pageSize = 20L;

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCate() {
		return itemCate;
	}

	public void setItemCate(String itemCate) {
		this.itemCate = itemCate;
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
}
