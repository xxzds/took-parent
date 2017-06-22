package com.tooklili.app.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * 设置公共属性的过滤器
 * @author shuai.ding
 *
 * @date 2017年6月21日上午11:02:27
 */
public class CommonSetFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//解决跨域问题
		HttpServletResponse httpServletResponse =(HttpServletResponse)response;
		// 指定允许其他域名访问 
		httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
		// 响应类型 
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST");
		// 响应头设置 
		httpServletResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {	
	}

}
