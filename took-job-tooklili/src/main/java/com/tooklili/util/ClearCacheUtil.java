package com.tooklili.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 清除缓存工具类
 * @author shuai.ding
 *
 * @date 2017年8月15日下午4:26:19
 */
public class ClearCacheUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClearCacheUtil.class);
	
	/**
	 * 清除缓存
	 * @author shuai.ding
	 */
	public static void clearCache(){
		String clearUrl="http://admin.tooklili.com/index.php?m=cache&a=qclear";
		String content = HttpClientUtil.get(clearUrl, TookliliCookieUtil.getLoginCookies());
		LOGGER.info("更新缓存，返回的内容：{}",content);
	}

}
