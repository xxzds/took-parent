package com.tooklili.admin.web.controller.system;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tooklili.admin.web.interceptor.annotation.RequiresPermissions;
import com.tooklili.model.admin.SysRole;
import com.tooklili.service.biz.intf.admin.system.RoleService;
import com.tooklili.util.result.BaseResult;
import com.tooklili.util.result.PageResult;

/**
 * 角色控制器
 * @author shuai.ding
 * @date 2017年12月13日下午3:24:08
 */
@Controller
@RequestMapping("system/role")
public class RoleController {
	
	@Resource
	private RoleService roleService;
	
	/**
	 * 主页
	 * @author shuai.ding
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String main(){
		return "system/role";
	}
	
	/**
	 * 角色集合
	 * @author shuai.ding
	 * @param sysRole
	 * @param page  当前页
	 * @param rows  页面大小
	 * @return
	 */
	@RequestMapping("/getRoles")
	@ResponseBody
	@RequiresPermissions("system:role:view")
	public PageResult<SysRole> getRoles(SysRole sysRole,Integer page,Integer rows){
		return roleService.findRoles(sysRole, page, rows);
	}
	
	/**
	 * 添加权限
	 * @author shuai.ding
	 * @param role
	 * @return
	 */
	@RequestMapping("/addRole")
	@ResponseBody
	@RequiresPermissions("system:role:add")
	public BaseResult addRole(SysRole role){
		return roleService.addRole(role);
	}
	
	@RequestMapping("/modifyRole")
	@ResponseBody
	@RequiresPermissions("system:role:modify")
	public BaseResult modifyRole(SysRole role){
		return roleService.modifyRole(role);
	}
	
	
	@RequestMapping("/delRole/{id}")
	@ResponseBody
	@RequiresPermissions("system:role:delete")
	public BaseResult delRole(@PathVariable Long id){
		return roleService.delRole(id);
	}

}
