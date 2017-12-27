package com.tooklili.service.biz.intf.admin.system;

import com.tooklili.model.admin.SysRole;
import com.tooklili.util.result.BaseResult;
import com.tooklili.util.result.ListResult;
import com.tooklili.util.result.PageResult;

/**
 * 角色服务
 * @author shuai.ding
 *
 * @date 2017年12月13日下午4:41:04
 */
public interface RoleService {
	/**
	 * 分页查询角色列表
	 * @author shuai.ding
	 * @param sysRole
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public PageResult<SysRole> findRoles(SysRole sysRole,Integer currentPage,Integer pageSize);
	
	/**
	 * 查询角色列表
	 * @author shuai.ding
	 * @param sysRole
	 * @return
	 */
	public ListResult<SysRole> findRoles(SysRole sysRole);
	
	/**
	 * 增加角色
	 * @author shuai.ding
	 * @param role
	 * @return
	 */
	public BaseResult addRole(SysRole role);
	
	/**
	 * 修改角色
	 * @author shuai.ding
	 * @param role
	 * @return
	 */
	public BaseResult modifyRole(SysRole role);
	

	/**
	 * 通过角色id，删除角色  -  删除所有关联的数据
	 * @author shuai.ding
	 * @param roleId
	 * @return
	 */
	public BaseResult delRole(Long roleId);
}
