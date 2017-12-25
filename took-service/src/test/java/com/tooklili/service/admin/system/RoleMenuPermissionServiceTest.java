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
import com.tooklili.service.biz.intf.admin.system.RoleMenuPermissionService;
import com.tooklili.util.JsonFormatTool;
import com.tooklili.util.result.ListResult;

/**
 * 
 * @author shuai.ding
 * @date 2017年12月22日上午10:35:16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-service-admin.xml"})
@ActiveProfiles("development")
public class RoleMenuPermissionServiceTest {
	public static final Logger logger = LoggerFactory.getLogger(RoleMenuPermissionServiceTest.class);
	
	@Resource
	private RoleMenuPermissionService roleMenuPermissionService;
	
	/**
	 * 获取用户所有权限集合
	 * @author shuai.ding
	 */
	@Test
	public void getPermissionsByUserTest(){
		ListResult<String> result =  roleMenuPermissionService.getPermissionsByUserId(1L);
		logger.info(JsonFormatTool.formatJson(JSON.toJSONString(result)));
	}

}
