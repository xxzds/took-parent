package com.tooklili.dao.test.admin;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.tooklili.dao.intf.admin.SysUserDao;
import com.tooklili.dao.test.BaseTest;
import com.tooklili.model.admin.SysUser;

/**
 * 用户持久化测试
 * @author shuai.ding
 * @date 2017年8月26日下午12:13:18
 */
public class SysUserDaoTest extends BaseTest{

	@Resource
	private SysUserDao sysUserDao;
	
	
	@Test
	public void insertTest(){
		SysUser sysUser = new SysUser();
		sysUser.setUserName("测试");
		sysUser.setUserPassword("123");
		sysUser.setUserSalt("1");
		sysUser.setUserCreateTime(new Date());
		sysUser.setUserStatus("666");
		
		sysUserDao.insert(sysUser);
		logger.info("主键:{}",sysUser.getId());
	}
	
	@Test
	public void insertSelective(){
		SysUser sysUser = new SysUser();
		sysUser.setUserName("选择性字段测试");
		sysUser.setUserPassword("123");
		sysUser.setUserSalt("1");
		
		sysUserDao.insertSelective(sysUser);
		logger.info("主键:{}",sysUser.getId());
	}
	
	@Test
	public void findByIdTest(){
		SysUser user = sysUserDao.findById(1L);
		logger.info(JSON.toJSONString(user));
	}
	
	@Test
	public void findTest(){
		List<SysUser> list = sysUserDao.find(null);
		for(SysUser sysUser:list){
			logger.info(JSON.toJSONString(sysUser));
		}
	}
	
	@Test
	public void deleteByIdTest(){
		sysUserDao.deleteById(8L);
	}
	
	@Test
	public void batchDeleteByIdsTest(){
		Long[] ids = new Long[]{2L,3L,4L};
		sysUserDao.batchDeleteByIds(ids);
	}
	
	@Test
	public void updateByIdTest(){
		SysUser sysUser = new SysUser();
		sysUser.setId(5L);
		sysUser.setUserName("测试更新");		
		sysUserDao.updateById(sysUser);
	}
	
	@Test
	public void updateByIdSelectiveTest(){
		SysUser sysUser = new SysUser();
		sysUser.setId(6L);
		sysUser.setUserPassword("456");
		sysUserDao.updateByIdSelective(sysUser);
	}
	
}
