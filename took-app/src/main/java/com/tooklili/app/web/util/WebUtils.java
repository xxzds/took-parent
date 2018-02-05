package com.tooklili.app.web.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * @author ding.shuai
 * @date 2016年8月23日下午3:11:09
 */
public class WebUtils {

	/**
	 * 获取当前urlRoot
	 * @param request
	 * @return
	 */
	public static String getLocaleUrlRoot(HttpServletRequest request) {
		// 获取当前url根路径，如 http://abc.aliyun-inc.net
		String curScheme = request.getScheme(); // http
		String curServerName = request.getServerName(); // abc.aliyun-inc.net
		int curServerPort = request.getServerPort();

		String curlocaleUrlRoot = curScheme + "://" + curServerName;
		if (curServerPort != 80) {
			curlocaleUrlRoot += ":" + curServerPort;
		}

		return curlocaleUrlRoot;
	}

	/**
	 * 获取主页url
	 */
	public static String getHomeUrl(HttpServletRequest request) {
		return getLocaleUrlRoot(request) + request.getContextPath();
	}

	/**
	 * 获取当前请求的地址
	 * @param request
	 * @return
	 */
	public static String getCurrentUrl(HttpServletRequest request) {
		// 根路径
		String localeUrl = getLocaleUrlRoot(request);

		String curUri = request.getRequestURI();
		String curQueryString = request.getQueryString();

		if (StringUtils.isNotBlank(curUri)) {
			localeUrl = localeUrl + curUri;
		}
		
		if (StringUtils.isNotBlank(curQueryString)) {
			localeUrl += "?"+curQueryString;
		}

		return localeUrl;
	}
}
