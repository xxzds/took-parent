package com.tooklili.admin.web.method;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tooklili.admin.web.method.annotation.AvoidDuplicateSubmission;
import com.tooklili.service.util.MessageUtils;
import com.tooklili.util.UUIDUtils;

/**
 * <p>
 * 防止重复提交过滤器
 * </p>
 *
 * @author ding.shuai
 * @date 2016年7月21日下午1:26:18
 */

public class AvoidDuplicateSubmissionInterceptor extends HandlerInterceptorAdapter {
	 private static Logger LOG = LoggerFactory.getLogger(AvoidDuplicateSubmissionInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
    	//不经过拦截，默认servlet和静态资源文件
//    	if(handler instanceof DefaultServletHttpRequestHandler || handler instanceof ResourceHttpRequestHandler){
//    		return true;
//    	}    	
    	
    	if(!(handler instanceof HandlerMethod)){
    		return true;
    	}
    	 HandlerMethod handlerMethod = (HandlerMethod) handler;
         Method method = handlerMethod.getMethod();

         AvoidDuplicateSubmission annotation = method.getAnnotation(AvoidDuplicateSubmission.class);
         if (annotation != null) {
             boolean needSaveSession = annotation.needSaveToken();
             if (needSaveSession) {
                 request.getSession(false).setAttribute("token", UUIDUtils.generateUuid32());
             }

             boolean needRemoveSession = annotation.needRemoveToken();
             if (needRemoveSession) {
                 if (isRepeatSubmit(request)) {
                     LOG.warn("please don't repeat submit,[url:"+ request.getServletPath() + "]");
                     
                     request.setAttribute("invalidtoken", MessageUtils.message("repeat.submit.no.valid"));
                     request.getRequestDispatcher("/repeatsubmit").forward(request, response);
                     return false;
                 }
                 request.getSession(false).removeAttribute("token");
             }
         }
        return true;
    }

    private boolean isRepeatSubmit(HttpServletRequest request) {
        String serverToken = (String) request.getSession(false).getAttribute("token");
        if (serverToken == null) {
            return true;
        }
        String clinetToken = request.getParameter("token");
        if (clinetToken == null) {
            return true;
        }
        if (!serverToken.equals(clinetToken)) {
            return true;
        }
        return false;
    }

}
