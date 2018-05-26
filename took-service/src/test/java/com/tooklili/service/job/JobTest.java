package com.tooklili.service.job;

import javax.annotation.Resource;

import org.junit.Test;

import com.tooklili.service.BaseTest;
import com.tooklili.service.jobhandler.ClearExpiredItemsJobHandler;
import com.tooklili.service.jobhandler.CollectCouponsItemBySupserSearchJobHandler;
import com.tooklili.service.jobhandler.CollectCouponsItemByTbkApiJobHandller;
import com.tooklili.service.jobhandler.DirectCollectCouponsItemsJobHandler;
import com.tooklili.service.jobhandler.PersistenceAlimamaCookieJobHandler;

/**
 * job测试
 * @author shuai.ding
 *
 * @date 2017年10月20日下午3:48:38
 */
public class JobTest extends BaseTest{
	
	@Resource
	private CollectCouponsItemBySupserSearchJobHandler collectCouponsItemBySupserSearchJobHandler;
	
	@Resource
	private CollectCouponsItemByTbkApiJobHandller collectCouponsItemByTbkApiJobHandller;
	
	@Resource
    private ClearExpiredItemsJobHandler clearExpiredItemsJobHandler;
	
	@Resource
	private PersistenceAlimamaCookieJobHandler persistenceAlimamaCookieJobHandler;
	
	@Resource
	private DirectCollectCouponsItemsJobHandler directCollectCouponsItemsJobHandler;
	
	@Test
	public void collectCouponsItemBySupserSearchJobHandlerTest() throws Exception{
		collectCouponsItemBySupserSearchJobHandler.execute();		
	}
	
	@Test
	public void collectCouponsItemByTbkApiJobHandllerTest() throws Exception{
		collectCouponsItemByTbkApiJobHandller.execute();
	}
	
	@Test
	public void clearExpiredItemsJobHandler() throws Exception{
		clearExpiredItemsJobHandler.execute();
	}
	
	@Test
	public void ersistenceAlimamaCookieJobHandlerTest() throws Exception{
		persistenceAlimamaCookieJobHandler.execute();
	}
	
	@Test
	public void directCollectCouponsItemsJobHandlerTest() throws Exception {
		try {
			while(true) {
				directCollectCouponsItemsJobHandler.execute();
				Thread.sleep(5 *60 *1000);
			}			
		}catch (Exception e) {
			
		}
		
	}
}
