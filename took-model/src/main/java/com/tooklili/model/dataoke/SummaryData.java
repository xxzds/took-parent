package com.tooklili.model.dataoke;

public class SummaryData{
	/**
	 * 名称
	 */
	private String api_type;
	
	/**
	 * 更新时间
	 */
	private String update_time;
	
	/**
	 * 返回结果总条数
	 */
	private String total_num;
	
	/**
	 * 应用类型
	 */
	private String api_content;

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

	public String getApi_content() {
		return api_content;
	}

	public void setApi_content(String api_content) {
		this.api_content = api_content;
	}
}

