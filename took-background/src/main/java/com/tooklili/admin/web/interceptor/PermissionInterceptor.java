package com.tooklili.admin.web.interceptor;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tooklili.admin.web.exception.UnauthorizedException;
import com.tooklili.admin.web.interceptor.annotation.Logical;
import com.tooklili.admin.web.interceptor.annotation.RequiresPermissions;
import com.tooklili.model.admin.SysUser;
import com.tooklili.service.biz.intf.admin.system.RoleMenuPermissionService;
import com.tooklili.service.constant.Constants;
import com.tooklili.service.util.MessageUtils;
import com.tooklili.service.util.SpringUtils;

/**
 * 权限拦截器
 * @author shuai.ding
 * @date 2017年12月22日下午1:55:17
 */
public class PermissionInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {  
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            
            RequiresPermissions requiresPermissions =null;
            
            //判断类上是否有RequiresPermissions注解
            Class<?> cla = handlerMethod.getBeanType();
            
            if( cla.isAnnotationPresent(RequiresPermissions.class)){
            	requiresPermissions = cla.getAnnotation(RequiresPermissions.class);
            }
            
            //判断方法上是否有注解(方法上注解的优先级大于类上的优先级)
            Method method = handlerMethod.getMethod();
            if(method.isAnnotationPresent(RequiresPermissions.class)){
            	requiresPermissions = method.getAnnotation(RequiresPermissions.class);
            }           
            
            if(requiresPermissions==null){
            	return true;
            }
            
           String[] value = requiresPermissions.value();
           Logical logical = requiresPermissions.logical();
           
           if(!isPermitted(value, logical)){
        	  throw new UnauthorizedException(MessageUtils.message("no.permission", StringUtils.join(value,",")));
           }
		}
		
		return super.preHandle(request, response, handler);
	}
	
	/**
	 * 判断用户，是否拥有权限集合
	 * @author shuai.ding
	 * @param permissins    权限集合
	 * @param logical       权限之间的逻辑
	 * @return
	 */
	public static boolean isPermitted(String[] permissins,Logical logical){
		if(logical == Logical.AND){
			for(String permission:permissins){
				if(!isPermitted(permission)){
					return false;
				}
			}
			return true;
		}
		
		if(logical == Logical.OR){
			for(String permission:permissins){
				if(isPermitted(permission)){
					return true;
				}
			}
			return false;
		}
		
		return false;
	}
	
	
	/**
	 * 判断用户是否拥有此权限
	 * @author shuai.ding
	 * @param permission   权限名称 
	 * @return
	 */
	public static boolean isPermitted(String permission){
		 HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		 SysUser user = (SysUser)request.getSession().getAttribute(Constants.CURRENT_USER);
		 
		 //获取当前用户的所有权限
		 RoleMenuPermissionService roleMenuPermissionService =  SpringUtils.getBean(RoleMenuPermissionService.class);
		 List<String> permissions =  roleMenuPermissionService.getPermissionsByUserId(user.getId()).getData();
		 
		 //权限完全匹配
		 if(permissions.contains(permission)){
			 return true;
		 }
		 //将权限最后一位变成*，进行比对
		 String[] strs =  permission.split(":");
		 strs[strs.length-1] = "*";		 
		 String newPermission =  StringUtils.join(strs, ":");
		 if(permissions.contains(newPermission)){
			 return true;
		 }
		 
		 return false;
	}

}
