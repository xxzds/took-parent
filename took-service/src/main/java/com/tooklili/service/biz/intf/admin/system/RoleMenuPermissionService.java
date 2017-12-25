package com.tooklili.service.biz.intf.admin.system;

import com.tooklili.model.admin.SysRoleMenuPermission;
import com.tooklili.util.result.BaseResult;
import com.tooklili.util.result.ListResult;

/**
 * 角色-菜单，权限服务
 * @author shuai.ding
 * @date 2017年12月18日上午9:53:26
 */
public interface RoleMenuPermissionService {
	
	/**
	 * 查询角色-菜单-权限列表
	 * @author shuai.ding
	 * @param sysRoleMenuPermission
	 * @return
	 */
	public ListResult<SysRoleMenuPermission> findRoleMenuPermissions(SysRoleMenuPermission sysRoleMenuPermission);
	
	
	/**
	 * 添加角色-菜单ID
	 * @author shuai.ding
	 * @param roleMenuId      角色-菜单ID
	 * @param permissionIds   权限列表集合
	 * @return
	 */
	public BaseResult addRoleMenuPermissions(Long roleMenuId,Long[] permissionIds);
	
	
	/**
	 * 获取用户所有权限集合
	 * @author shuai.ding
	 * @param userId       用户id
	 * @return
	 */
	public ListResult<String> getPermissionsByUserId(Long userId);
	
	/**
	 * 通过角色集合，查询角色所拥有的权限集合
	 * @author shuai.ding
	 * @param roleIds
	 * @return
	 */
	public ListResult<String> getPermissionByRoleIds(Long[] roleIds);
	
	/**
	 * 通过角色，获取所拥有的权限集合(此处引入redis缓存，加快查询速度)
	 * @author shuai.ding
	 * @param roleId
	 * @return
	 */
	public ListResult<String> getPermissionByRoleId(Long roleId);

}
