package com.tooklili.service.biz.impl.common;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.tooklili.http.HttpCallService;
import com.tooklili.service.biz.intf.common.ShortLinkService;
import com.tooklili.util.result.PlainResult;

/**
 * 短连接服务
 * @author shuai.ding
 * @date 2017年10月31日下午1:43:55
 */
//@Service
public class ShortLinkServiceImpl implements ShortLinkService{
	private static final Logger LOGGER =LoggerFactory.getLogger(ShortLinkServiceImpl.class);
	@Resource
	private HttpCallService httpCallService;

	@Override
	public PlainResult<String> getShortLinkUrl(String url){
		PlainResult<String> result = new PlainResult<String>();
		if(StringUtils.isEmpty(url)){
			return result.setErrorMessage("url不能为空");
		}
		Map<String, String> params = Maps.newHashMap();
		params.put("url", url);
		LOGGER.info("请求参数:{}",url);
		PlainResult<String> responseResult =  httpCallService.httpPost(short_url, params);
		if(!responseResult.isSuccess()){
			LOGGER.info("调用地址【{}】失败，失败原因：{}",short_url,responseResult.getMessage());
			return result.setErrorMessage(responseResult.getMessage());
		}

		result.setData(JSON.parseObject(responseResult.getData()).getString("list"));
		return result;
	}

}
