package com.tooklili.dao.admin;

import javax.annotation.Resource;

import org.junit.Test;

import com.tooklili.dao.BaseTest;
import com.tooklili.dao.intf.admin.SysMenuDao;

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
}
