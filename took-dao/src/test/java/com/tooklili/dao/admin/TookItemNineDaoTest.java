package com.tooklili.dao.admin;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tooklili.dao.BaseTest;
import com.tooklili.dao.db.intf.admin.TookItemNineDao;
import com.tooklili.model.tooklili.Item;

/**
 * 商品9块9测试
 * @author ding.shuai
 * @date 2018年5月20日下午3:19:35
 */
public class TookItemNineDaoTest extends BaseTest{
	@Autowired
	private TookItemNineDao tookItemNineDao;
	
	@Test
	public void findTest() {
		try {
			List<Item> result = tookItemNineDao.find( null);
			logger.info("结果：{}",result);
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
	}
	
	/**
	 * 删除过期商品
	 */
	@Test
	public void delExpiredItemsTest() {
		try {
			int count = tookItemNineDao.delExpiredItems();
			logger.info("删除过期商品的个数为：{}",count);
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
	}

}
