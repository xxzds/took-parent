package com.tooklili.model.admin;

/**
 * 角色-菜单表
 * @author shuai.ding
 *
 * @date 2017年12月18日上午9:41:48
 */
public class SysRoleMenu {

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
}
