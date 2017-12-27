package com.tooklili.admin.web.controller.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tooklili.admin.web.interceptor.annotation.RequiresPermissions;
import com.tooklili.model.admin.SysPermission;
import com.tooklili.model.admin.SysRoleMenuPermission;
import com.tooklili.service.biz.intf.admin.system.PermissionService;
import com.tooklili.service.biz.intf.admin.system.RoleMenuPermissionService;
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
	
	@Resource
	private RoleMenuPermissionService roleMenuPermissionService;
	
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
	@RequiresPermissions("system:permission:view")
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
	@RequiresPermissions("system:permission:add")
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
	@RequiresPermissions("system:permission:modify")
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
	@RequiresPermissions("system:permission:delete")
	public BaseResult delPermission(@PathVariable Long id){
		return permissionService.delPermission(id);
	}
	
	/**
	 * 获取权限列表，同时标识选中的权限
	 * @author shuai.ding
	 * @param roleMenuId   角色-菜单ID
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryPermissons")
	public String queryPermissionList(Long roleMenuId,Model model){
		List<SysPermission> permissions = permissionService.findPermissions(null).getData();
		
		if(roleMenuId!=null){
			SysRoleMenuPermission sysRoleMenuPermission = new SysRoleMenuPermission();
			sysRoleMenuPermission.setRoleMenuId(roleMenuId);
			List<SysRoleMenuPermission> roleMenuPermissions = roleMenuPermissionService.findRoleMenuPermissions(sysRoleMenuPermission).getData();
			
			if(roleMenuPermissions!=null && roleMenuPermissions.size()>0){
				for(SysPermission sysPermission:permissions){
					for(SysRoleMenuPermission roleMenuPermission:roleMenuPermissions){
						if(roleMenuPermission.getPermissionId()==sysPermission.getId()){
							sysPermission.setSelected(true);
							continue;
						}
					}
				}
			}
	
		}
		
		model.addAttribute("permissions", permissions);
		return "system/permission_list";
	}

}
