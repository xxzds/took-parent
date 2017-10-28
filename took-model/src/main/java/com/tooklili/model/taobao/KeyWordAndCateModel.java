package com.tooklili.model.taobao;

/**
 * 关键字分类实体
 * @author ding.shuai
 * @date 2017年10月28日上午9:56:34
 */
public class KeyWordAndCateModel implements Cloneable{
	
	/**
	 * 关键字
	 */
	private String keyWord;
	
	/**
	 * 分类
	 */
	private Integer cate;
	
	/**
	 * 查询第几页
	 */
	private Integer currentPage;
	
	public KeyWordAndCateModel() {
	}
	
	public KeyWordAndCateModel(String keyWord,Integer cate,Integer currentPage) {
		this.keyWord = keyWord;
		this.cate = cate;
		this.currentPage = currentPage;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public Integer getCate() {
		return cate;
	}

	public void setCate(Integer cate) {
		this.cate = cate;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	
	@Override
	public KeyWordAndCateModel clone(){
		try {
			return (KeyWordAndCateModel)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
