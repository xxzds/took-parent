package com.tooklili.admin.web.controller.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tooklili.model.admin.leftMenu.MenuNode;
import com.tooklili.service.biz.intf.admin.system.MenuService;
import com.tooklili.service.biz.intf.admin.system.RoleMenuPermissionService;
import com.tooklili.service.biz.intf.admin.system.RoleMenuService;
import com.tooklili.util.result.BaseResult;

/**
 * 授权控制器
 * @author shuai.ding
 * @date 2017年12月15日下午4:04:08
 */
@Controller
@RequestMapping("system/auth")
public class AuthController {
	
	@Resource
	private MenuService menuService;
	
	@Resource
	private RoleMenuService roleMenuService;
	
	@Resource
	private RoleMenuPermissionService roleMenuPermissionService;
	
	/**
	 * 主页
	 * @author shuai.ding
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String main(){
		return "system/auth";
	}
	
	
	/**
	 * 通过角色，查看菜单列表，标识角色选中的菜单
	 * @author shuai.ding
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/getMenuByRoleId")
	@ResponseBody
	public List<MenuNode> getMenuByRoleId(Long roleId){
		return menuService.getMenuByRoleId(roleId);
	}
	
	
	/**
	 * 增加角色-菜单 关联关系
	 * @author shuai.ding
	 * @param roleId
	 * @param menuIds
	 * @return
	 */
	@RequestMapping("/addRoleMenu")
	@ResponseBody
	public BaseResult addRoleMenu(Long roleId,Long[] menuIds){
		return roleMenuService.addRoleMenu(roleId, menuIds);
	}
	
	/**
	 * 增加 角色-菜单-权限
	 * @author shuai.ding
	 * @param roleMenuId      角色-菜单id
	 * @param permissionIds   权限集合
	 * @return
	 */
	@RequestMapping("/addRoleMenuPermission")
	@ResponseBody
	public BaseResult addRoleMenuPermission(Long roleMenuId,Long[] permissionIds){
		return roleMenuPermissionService.addRoleMenuPermissions(roleMenuId, permissionIds);
	}

}
