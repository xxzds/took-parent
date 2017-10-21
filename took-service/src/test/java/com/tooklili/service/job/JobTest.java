package com.tooklili.service.job;

import javax.annotation.Resource;

import org.junit.Test;

import com.tooklili.service.BaseTest;
import com.tooklili.service.jobhandler.CollectCouponsItemBySupserSearchJobHandler;
import com.tooklili.service.jobhandler.DemoJobHandler;

/**
 * job测试
 * @author shuai.ding
 *
 * @date 2017年10月20日下午3:48:38
 */
public class JobTest extends BaseTest{
	
	@Resource
	private DemoJobHandler demoJobHandler;
	
	@Resource
	private CollectCouponsItemBySupserSearchJobHandler collectCouponsItemBySupserSearchJobHandler;
	
	@Test
	public void demoJobHandlerTest() throws Exception{
		demoJobHandler.execute();
	}
	
	@Test
	public void collectCouponsItemBySupserSearchJobHandlerTest() throws Exception{
		for(int i=0;i<5;i++){
			collectCouponsItemBySupserSearchJobHandler.execute();
			Thread.sleep(10000);
		}
		
	}

}
