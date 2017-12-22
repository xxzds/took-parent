package com.tooklili.service.admin.system;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.tooklili.model.admin.SysUser;
import com.tooklili.service.biz.intf.admin.system.UserService;
import com.tooklili.util.JsonFormatTool;
import com.tooklili.util.result.BaseResult;
import com.tooklili.util.result.PageResult;
import com.tooklili.util.result.PlainResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-service-admin.xml"})
@ActiveProfiles("development")
public class UserServiceTest{
	public static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);
	@Resource
	private UserService userService;
	
	@Test
	public void findUsersTest(){
		SysUser user = new SysUser();
		int currentPage =1;
		int pageSize = 10;
		PageResult<SysUser> pageResult = userService.findUsers(user, currentPage, pageSize);
		
		logger.info(JsonFormatTool.formatJson(JSON.toJSONString(pageResult)));
		
	}
	
	@Test
	public void addUserTest(){
		SysUser user = new SysUser();
		user.setUserName("张三");
		user.setUserPassword("123");
		
		PlainResult<Long> result = userService.addUser(user);
		logger.info(JsonFormatTool.formatJson(JSON.toJSONString(result)));
	}
	
	@Test
	public void editUserTest(){
		SysUser user = new SysUser();
		user.setId(10L);
		user.setUserName("李四");
		BaseResult result = userService.editUser(user);
		logger.info(JsonFormatTool.formatJson(JSON.toJSONString(result)));
	}
}
