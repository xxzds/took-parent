package com.tooklili.service.test.admin.system;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.tooklili.model.admin.leftMenu.MenuNode;
import com.tooklili.service.biz.intf.admin.system.MenuService;
import com.tooklili.service.test.BaseTest;
import com.tooklili.util.JsonFormatTool;

/**
 * @author shuai.ding
 * @date 2017年8月27日下午3:51:05
 */
public class MenuServiceTest extends BaseTest{

	@Resource
	private MenuService menuService;
	
	@Test
	public void getMenuTest(){
		
		List<MenuNode> result = menuService.getMenu();
		
		logger.info(JsonFormatTool.formatJson(JSON.toJSONString(result)));
	}
	
}
