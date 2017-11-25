package com.tooklili.dao.admin;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tooklili.dao.BaseTest;
import com.tooklili.dao.intf.admin.SysUserDao;
import com.tooklili.enums.admin.UserStatus;
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
		sysUser.setUserStatus(UserStatus.normal);
		
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
		try{
			SysUser sysUser1 = new SysUser();
			sysUser1.setUserName("admin");
			sysUser1.setUserStatus(UserStatus.normal);
			List<SysUser> list = sysUserDao.find(sysUser1);
			for(SysUser sysUser:list){
				logger.info(JSON.toJSONString(sysUser));
			}
		}catch(Exception e){
			logger.error("exception",e);
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
	
	@Test
	public void queryUsersByPageTest(){
		try{
			PageBounds pageBounds = new PageBounds(1, 10,Order.formString("id.desc,user_name.asc"));
			sysUserDao.queryUsersByPage(null, pageBounds);
		}catch(Exception e){
			logger.error("exception",e);
		}
	}
	
}
