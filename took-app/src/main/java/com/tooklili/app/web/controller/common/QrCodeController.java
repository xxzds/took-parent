package com.tooklili.app.web.controller.common;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tooklili.service.biz.intf.qrcode.QuickResponseCodeService;
import com.tooklili.util.result.PlainResult;

@Controller
public class QrCodeController {

	@Resource
	private QuickResponseCodeService quickResponseCodeService;
	
	/**
	 * 生成二维码base64字符串
	 * @author shuai.ding
	 * @param url
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/getQrCodeBase64")
	@ResponseBody
	public PlainResult<String> getQrCodeBase64(String url) throws UnsupportedEncodingException{		
		PlainResult<String> result =  quickResponseCodeService.getQrCodeBase64(url);		
		return result;
	}
}