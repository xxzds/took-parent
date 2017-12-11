package com.tooklili.admin.web.controller.system;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tooklili.model.admin.SysMenu;
import com.tooklili.service.biz.intf.admin.system.MenuService;
import com.tooklili.util.result.ListResult;

/**
 * 菜单控制器
 * @author shuai.ding
 *
 * @date 2017年12月8日下午5:10:56
 */
@Controller
@RequestMapping("system/menu")
public class MenuController {
	
	@Resource
	private MenuService menuService;
	
	/**
	 * 主页
	 * @author shuai.ding
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String main(){
		return "system/menu";
	}
	
	/**
	 * 通过pid获取菜单列表
	 * @author shuai.ding
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "getMenuTree")
	@ResponseBody
	public ListResult<SysMenu> getMenuTree(Long pid){
		if(pid==null){
			pid=0L;
		}
		return menuService.getMenuTree(pid);
	}

}
