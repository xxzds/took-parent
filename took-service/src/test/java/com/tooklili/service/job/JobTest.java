package com.tooklili.service.job;

import javax.annotation.Resource;

import org.junit.Test;

import com.tooklili.service.BaseTest;
import com.tooklili.service.jobhandler.CollectCouponsItemBySupserSearchJobHandler;
import com.tooklili.service.jobhandler.CollectCouponsItemByTbkApiJobHandller;
import com.tooklili.service.jobhandler.DemoJobHandler;
import com.tooklili.service.jobhandler.DirctCollectCouponsByParamJobHandler;

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
	
	@Resource
	private CollectCouponsItemByTbkApiJobHandller collectCouponsItemByTbkApiJobHandller;
	
	@Resource
	private DirctCollectCouponsByParamJobHandler dirctCollectCouponsByParamJobHandler;
	
	@Test
	public void demoJobHandlerTest() throws Exception{
		demoJobHandler.execute();
	}
	
	@Test
	public void collectCouponsItemBySupserSearchJobHandlerTest() throws Exception{
		collectCouponsItemBySupserSearchJobHandler.execute();		
	}
	
	@Test
	public void collectCouponsItemByTbkApiJobHandllerTest() throws Exception{
		collectCouponsItemByTbkApiJobHandller.execute();
	}
	
	@Test
	public void dirctCollectCouponsByParamJobHandlerTest() throws Exception{
		dirctCollectCouponsByParamJobHandler.execute("小型收纳盒","2","5","38");
	}

}
