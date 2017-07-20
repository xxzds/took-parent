package com.tooklili.jobhandler;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.tooklili.util.HttpClientUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
/**
 * 清除过期商品，同时更新缓存
 * @author shuai.ding
 * @date 2017年7月20日下午3:59:49
 */
@JobHander(value="updateCacheJobHandler")
@Service
public class UpdateCacheJobHandler extends IJobHandler{
	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateCacheJobHandler.class);

	@Override
	public ReturnT<String> execute(String... params) throws Exception {
		
		//1、登录，获取cookie
		String url = "http://admin.tooklili.com:index.php?m=index&a=login";
		Map<String, String> requestParams = Maps.newHashMap();
		requestParams.put("username", "admin");
		requestParams.put("password","123456");
		
		Map<String, String> cookieMap = HttpClientUtil.getCookiesByPost(url, requestParams, null);		
		LOGGER.info("登录后台获取的cookies：{}",JSON.toJSONString(cookieMap));
		
		StringBuilder cookies=new StringBuilder();
		for(String key:cookieMap.keySet()){
			cookies.append(key+"="+cookieMap.get(key)+";");
		}
		
		//3、清除过期商品
		String clearItemUrl="http://admin.tooklili.com/index.php?m=items&a=clear";
		Map<String, String> requestParams2 = Maps.newHashMap();
		requestParams.put("isok", "1");
		requestParams.put("action","outtime");		
		String content1 = HttpClientUtil.post(clearItemUrl, requestParams2, cookies.toString());
		LOGGER.info("清除过期商品，返回的内容：{}",content1);
		
		//2、更新缓存
		String clearUrl="http://admin.tooklili.com/index.php?m=cache&a=qclear";
		String content2 = HttpClientUtil.get(clearUrl, cookies.toString());
		LOGGER.info("更新缓存，返回的内容：{}",content2);
		
		
		
		return ReturnT.SUCCESS;
	}

}
