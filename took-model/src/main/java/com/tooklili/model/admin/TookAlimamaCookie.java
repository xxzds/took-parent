package com.tooklili.model.admin;

import org.apache.commons.lang.StringUtils;

/**
 * alimama cookie表
 * @author ding.shuai
 * @date 2018年2月18日上午11:33:52
 */
public class TookAlimamaCookie {
	
	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * alimama cookie
	 */
	private String alimamaCookie;
	
	/**
	 * 1-可用，2-不可用
	 */
	private Integer isAvailable;
	
	/**
	 * 推广pid
	 */
	private String pid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlimamaCookie() {
		return alimamaCookie;
	}

	public void setAlimamaCookie(String alimamaCookie) {
		this.alimamaCookie = alimamaCookie;
	}

	public Integer getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
	/**
	 * 获取_tb_token_
	 * @return
	 */
	public String getTbToken() {
		String tbToken = null;
		
		if(StringUtils.isNotEmpty(this.alimamaCookie)) {
			String[] temps = this.alimamaCookie.split(";");
			for(String temp :temps) {
				String[] temps2 = temp.split("=");
				if("_tb_token_".equals(temps2[0].trim())) {
					tbToken = temps2[1];
					break;
				}
			}
		}
		return tbToken;
	}

	@Override
	public String toString() {
		return "TookAlimamaCookie [id=" + id + ", name=" + name + ", alimamaCookie=" + alimamaCookie + ", isAvailable="
				+ isAvailable + "]";
	}
}
