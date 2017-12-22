package com.tooklili.service.biz.intf.admin.system;

import com.tooklili.model.admin.SysPermission;
import com.tooklili.util.result.BaseResult;
import com.tooklili.util.result.ListResult;
import com.tooklili.util.result.PageResult;

public interface PermissionService {
	/**
	 * 分页查询权限列表
	 * @author shuai.ding
	 * @param sysPermission 权限实体
	 * @param currentPage   当前页
	 * @param pageSize      页面大小
	 * @return
	 */
	public PageResult<SysPermission> findPermissions(SysPermission sysPermission,Integer currentPage,Integer pageSize);
	
	
	/**
	 * 查询权限列表
	 * @author shuai.ding
	 * @param sysPermission
	 * @return
	 */
	public ListResult<SysPermission> findPermissions(SysPermission sysPermission);
	
	/**
	 * 添加权限
	 * @author shuai.ding
	 * @param sysPermission
	 * @return
	 */
	public BaseResult addPermission(SysPermission sysPermission);
	
	/**
	 * 修改权限
	 * @author shuai.ding
	 * @param sysPermission
	 * @return
	 */
	public BaseResult modifyPermission(SysPermission sysPermission);
	
	/**
	 * 删除
	 * @author shuai.ding
	 * @param id
	 * @return
	 */
	public BaseResult delPermission(Long id);

}
