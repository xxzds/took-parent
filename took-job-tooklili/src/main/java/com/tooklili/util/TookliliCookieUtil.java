package com.tooklili.util;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

/**
 * 获取登录cookie的工具类
 * @author shuai.ding
 * @date 2017年8月15日下午2:56:32
 */
public class TookliliCookieUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(TookliliCookieUtil.class);
	
	/**
	 * cookie存储时间
	 */
	private static final long TTL = 1800;
	
	/**
	 * 登录后，cookie的内容
	 */
	private static String cookies;
	
	/**
	 * 获取cookie时的时间，用这个时间判断cookies存储的时间,存储时间为TTL
	 */
	private static Date recordDate;
	
	
	/**
	 * 获取登录的cookies
	 * @author shuai.ding
	 * @return
	 */
	public static String getLoginCookies() {
		//1.如果cookies不为空，且时间没有超过TTL，直接读取静态的成员变量
		if(StringUtils.isNotEmpty(cookies) && recordDate!=null){
			long recordDateTime = recordDate.getTime();
			long newDateTime = new Date().getTime();
			
			long interval = (newDateTime - recordDateTime)/1000;
			
			if(interval < TTL){
				return cookies;
			}
		}
		
		//2.清空读取数据
		cookies=null;
		recordDate=null;
		
		return loginTooklili();		
	}
	
	private static Object lock = new Object();	
	private static String loginTooklili() {
		//解决高并发，同时调用登录接口，导致获取的cookie被覆盖。
		synchronized (lock) {
			if(StringUtils.isNotEmpty(cookies)){
				return cookies;
			}
			
			// 1、登录，获取cookie
			String url = "http://admin.tooklili.com/index.php?m=index&a=login";
			Map<String, String> requestParams = Maps.newHashMap();
			requestParams.put("username", "admin");
			requestParams.put("password", "123456");

			Map<String, String> cookieMap = HttpClientUtil.getCookiesByPost(url, requestParams, null);
			LOGGER.info("登录后台获取的cookies：{}", JSON.toJSONString(cookieMap));

			StringBuilder cookies = new StringBuilder();
			for (String key : cookieMap.keySet()) {
				cookies.append(key + "=" + cookieMap.get(key) + ";");
			}
			
			//存储cookie和当前时间
			TookliliCookieUtil.cookies = cookies.toString();
			TookliliCookieUtil.recordDate = new Date();
			
			return cookies.toString();			
		}	
	}
}
