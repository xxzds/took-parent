package com.tooklili.model.admin;

/**
 * 权限
 * @author shuai.ding
 * @date 2017年8月26日下午4:05:35
 */
public class SysPermission {

	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 权限名称
	 */
	private String permissionName;
	
	/**
	 * 权限标识
	 */
	private String permissionIdentify;
	
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

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getPermissionIdentify() {
		return permissionIdentify;
	}

	public void setPermissionIdentify(String permissionIdentify) {
		this.permissionIdentify = permissionIdentify;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
