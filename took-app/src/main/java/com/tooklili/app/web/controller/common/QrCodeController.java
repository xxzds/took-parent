package com.tooklili.app.web.controller.common;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tooklili.service.biz.api.quickResponseCode.QuickResponseCodeService;
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
	 */
	@RequestMapping("/getQrCodeBase64")
	@ResponseBody
	public PlainResult<String> getQrCodeBase64(String url){		
		PlainResult<String> result =  quickResponseCodeService.getQrCodeBase64(url);		
		return result;
	}
}
