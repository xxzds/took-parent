package com.tooklili.admin.web.controller.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tooklili.model.admin.SysMenu;
import com.tooklili.model.admin.easyui.MenuTreeGridModel;
import com.tooklili.service.biz.intf.admin.system.MenuService;
import com.tooklili.util.result.BaseResult;
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
	 * @param id   父节点的id
	 * @return
	 */
	@RequestMapping(value = "getMenuTree")
	@ResponseBody
	public List<MenuTreeGridModel> getMenuTree(Long id){
		if(id==null){
			id=0L;
		}
		ListResult<MenuTreeGridModel> listResult = menuService.getMenuTree(id);
		return listResult.getData();
	}
	
	/**
	 * 增加菜单
	 * @author shuai.ding
	 * @return
	 */
	@RequestMapping(value = "addMenu")
	@ResponseBody
	public BaseResult addMenu(SysMenu sysMenu){
		return menuService.addMenu(sysMenu);
	}
	
	/**
	 * 修改菜单
	 * @author shuai.ding
	 * @param sysMenu
	 * @return
	 */
	@RequestMapping(value = "modifyMenu")
	@ResponseBody
	public BaseResult modifyMenu(SysMenu sysMenu){
		return menuService.modifyMenu(sysMenu);
	}
	
	/**
	 * 删除菜单
	 * @author shuai.ding
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delMenu/{id}")
	@ResponseBody
	public BaseResult delMenu(@PathVariable Long id){
		return menuService.delMenu(id);
	}

}
