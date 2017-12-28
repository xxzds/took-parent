package com.tooklili.admin.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * cookie 工具类
 * @author shuai.ding
 *
 * @date 2017年12月27日下午4:01:00
 */
public class CookieUtils {
	private static final String PATH = "/";
	
	public static String getCookieValue(String key, HttpServletRequest request) {
        Cookie cookie = getCookie(key, request);
        if (cookie == null) {
            return null;
        }
        return cookie.getValue();
    }	
	
	public static Cookie getCookie(String key, HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        Cookie value = null;
        for (Cookie c : cookies) {
            if (key.equals(c.getName())) {
                value = c;
                break;
            }
        }
        return value;
    }
	
	public static void addCookie(String key, String value, HttpServletResponse response) {
		setCookie(key, value, -1, null, null, response);
	}

	public static void addCookie(String key, String value, final boolean httpOnly, HttpServletResponse response) {
		setCookie(key, value, -1, null, null, httpOnly, response);
	}

	public static void addCookie(String key, String value, final boolean httpOnly, final boolean secure,
			HttpServletResponse response) {
		setCookie(key, value, -1, null, null, httpOnly, secure, response);
	}

	public static void addCookie(String key, String value, int maxAge, HttpServletResponse response) {
		setCookie(key, value, maxAge, null, null, response);
	}
	
	public static void addCookie(String key, String value, int maxAge, final boolean httpOnly,
			HttpServletResponse response) {
		setCookie(key, value, maxAge, null, null, httpOnly, response);
	}
	
	public static void addCookie(String key, String value, int maxAge, final boolean httpOnly, final boolean secure,
			HttpServletResponse response) {
		setCookie(key, value, maxAge, null, null, httpOnly, secure, response);
	}
	
	public static void addCookie(String key, String value, int maxAge, String path, String domainName,
			HttpServletResponse response) {
		setCookie(key, value, maxAge, path, domainName, response);
	}
	
	public static void addCookie(String key, String value, int maxAge, String path, String domainName,
			final boolean httpOnly, HttpServletResponse response) {
		setCookie(key, value, maxAge, path, domainName, httpOnly, response);
	}
	
	public static void addCookie(String key, String value, int maxAge, String path, String domainName,
			final boolean httpOnly, final boolean secure, HttpServletResponse response) {
		setCookie(key, value, maxAge, path, domainName, httpOnly, secure, response);
	}
	
	public static void removeCookie(String key, HttpServletResponse response) {
        removeCookie(key, null, null, response);
    }
	
	public static void removeCookie(String key, String path, String domainName, HttpServletResponse response) {
		setCookie(key, StringUtils.EMPTY, 0, path, domainName, false, response);
	}
	
	private static void setCookie(String key, String value, int maxAge, String path, String domainName,
			HttpServletResponse response) {
		setCookie(key, value, maxAge, path, domainName, false, false, response);
	}
	
	private static void setCookie(String key, String value, int maxAge, String path, String domainName,
			final boolean httpOnly, HttpServletResponse response) {
		setCookie(key, value, maxAge, path, domainName, httpOnly, false, response);
	}
	
	/**
	 * 设置cookie
	 * @author shuai.ding
	 * @param key         键
	 * @param value       值
	 * @param maxAge      存活时间
	 * @param path        路径,默认为/
	 * @param domainName  域名
	 * @param httpOnly    设置为true，通过js脚本(document.cookie)将无法读取到cookie信息，这样能一定程度上的防止XSS攻击，但http请求依然会携带
	 * @param secure      安全标志，指定后，只有在使用SSL链接时，会携带
	 * @param response
	 */
	private static void setCookie(String key, String value, int maxAge, String path, String domainName,
			final boolean httpOnly, final boolean secure, HttpServletResponse response) {
		if (response != null) {
			Cookie cookie = new Cookie(key, value);
			cookie.setMaxAge(maxAge);
			if (StringUtils.isNotBlank(path)) {
				cookie.setPath(path);
			} else {
				cookie.setPath(PATH);
			}
			if (StringUtils.isNotBlank(domainName)) {
				cookie.setDomain(domainName);
			}
			cookie.setSecure(secure);			
			cookie.setHttpOnly(httpOnly);
			response.addCookie(cookie);
		}
	}

}
