package com.tooklili.admin.web.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 授权控制器
 * @author shuai.ding
 * @date 2017年12月15日下午4:04:08
 */
@Controller
@RequestMapping("system/auth")
public class AuthController {
	
	/**
	 * 主页
	 * @author shuai.ding
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String main(){
		return "system/auth";
	}
	

}
