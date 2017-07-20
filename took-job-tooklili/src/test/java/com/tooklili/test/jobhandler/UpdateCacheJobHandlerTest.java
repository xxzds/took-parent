package com.tooklili.test.jobhandler;

import javax.annotation.Resource;

import org.junit.Test;

import com.tooklili.jobhandler.UpdateCacheJobHandler;
import com.tooklili.test.BaseTest;

public class UpdateCacheJobHandlerTest extends BaseTest{
	
	@Resource
	private UpdateCacheJobHandler updateCacheJobHandler;
	
	@Test
	public void executeTest(){
		try {
			updateCacheJobHandler.execute("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
