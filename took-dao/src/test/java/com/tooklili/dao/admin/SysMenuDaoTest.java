package com.tooklili.dao.admin;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.tooklili.dao.BaseTest;
import com.tooklili.dao.intf.admin.SysMenuDao;
import com.tooklili.model.admin.SysMenu;

public class SysMenuDaoTest extends BaseTest{
	
	@Resource
	private SysMenuDao sysMenuDao;
	
	@Test
	public void getCountByPidTest(){
		try{
			int count = sysMenuDao.getCountByPid(0L);
			logger.info("个数:{}",count);
		}catch(Exception e){
			logger.error("exception",e);
		}
		
	}
	
	@Test
	public void findMenuAndOrder(){
		try{
			logger.info(JSON.toJSONString(Order.formString("menu_sort desc")));
			sysMenuDao.findMenuAndOrder(new SysMenu(), Order.formString("id desc,menu_sort desc"));
			
		}catch(Exception e){
			logger.info("exception",e);
		}
	}
}
