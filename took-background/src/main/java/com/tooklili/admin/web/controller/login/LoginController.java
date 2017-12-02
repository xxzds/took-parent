package com.tooklili.admin.web.controller.login;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tooklili.model.admin.SysUser;
import com.tooklili.service.biz.intf.admin.system.UserService;
import com.tooklili.service.constant.Constants;
import com.tooklili.util.result.BaseResult;
import com.tooklili.util.result.PlainResult;

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
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public BaseResult login(String userName,String password, HttpSession session,String ifRemember){
		BaseResult result = new BaseResult();
		
		PlainResult<SysUser> plainResult = userService.findUserByUsernameAndPassword(userName, password);
		if(!plainResult.isSuccess()){
			return result.setErrorMessage(plainResult.getMessage());
		}
		
		//记住我
		if(StringUtils.isNotEmpty(ifRemember) && "on".equals(ifRemember)){
			
		}
		
		SysUser sysUser = plainResult.getData();
		session.setAttribute(Constants.CURRENT_USER, sysUser);	
		return result;		
	}
	
	/**
	 * 登出
	 * @author shuai.ding
	 * @param session
	 * @return
	 */
	@RequestMapping("/logout")
	@ResponseBody
	public BaseResult logout(HttpSession session){
		session.removeAttribute(Constants.CURRENT_USER);
		return new BaseResult();		
	}
}
