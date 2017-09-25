package com.tooklili.model.taobao;

import java.util.List;

/**
 * 查询淘宝商品详情图片模型
 * @author shuai.ding
 *
 * @date 2017年9月25日下午5:16:56
 */
public class ItemImageDetailModel {
	
	private String api;
	
	private String v;
	
	private List<String> ret;
	
	private ImagesData data;

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

	public List<String> getRet() {
		return ret;
	}

	public void setRet(List<String> ret) {
		this.ret = ret;
	}

	public ImagesData getData() {
		return data;
	}

	public void setData(ImagesData data) {
		this.data = data;
	}
	
	/**
	 * 图片数据
	 * @author shuai.ding
	 * @date 2017年9月25日下午5:16:44
	 */
	public class ImagesData{
		private List<String> pages;
		
		private List<String> images;

		public List<String> getPages() {
			return pages;
		}

		public void setPages(List<String> pages) {
			this.pages = pages;
		}

		public List<String> getImages() {
			return images;
		}

		public void setImages(List<String> images) {
			this.images = images;
		}
	}
}