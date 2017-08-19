package com.tooklili.app.web.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * 设置公共属性的过滤器
 * @author shuai.ding
 *
 * @date 2017年6月21日上午11:02:27
 */
public class CommonSetFilter implements Filter{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonSetFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest =(HttpServletRequest)request;
		HttpServletResponse httpServletResponse =(HttpServletResponse)response;
		
		//打印请求地址和参数
		Map<String, String[]> nameValue = httpServletRequest.getParameterMap();		
		LOGGER.info("\r\n请求地址:{}\r\n参数:{}",httpServletRequest.getRequestURL(),JSON.toJSONString(nameValue));
		
		
		//request response 是通过ThreadLocal分发的，一个线程绑定一对，session和客户端的cookid有关，不同的cookid对应一个session。
//		LOGGER.info("ServletRequest HASHCODE:{}",request.hashCode());
//		LOGGER.info("ServletRequest HASHCODE:{}",response.hashCode());		
//		LOGGER.info("HttpServletRequest HASHCODE:{}",httpServletRequest.hashCode());
//		LOGGER.info("HttpServletResponse HASHCODE:{}",httpServletResponse.hashCode());
//		LOGGER.info("SESSION HASHCODE:{}",httpServletRequest.getSession());
		
				
		//解决跨域问题		
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
