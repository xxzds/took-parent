package com.tooklili.admin.web.controller.system;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tooklili.model.admin.SysUserRole;
import com.tooklili.service.biz.intf.admin.system.UserRoleService;
import com.tooklili.util.result.ListResult;

/**
 * 用户-角色控制器
 * @author shuai.ding
 * @date 2017年12月15日下午3:18:41
 */
@Controller
@RequestMapping("/system/userRole")
public class UserRoleController {
	
	@Resource
	private UserRoleService userRoleService;
	
	/**
	 * 查询用户-角色列表
	 * @author shuai.ding
	 * @param sysUserRole
	 * @return
	 */
	@RequestMapping("/queryUserRoles")
	@ResponseBody
	public ListResult<SysUserRole> queryUserRoles(SysUserRole sysUserRole){
		return userRoleService.queryUserRole(sysUserRole);
	}

}
