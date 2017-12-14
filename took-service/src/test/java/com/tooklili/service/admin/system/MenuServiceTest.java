package com.tooklili.service.admin.system;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.tooklili.model.admin.easyui.MenuTreeGridModel;
import com.tooklili.model.admin.leftMenu.MenuNode;
import com.tooklili.service.biz.intf.admin.system.MenuService;
import com.tooklili.util.JsonFormatTool;
import com.tooklili.util.result.ListResult;

/**
 * @author shuai.ding
 * @date 2017年8月27日下午3:51:05
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-service-admin.xml"})
@ActiveProfiles("admin_development")
public class MenuServiceTest{
	public static final Logger logger = LoggerFactory.getLogger(MenuServiceTest.class);

	@Resource
	private MenuService menuService;
	
	@Test
	public void getMenuTest(){
		
		List<MenuNode> result = menuService.getMenu();
		
		logger.info(JsonFormatTool.formatJson(JSON.toJSONString(result)));
	}
	
	@Test
	public void getMenuTreeTest(){
		try{
			ListResult<MenuTreeGridModel> result = menuService.getMenuTree(1L);
			logger.info(JsonFormatTool.formatJson(JSON.toJSONString(result)));
		}catch(Exception e){
			logger.error("exception",e);
		}
	}
	
}
