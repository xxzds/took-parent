package com.tooklili.service.biz.intf.wechat;

import java.io.UnsupportedEncodingException;

/**
 * 微信xml消息推送的服务
 * @author shuai.ding
 *
 * @date 2017年10月26日下午2:55:27
 */
public interface WechatMessageService {
	
	/**
	 * 处理xml数据
	 * @param xml  微信传入的数据
	 * @return  返回xml格式的数据
	 * @throws UnsupportedEncodingException 
	 */
	public String  processWechatMag(String xml) throws UnsupportedEncodingException;

}
