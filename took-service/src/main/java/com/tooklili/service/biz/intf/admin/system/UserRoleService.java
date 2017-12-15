package com.tooklili.service.biz.intf.admin.system;

import com.tooklili.model.admin.SysUserRole;
import com.tooklili.util.result.BaseResult;
import com.tooklili.util.result.ListResult;

/**
 * 用户角色服务
 * @author shuai.ding
 *
 * @date 2017年12月15日下午1:48:15
 */
public interface UserRoleService {
	
	/**
	 * 添加用户，角色关联关系
	 * @author shuai.ding
	 * @param userId      用户id
	 * @param roleId      角色id
	 * @return
	 */
	public BaseResult addUserRole(Long userId,Long roleId);
	
	/**
	 * 删除用户-角色的关联关系
	 * @author shuai.ding
	 * @param userId
	 * @return
	 */
	public BaseResult delUserRole(Long userId);
	
	/**
	 * 查询用户-角色列表
	 * @author shuai.ding
	 * @param sysUserRole
	 * @return
	 */
	public ListResult<SysUserRole> queryUserRole(SysUserRole sysUserRole);

}
