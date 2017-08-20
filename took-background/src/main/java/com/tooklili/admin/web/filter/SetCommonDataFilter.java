package com.tooklili.admin.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.tooklili.service.constant.Constants;

/**
 * 设置公共数据
 * @author shuai.ding
 *
 * @date 2017年8月20日下午4:30:49
 */
public class SetCommonDataFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		// ctx---->request.contextPath
		if (request.getAttribute(Constants.CONTEXT_PATH) == null) {	
			request.setAttribute(Constants.CONTEXT_PATH, req.getContextPath());
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
