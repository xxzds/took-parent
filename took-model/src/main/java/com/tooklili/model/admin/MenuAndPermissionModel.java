package com.tooklili.model.admin;

/**
 * @author shuai.ding
 * @date 2017年12月21日下午5:31:17
 */
public class MenuAndPermissionModel extends SysMenu{
	/**
	 * 角色-菜单id，用此字段查询所有权限
	 */
	private Long roleMenuId;

	public Long getRoleMenuId() {
		return roleMenuId;
	}

	public void setRoleMenuId(Long roleMenuId) {
		this.roleMenuId = roleMenuId;
	}
}
