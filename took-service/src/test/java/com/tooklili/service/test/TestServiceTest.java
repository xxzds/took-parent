package com.tooklili.service.test;

import javax.annotation.Resource;

import org.junit.Test;

import com.tooklili.service.BaseTest;
import com.tooklili.service.biz.intf.test.TestService;

public class TestServiceTest extends BaseTest{
	
	@Resource
	private TestService testService;

	
	@Test
	public void testTransaction(){
		try{
			testService.testTransaction();
		}catch(Exception e){
			logger.error("exception",e);
		}
		
	}
}
