package com.tooklili.dao.db.intf.admin;

import java.util.List;

import com.tooklili.dao.db.intf.BaseDao;
import com.tooklili.model.admin.SysUserRole;

/**
 * 用户角色，关联 
 * @author shuai.ding
 *
 * @date 2017年12月15日下午1:46:56
 */
public interface SysUserRoleDao extends BaseDao<SysUserRole,Long> {
	
	/**
	 * 通过userId删除关联关系
	 * @author shuai.ding
	 * @param userId
	 * @return
	 */
	public long delUserRoleByUserId(Long userId);
	
	/**
	 * 查询用户信息和角色信息
	 * @author shuai.ding
	 * @param sysUserRole
	 * @return
	 */
	public List<SysUserRole> queryUserRole(SysUserRole sysUserRole);
	
	/**
	 * 通过角色id，查询用户角色信息
	 * @author shuai.ding
	 * @param roleId
	 * @return
	 */
	public long delUserRoleByRoleId(Long roleId);

}