package com.tooklili.jobhandler;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.tooklili.util.ClearCacheUtil;
import com.tooklili.util.HttpClientUtil;
import com.tooklili.util.TookliliCookieUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
/**
 * 清除过期商品
 * @author shuai.ding
 * @date 2017年7月20日下午3:59:49
 */
@JobHander(value="clearExpiredItemsJobHandler")
@Service
public class ClearExpiredItemsJobHandler extends IJobHandler{
	private static final Logger LOGGER = LoggerFactory.getLogger(ClearExpiredItemsJobHandler.class);

	@Override
	public ReturnT<String> execute(String... params) throws Exception {
		
		//1.获取cookies
		String cookies = TookliliCookieUtil.getLoginCookies();
			
		//2、清除过期商品
		String clearItemUrl="http://admin.tooklili.com/index.php?m=items&a=clear";
		Map<String, String> requestParams = Maps.newHashMap();
		requestParams.put("isok", "1");
		requestParams.put("action","outtime");
		String content = HttpClientUtil.post(clearItemUrl, requestParams, cookies);
		LOGGER.info("清除过期商品，返回的内容：{}",content);
		
		//3、更新缓存
		ClearCacheUtil.clearCache();
			
		return ReturnT.SUCCESS;
	}

}
