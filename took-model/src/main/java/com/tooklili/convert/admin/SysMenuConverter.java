package com.tooklili.convert.admin;

import com.tooklili.model.admin.SysMenu;
import com.tooklili.model.admin.leftMenu.MenuNode;

/**
 * @author shuai.ding
 * @date 2017年8月26日下午6:32:04
 */
public class SysMenuConverter {
	public static MenuNode toMenuNode(SysMenu sysMenu){
		if(sysMenu==null){
			return null;
		}
		MenuNode menuNode = new MenuNode();
		menuNode.setId(sysMenu.getId());
		menuNode.setText(sysMenu.getMenuName());
		menuNode.setIconCls(sysMenu.getMenuIcon());
		menuNode.getAttributes().setUrl(sysMenu.getMenuUrl());
		menuNode.getAttributes().setParentid(sysMenu.getMenuParentId());
		menuNode.getAttributes().setSortnum(sysMenu.getMenuSort());
		
		return menuNode;
	}
}
