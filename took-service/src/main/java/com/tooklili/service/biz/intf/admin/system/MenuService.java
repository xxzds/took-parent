package com.tooklili.service.biz.intf.admin.system;

import java.util.List;

import com.tooklili.model.admin.leftMenu.MenuNode;

/**
 * 菜单服务
 * @author shuai.ding
 * @date 2017年8月27日下午3:40:47
 */
public interface MenuService {

	public List<MenuNode> getMenu();
}
