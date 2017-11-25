package com.tooklili.admin.web.controller.login;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tooklili.model.admin.SysUser;
import com.tooklili.service.biz.intf.admin.system.UserService;
import com.tooklili.service.constant.Constants;
import com.tooklili.service.util.MessageUtils;
import com.tooklili.util.result.BaseResult;

/**
 * 登录、退出控制器
 * @author shuai.ding
 * @date 2017年9月27日下午1:58:20
 */
@Controller
public class LoginController {
	
	@Resource
	private UserService userService;

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
	public BaseResult login(String userName,String password,HttpSession session){
		BaseResult result = new BaseResult();
		
		if(StringUtils.isEmpty(userName)){
			return result.setErrorMessage(MessageUtils.message("login.no.username"));
		}
		if(StringUtils.isEmpty(password)){
			return result.setErrorMessage(MessageUtils.message("login.no.password"));
		}
		
		SysUser sysUser = userService.findUserByUsernameAndPassword(userName, password).getData();
		if(sysUser==null){
			return result.setErrorMessage(MessageUtils.message("login.error"));
		}
		session.setAttribute(Constants.CURRENT_USER, sysUser);	
		return result;		
	}
}
