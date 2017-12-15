package com.tooklili.admin.web.controller.system;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tooklili.model.admin.SysPermission;
import com.tooklili.service.biz.intf.admin.system.PermissionService;
import com.tooklili.util.result.BaseResult;
import com.tooklili.util.result.PageResult;

/**
 * 权限控制器
 * @author shuai.ding
 * @date 2017年12月15日上午9:22:42
 */
@Controller
@RequestMapping("system/permission")
public class PermissionController {
	
	@Resource
	private PermissionService permissionService;
	
	/**
	 * 主页
	 * @author shuai.ding
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
    public String main() {
		return "system/permission";
    }
	
	/**
	 * 权限列表查询
	 * @author shuai.ding
	 * @param sysPermission  权限实体
	 * @param page           当前页
	 * @param rows           页面大小
	 * @return
	 */
	@RequestMapping("/permissionList")
	@ResponseBody
	public PageResult<SysPermission> permissionList(SysPermission sysPermission,Integer page,Integer rows){
		return permissionService.findPermissions(sysPermission, page, rows);
	}
	
	/**
	 * 添加权限
	 * @author shuai.ding
	 * @param sysPermission
	 * @return
	 */
	@RequestMapping("/addPermission")
	@ResponseBody
	public BaseResult addPermission(SysPermission sysPermission){
		return permissionService.addPermission(sysPermission);
	}
	
	/**
	 * 修改权限
	 * @author shuai.ding
	 * @param sysPermission
	 * @return
	 */
	@RequestMapping("/modifyPermission")
	@ResponseBody
	public BaseResult modifyPermission(SysPermission sysPermission){
		return permissionService.modifyPermission(sysPermission);
	}
	
	/**
	 * 删除权限
	 * @author shuai.ding
	 * @param id
	 * @return
	 */
	@RequestMapping("/delPermission/{id}")
	@ResponseBody
	public BaseResult delPermission(@PathVariable Long id){
		return permissionService.delPermission(id);
	}

}
