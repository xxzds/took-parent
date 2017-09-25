package com.tooklili.service.biz.impl.qrcode;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tooklili.http.HttpCallService;
import com.tooklili.service.biz.intf.qrcode.QuickResponseCodeService;
import com.tooklili.util.result.PlainResult;

/**
 * 二维码服务
 * @author shuai.ding
 * @date 2017年9月22日上午10:17:00
 */
@Service
public class QuickResponseCodeServiceImpl implements QuickResponseCodeService{
	private static final Logger LOGGER = LoggerFactory.getLogger(QuickResponseCodeService.class);
	
	@Resource
	private HttpCallService httpCallService;
	
	/**
	 * 生成二维码base64字符串
	 * @author shuai.ding
	 * @param url        需转成二维码的url地址
	 * @return
	 */
	public PlainResult<String> getQrCodeBase64(String url){
		PlainResult<String> result = new PlainResult<String>();
		if(StringUtils.isEmpty(url)){
			return result.setErrorMessage("url不能为空");
		}
				
		PlainResult<byte[]> responseResult =  httpCallService.urlConnectionGetReturnByte(urlPrfix+url);
		if(!responseResult.isSuccess()){
			LOGGER.info("调用地址【{}】失败，失败原因：{}",urlPrfix+url,responseResult.getMessage());
			return result.setErrorMessage(responseResult.getMessage());
		}
		
		//base64编码
		String data = "data:image/png;base64,"+ Base64.encodeBase64String(responseResult.getData());
		
		result.setData(data);		 
		return result;
	}
}
