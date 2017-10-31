package com.tooklili.service.biz.intf.common;

import java.io.UnsupportedEncodingException;

import com.tooklili.util.result.PlainResult;

/**
 * 二维码服务
 * @author shuai.ding
 * @date 2017年9月22日上午10:17:00
 */
public interface QuickResponseCodeService {
	/**
	 * 获取二维码地址前缀
	 */
	public final String urlPrefix ="http://qr.liantu.com/api.php?text=";

	/**
	 * 生成二维码base64字符串
	 * @author shuai.ding
	 * @param url        需转成二维码的url地址
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public PlainResult<String> getQrCodeBase64(String url) throws UnsupportedEncodingException;
	
}
