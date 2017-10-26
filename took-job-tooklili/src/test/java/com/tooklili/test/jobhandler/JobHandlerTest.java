package com.tooklili.test.jobhandler;

import javax.annotation.Resource;

import org.junit.Test;

import com.tooklili.jobhandler.ClearExpiredItemsJobHandler;
import com.tooklili.jobhandler.CollectItemsJobHandler;
import com.tooklili.jobhandler.PersistenceAlimamaCookieJobHandler;
import com.tooklili.jobhandler.UpdateItemPicUrlJobHandler;
import com.tooklili.test.BaseTest;

public class JobHandlerTest extends BaseTest{
	
	@Resource
	private ClearExpiredItemsJobHandler clearExpiredItemsJobHandler;
	
	@Resource
	private CollectItemsJobHandler collectItemsJobHandler;
	
	@Resource
	private PersistenceAlimamaCookieJobHandler persistenceAlimamaCookieJobHandler;
	
	@Resource
	private UpdateItemPicUrlJobHandler updateItemPicUrlJobHandler;
	
	/**
	 * 清除过期商品测试
	 * @author shuai.ding
	 */
	@Test
	public void clearExpiredItemsJobHandlerTest(){
		try {
			clearExpiredItemsJobHandler.execute("");
		} catch (Exception e) {
			logger.error("exception:",e);
		}
	}
	
	@Test
	public void collectItemsJobHandlerTest(){
		try {
			collectItemsJobHandler.execute("");
		} catch (Exception e) {
			logger.error("exception:",e);
		}
	}
	
	@Test
	public void persistenceAlimamaCookieJobHandlerTest(){
		try {
			persistenceAlimamaCookieJobHandler.execute();
		} catch (Exception e) {
			logger.error("exception:",e);
		}
	}
	
	@Test
	public void updateItemPicUrlJobHandlerTest(){
		try {
			updateItemPicUrlJobHandler.execute();
		} catch (Exception e) {
			logger.error("exception:",e);
		}
	}

}
