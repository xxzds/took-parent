package com.tooklili.service.biz.impl.test;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tooklili.dao.db.intf.admin.SysUserDao;
import com.tooklili.model.admin.SysUser;
import com.tooklili.service.biz.intf.test.TestService;

@Service
public class TestServiceImpl implements TestService{

	@Resource
	private  SysUserDao sysUserDao;
	
	@Override
	@Transactional
	public void testTransaction() {
		
		SysUser sysUser = new SysUser();
		sysUser.setUserName("test-demo");
		sysUser.setUserPassword("123");
		sysUserDao.insert(sysUser);
		
		throw new RuntimeException("insert user exception");
		
	}

	@Override
	public void insertUser() {
		SysUser sysUser = new SysUser();
		sysUser.setUserName("test-demo");
		sysUser.setUserPassword("123");
		sysUserDao.insert(sysUser);		
	}

}
