package com.tooklili.service.biz.intf.admin.system;

import com.tooklili.model.admin.SysRole;
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
}
