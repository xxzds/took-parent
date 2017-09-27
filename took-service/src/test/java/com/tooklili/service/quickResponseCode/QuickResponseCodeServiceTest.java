package com.tooklili.service.quickResponseCode;

import javax.annotation.Resource;

import org.junit.Test;

import com.tooklili.http.HttpCallService;
import com.tooklili.service.biz.intf.qrcode.QuickResponseCodeService;
import com.tooklili.service.BaseTest;
import com.tooklili.util.result.PlainResult;

/**
 * 
 * @author shuai.ding
 * @date 2017年9月22日下午1:41:13
 */
public class QuickResponseCodeServiceTest extends BaseTest{

	@Resource
	private QuickResponseCodeService quickResponseCodeService;
	
	@Resource
	private HttpCallService httpCallService;
	
	@Test
	public void getCodeTest(){
		try{
			PlainResult<String> result =quickResponseCodeService.getQrCodeBase64("http://www.tooklili.com:81/took-app/itemDetail.html?numIid=541422273065&id=197189");
			
			logger.info(result.getData());
		}catch(Exception e){
			logger.error("exception",e);
		}		
	}
}
