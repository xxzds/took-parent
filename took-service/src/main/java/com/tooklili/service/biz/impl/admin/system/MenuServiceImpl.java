package com.tooklili.service.biz.impl.admin.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.tooklili.convert.admin.SysMenuConverter;
import com.tooklili.dao.intf.admin.SysMenuDao;
import com.tooklili.model.admin.SysMenu;
import com.tooklili.model.admin.leftMenu.MenuNode;
import com.tooklili.service.biz.intf.admin.system.MenuService;

/**
 * 菜单服务
 * @author shuai.ding
 * @date 2017年8月27日下午3:41:06
 */
@Service
public class MenuServiceImpl implements MenuService{
	
	@Resource
	private SysMenuDao sysMenuDao;

	@Override
	public List<MenuNode> getMenu() {
		List<MenuNode> MenuNodeList =Lists.newArrayList();
		
		List<SysMenu> sysMenuList =  sysMenuDao.find(null);
		
		if(sysMenuList!=null){
			MenuNodeList = Lists.transform(sysMenuList, new Function<SysMenu, MenuNode>() {

				@Override
				public MenuNode apply(SysMenu input) {
					
					return SysMenuConverter.toMenuNode(input);
				}
			});
		}
		return MenuNode.buildMenu(MenuNodeList).getChildren();
	}

}
