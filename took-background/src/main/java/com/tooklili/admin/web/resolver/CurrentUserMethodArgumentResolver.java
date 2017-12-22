package com.tooklili.admin.web.resolver;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.tooklili.admin.web.resolver.annotation.CurrentUser;

/**
 * 通过注解CurrentUser，绑定当前用户
 * @author shuai.ding
 * @date 2017年11月28日下午5:55:04
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver{

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		if (parameter.hasParameterAnnotation(CurrentUser.class)) {
            return true;
        }
        return false;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		CurrentUser currentUserAnnotation = parameter.getParameterAnnotation(CurrentUser.class);
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		return request.getSession().getAttribute(currentUserAnnotation.value());
	}

}
