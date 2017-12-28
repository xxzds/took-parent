package com.tooklili.admin.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tooklili.admin.web.util.CookieUtils;
import com.tooklili.model.admin.SysUser;
import com.tooklili.service.biz.intf.admin.system.UserService;
import com.tooklili.service.constant.Constants;
import com.tooklili.service.util.SpringUtils;
import com.tooklili.util.result.PlainResult;

/**
 * 是否登录拦截器
 * @author shuai.ding
 * @date 2017年11月22日上午9:59:04
 */
public class LoginInterceptor extends HandlerInterceptorAdapter{
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
//		LOGGER.info("project root:{},request path：{}",request.getContextPath(),request.getServletPath());
		SysUser sysUser = (SysUser)request.getSession().getAttribute(Constants.CURRENT_USER);
		if(sysUser==null){
			//从cookie中判断用户是否进行了记住我操作
			String rememberMeCookieKey = CookieUtils.getCookieValue(Constants.REMEMBER_ME_COOKIE_KEY, request);
			
			UserService userService = SpringUtils.getBean(UserService.class);
			PlainResult<SysUser> plainResult = userService.validRememberMeCookieKey(rememberMeCookieKey);
			if(plainResult.isSuccess()){
				request.getSession().setAttribute(Constants.CURRENT_USER, plainResult.getData());
				return true;
			}
						
			//如果是ajax请求响应头会有,x-requested-with
			if(request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
				response.setHeader("sessionstatus", "timeout");//在响应头设置session状态
				response.setHeader("login", request.getContextPath()+"/toLogin");
			}else{
				response.sendRedirect(request.getContextPath()+"/toLogin");
			}
			return false;
		}
		LOGGER.info("current login user：{}",sysUser.getUserName());
		
		return true;
	}

}
