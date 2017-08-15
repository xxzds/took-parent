package com.tooklili.test.jobhandler;

import javax.annotation.Resource;

import org.junit.Test;

import com.tooklili.jobhandler.ClearExpiredItemsJobHandler;
import com.tooklili.test.BaseTest;

public class JobHandlerTest extends BaseTest{
	
	@Resource
	private ClearExpiredItemsJobHandler clearExpiredItemsJobHandler;
	
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

}
