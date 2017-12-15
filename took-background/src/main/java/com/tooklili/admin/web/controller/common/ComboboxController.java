package com.tooklili.admin.web.controller.common;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tooklili.model.admin.SysRole;
import com.tooklili.service.biz.intf.admin.system.RoleService;

/**
 * 下拉框控制器
 * @author shuai.ding
 * @date 2017年12月15日上午11:51:01
 */
@Controller
@RequestMapping("/common")
public class ComboboxController {
	
	@Resource
	private RoleService roleService;
	
	@RequestMapping("/getRoles")
	@ResponseBody
	public List<SysRole> getRoles(){
		return roleService.findRoles(null).getData();
	}

}
