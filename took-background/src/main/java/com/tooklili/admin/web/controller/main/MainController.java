package com.tooklili.admin.web.controller.main;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tooklili.admin.web.resolver.annotation.CurrentUser;
import com.tooklili.model.admin.SysUser;
import com.tooklili.model.admin.leftMenu.MenuNode;
import com.tooklili.service.biz.intf.admin.system.MenuService;

/**
 * 主页控制器
 * @author shuai.ding
 * @date 2017年8月20日下午4:01:15
 */
@Controller
public class MainController {
	
	@Resource
	private MenuService menuService;

	@RequestMapping(value= {"/","/main"})
	public String main() {
		return "main/index";
	}
	
	
	@RequestMapping("/getMenu")
	@ResponseBody
	public List<MenuNode> getMenu(@CurrentUser SysUser user) throws IOException{		
		List<MenuNode> result = menuService.getMenu(user.getId());		
		return result;

	}
}
