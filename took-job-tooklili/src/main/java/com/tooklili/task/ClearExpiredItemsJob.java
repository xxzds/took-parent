package com.tooklili.task;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.tooklili.util.ClearCacheUtil;
import com.tooklili.util.HttpClientUtil;
import com.tooklili.util.PropertiesUtil;
import com.tooklili.util.TookliliCookieUtil;
/**
 * 清除过期商品
 * @author shuai.ding
 * @date 2017年7月20日下午3:59:49
 */
@Service
public class ClearExpiredItemsJob extends BaseJob{
	private static final Logger LOGGER = LoggerFactory.getLogger(ClearExpiredItemsJob.class);
	
	private PropertiesUtil propertiesUtil = PropertiesUtil.getInstance("system.properties");
	private String tookliliPrefixUrl=propertiesUtil.getValue("tooklili_prefix_url");
	
	public ClearExpiredItemsJob() {
		this.setCorn(propertiesUtil.getValue("clear_expired_item_cron"));
	}

	@Override
	public void execute(){
		
		//1.获取cookies
		String cookies = TookliliCookieUtil.getLoginCookies();
			
		//2、清除过期商品
		String clearItemUrl=tookliliPrefixUrl+"/index.php?m=items&a=clear";
		Map<String, String> requestParams = Maps.newHashMap();
		requestParams.put("isok", "1");
		requestParams.put("action","outtime");
		String content = HttpClientUtil.post(clearItemUrl, requestParams, cookies);
		LOGGER.info("清除过期商品，返回的内容：{}",content);
		
		//3、更新缓存
		ClearCacheUtil.clearCache();
	}
}
