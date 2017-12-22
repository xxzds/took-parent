package com.tooklili.dao.admin;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.tooklili.dao.BaseTest;
import com.tooklili.dao.intf.admin.SysRoleMenuDao;
import com.tooklili.model.admin.SysMenu;
import com.tooklili.model.admin.SysRoleMenu;
import com.tooklili.util.JsonFormatTool;

public class SysRoleMenuDaoTest extends BaseTest{
	
	@Resource
	private SysRoleMenuDao sysRoleMenuDao;

	@Test
	public void batchAddRoleMenuTest(){
		try{
			List<SysRoleMenu> roleMenus = Lists.newArrayList();
			
			SysRoleMenu sysRoleMenu1 = new SysRoleMenu();
			sysRoleMenu1.setRoleId(1L);
			sysRoleMenu1.setMenuId(1L);
			roleMenus.add(sysRoleMenu1);
			
			SysRoleMenu sysRoleMenu2 = new SysRoleMenu();
			sysRoleMenu2.setRoleId(2L);
			sysRoleMenu2.setMenuId(2L);
			roleMenus.add(sysRoleMenu2);
			
			sysRoleMenuDao.batchAddRoleMenu(roleMenus);
		}catch(Exception e){
			logger.info("exception",e);
		}
		
	}
	
	@Test
	public void delRoleMenuByRoleIdAndMenuIdsTest(){
		List<Long> menuIds = Lists.newArrayList();
		menuIds.add(1L);
		menuIds.add(2L);
		
		Long roleId =2L;
				
		sysRoleMenuDao.delRoleMenuByRoleIdAndMenuIds(menuIds, roleId);
	}
	
	@Test
	public void queryMenuByUserIdTest(){
		List<SysMenu> list = sysRoleMenuDao.queryMenuByUserId(1L);
		logger.info(JsonFormatTool.formatJson(JSON.toJSONString(list)));
	}
}
