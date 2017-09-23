package com.tooklili.service.test.quickResponseCode;

import javax.annotation.Resource;

import org.junit.Test;

import com.tooklili.http.HttpCallService;
import com.tooklili.service.biz.api.quickResponseCode.QuickResponseCodeService;
import com.tooklili.service.test.BaseTest;
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
			PlainResult<String> result =quickResponseCodeService.getQrCodeBase64("http://www.baidu.com");
			
			logger.info(result.getData());
		}catch(Exception e){
			logger.error("exception",e);
		}		
	}
}
