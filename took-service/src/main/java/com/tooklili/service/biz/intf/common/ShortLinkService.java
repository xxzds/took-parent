package com.tooklili.service.biz.intf.common;

import java.io.UnsupportedEncodingException;

import com.tooklili.util.result.PlainResult;

/**
 * 短连接服务
 * @author shuai.ding
 *
 * @date 2017年10月31日下午1:11:29
 */
public interface ShortLinkService {
	
	/**
	 * 短连接接口
	 */
	final String short_url="http://suo.im/front/index/urlCreate";
	
	/**
	 * 生成短连接
	 * @author shuai.ding
	 * @param url         需要转成短连接的url
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public PlainResult<String> getShortLinkUrl(String url);

}
