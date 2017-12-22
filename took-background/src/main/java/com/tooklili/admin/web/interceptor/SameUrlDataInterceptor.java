package com.tooklili.admin.web.interceptor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.tooklili.admin.web.interceptor.annotation.SameUrlData;
import com.tooklili.service.util.MessageUtils;
import com.tooklili.util.result.BaseResult;

/**
 * 一个用户 相同url 同时提交 相同数据 验证 
 * 主要通过 session中保存到的url 和 请求参数。如果和上次相同，则是重复提交表单 
 * @author shuai.ding
 *
 * @date 2017年11月29日下午4:34:25
 */
public class SameUrlDataInterceptor extends HandlerInterceptorAdapter{
	private static final Logger LOGGER = LoggerFactory.getLogger(SameUrlDataInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {  
            HandlerMethod handlerMethod = (HandlerMethod) handler;  
            Method method = handlerMethod.getMethod();  
            SameUrlData annotation = method.getAnnotation(SameUrlData.class);  
            if (annotation != null) {  
                if(repeatDataValidator(request)){//如果重复相同数据  
                	LOGGER.warn("please don't repeat submit,[url:"+ request.getServletPath() + "]");
                	             	
                	ResponseBody responseBody = method.getAnnotation(ResponseBody.class);
                	if(responseBody!=null){  //返回json数据
                	   BaseResult result =	new BaseResult().setErrorMessage(MessageUtils.message("repeat.submit"));
                		response.getWriter().println(JSON.toJSONString(result));
                	}
                    return false;
                }
            }
        }
		return super.preHandle(request, response, handler);
	}
	
	private boolean repeatDataValidator(HttpServletRequest request){
		 String params=JSON.toJSONString(request.getParameterMap());
		 String url=request.getRequestURI();  
	     Map<String,String> map=new HashMap<String,String>();  
	     map.put(url, params);  
	     String nowUrlParams=map.toString();
	     
	     Object preUrlParams=request.getSession().getAttribute("repeatData"); 
	     
	     //如果上一个数据为null，表示没有重复提交
	     if(preUrlParams==null){
	    	 request.getSession().setAttribute("repeatData", nowUrlParams);  
	         return false;  
	     }
	     
	     //如果上次 url+数据 和本次 url+数据 不同，则不是重复提交
	     if(!preUrlParams.toString().equals(nowUrlParams)){
	    	 request.getSession().setAttribute("repeatData", nowUrlParams);  
             return false;  
	     }
		return true;
	}

}
