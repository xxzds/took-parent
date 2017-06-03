package com.tooklili.model.dataoke;

public class SummaryData{	
	/**
	 * 更新时间
	 */
	private String update_time;
	
	/**
	 * 更新内容
	 */
	private String update_content;
	
	/**
	 * 返回结果总条数
	 */
	private String total_num;
	
	/**
	 * 接口类型
	 */
	private String api_type;

	public String getApi_type() {
		return api_type;
	}

	public void setApi_type(String api_type) {
		this.api_type = api_type;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getTotal_num() {
		return total_num;
	}

	public void setTotal_num(String total_num) {
		this.total_num = total_num;
	}

	public String getUpdate_content() {
		return update_content;
	}

	public void setUpdate_content(String update_content) {
		this.update_content = update_content;
	}
}

