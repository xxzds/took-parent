package com.tooklili.admin.web.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tooklili.admin.web.controller.BaseController;

/**
 * 用户控制器
 * @author shuai.ding
 *
 * @date 2017年8月27日下午4:54:00
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController{
	
	public UserController() {
		setResourceIdentity("system:user");
	}
	
	/**
	 * 主页
	 * @author shuai.ding
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
    public String main() {
		
		if (permissionList != null) {
			this.permissionList.assertHasViewPermission();
		}	
		
		
		return "sytem/user";
    }

}
