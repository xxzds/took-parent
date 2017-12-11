package com.tooklili.service.biz.intf.admin.system;

import java.util.List;

import com.tooklili.model.admin.SysMenu;
import com.tooklili.model.admin.leftMenu.MenuNode;
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
	public List<MenuNode> getMenu();
	
	/**
	 * 通过pid获取菜单列表
	 * @author shuai.ding
	 * @param pid
	 * @return
	 */
	public ListResult<SysMenu> getMenuTree(Long pid);
}
