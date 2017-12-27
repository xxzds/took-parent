package com.tooklili.dao.intf.admin;

import java.util.List;

import com.tooklili.dao.intf.BaseDao;
import com.tooklili.model.admin.SysPermission;
import com.tooklili.model.admin.SysRoleMenuPermission;

/**
 * 
 * @author shuai.ding
 * @date 2017年12月18日上午9:50:45
 */
public interface SysRoleMenuPermissionDao extends BaseDao<SysRoleMenuPermission,Long> {
	
	/**
	 * 通过角色-菜单id，删除角色-菜单-权限数据
	 * @author shuai.ding
	 * @param roleMenuIds
	 * @return
	 */
	public long delRoleMenuPermissionByRoleMenuIds(List<Long> roleMenuIds);
	
	
	/**
	 *通过角色-菜单id，删除角色-菜单-权限数据
	 * @author shuai.ding
	 * @param roleMenuId
	 * @return
	 */
	public long delRoleMenuPermissionByRoleMenuId(Long roleMenuId);
	
	/**
	 * 批量增加
	 * @author shuai.ding
	 * @param roleMenuPermissions
	 * @return
	 */
	public long batchAddRoleMenuPermission(List<SysRoleMenuPermission> roleMenuPermissions);
	
	/**
	 * 通过roleMenuId查询权限信息
	 * @author shuai.ding
	 * @param roleMenuId
	 * @return
	 */
	public List<SysPermission> queryPermissionByRoleMenuId(Long roleMenuId);

}