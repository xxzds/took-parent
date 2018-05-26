package com.tooklili.dao.admin;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tooklili.dao.BaseTest;
import com.tooklili.dao.db.intf.admin.TookItemTwentyDao;

/**
 * 20元商品测试
 * @author ding.shuai
 * @date 2018年5月26日下午12:35:33
 */
public class TookItemTwentyDaoTest extends BaseTest{

	@Autowired
	private TookItemTwentyDao tookItemTwentyDao;
	
	/**
	 * 删除过期商品
	 */
	@Test
	public void delExpiredItemsTest() {
		try {
			int count = tookItemTwentyDao.delExpiredItems();
			logger.info("删除过期商品的个数为：{}",count);
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
}
