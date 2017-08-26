package com.tooklili.model.admin;

/**
 * 角色菜单权限
 * @author shuai.ding
 * @date 2017年8月26日下午4:34:02
 */
public class SysRoleMenuPermission {
	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 角色id
	 */
	private Long roleId;
	
	/**
	 * 菜单id
	 */
	private Long menuId;
	
	/**
	 * 权限id
	 */
	private Long permissionId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}
}
