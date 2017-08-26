package com.tooklili.model.admin;

/**
 * 图标
 * @author shuai.ding
 * @date 2017年8月26日下午3:57:10
 */
public class SysIcon {

	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 图标标识符
	 */
	private String iconIdentity;
	
	/**
	 * 图标大小
	 */
	private String iconSize;
	
	/**
	 * 备注
	 */
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIconIdentity() {
		return iconIdentity;
	}

	public void setIconIdentity(String iconIdentity) {
		this.iconIdentity = iconIdentity;
	}

	public String getIconSize() {
		return iconSize;
	}

	public void setIconSize(String iconSize) {
		this.iconSize = iconSize;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
