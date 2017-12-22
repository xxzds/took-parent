package com.tooklili.service.biz.intf.admin.system;

import com.tooklili.util.result.BaseResult;

/**
 * 角色-菜单服务
 * @author shuai.ding
 * @date 2017年12月18日上午9:52:30
 */
public interface RoleMenuService {
	
	/**
	 * 增加角色-菜单的关联关系
	 * @author shuai.ding
	 * @param roleId    角色ID
	 * @param menuIds   菜单ID集合
	 * @return
	 */
	public BaseResult addRoleMenu(Long roleId,Long[] menuIds);

}
