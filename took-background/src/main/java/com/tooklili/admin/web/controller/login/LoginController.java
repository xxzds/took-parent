package com.tooklili.admin.web.controller.login;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tooklili.util.result.BaseResult;

/**
 * 登录、退出控制器
 * @author shuai.ding
 * @date 2017年9月27日下午1:58:20
 */
@Controller
public class LoginController {

	/**
	 * 去登录页面
	 * @author shuai.ding
	 * @return
	 */
	@RequestMapping("/toLogin")
	public String toLogin(){
		return "login/login";
	}
	
	/**
	 * 登录
	 * @author shuai.ding
	 * @return
	 */
	@RequestMapping("/login")
	@ResponseBody
	public BaseResult login(String userName,String password){
		BaseResult result = new BaseResult();
		
		if(StringUtils.isEmpty(userName)){
			return result.setErrorMessage("用户名不能为空");
		}
		if(StringUtils.isEmpty(password)){
			return result.setErrorMessage("密码不能为空");
		}
		
		
		
		return result;
		
	}
}
