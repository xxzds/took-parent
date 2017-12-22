package com.tooklili.service.biz.intf.admin.system;

import java.util.List;

import com.tooklili.model.admin.SysMenu;
import com.tooklili.model.admin.easyui.MenuTreeGridModel;
import com.tooklili.model.admin.leftMenu.MenuNode;
import com.tooklili.util.result.BaseResult;
import com.tooklili.util.result.ListResult;

/**
 * 菜单服务
 * @author shuai.ding
 * @date 2017年8月27日下午3:40:47
 */
public interface MenuService {

	/**
	 * 获取菜单列表
	 * @author shuai.ding
	 * @return
	 */
	
	/**
	 * 通过用户ID,查询此用户所拥有的菜单，如果用户id为空，查询所有菜单
	 * @author shuai.ding
	 * @param userId
	 * @return
	 */
	public List<MenuNode> getMenu(Long userId);
	
	/**
	 * 通过角色，查看菜单列表，标识角色选中的菜单
	 * @author shuai.ding
	 * @param roleId
	 * @return
	 */
	public List<MenuNode> getMenuByRoleId(Long roleId);
	
	/**
	 * 通过pid获取菜单列表
	 * @author shuai.ding
	 * @param pid
	 * @return
	 */
	public ListResult<MenuTreeGridModel> getMenuTree(Long pid);
	
	/**
	 * 增加菜单
	 * @author shuai.ding
	 * @param sysMenu
	 * @return
	 */
	public BaseResult addMenu(SysMenu sysMenu);
	
	/**
	 * 修改菜单信息
	 * @author shuai.ding
	 * @param sysMenu
	 * @return
	 */
	public BaseResult modifyMenu(SysMenu sysMenu);
	
	/**
	 * 删除菜单，如果此菜单有子节点，删除所有子节点
	 * @author shuai.ding
	 * @param id  菜单ID
	 * @return
	 */
	public BaseResult delMenu(Long id);
}
