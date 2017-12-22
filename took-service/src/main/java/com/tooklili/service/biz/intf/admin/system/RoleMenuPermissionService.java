package com.tooklili.service.biz.intf.admin.system;

import com.tooklili.model.admin.SysRoleMenuPermission;
import com.tooklili.model.admin.SysUser;
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
	 * @param user           用户实体
	 * @return
	 */
	public ListResult<String> getPermissionsByUser(SysUser user);

}
