package com.tooklili.dao.tooklili;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.tooklili.dao.BaseTest;
import com.tooklili.dao.db.intf.tooklili.ItemDao;
import com.tooklili.model.tooklili.Item;

public class ItemDaoTest extends BaseTest{
	
	@Resource
	private ItemDao itemDao;

	@Test
	public void queryItemsTest(){
		List<Item> list =  itemDao.queryItemsByCateId(35);
		for(Item item:list){
			logger.info(JSON.toJSONString(item));
		}
	}
	
	@Test
	public void queryItemByIdTest(){
		Item item = itemDao.queryItemById(156801L);
		logger.info(JSON.toJSONString(item));
	}
	
	@Test
	public void updateItemPicUrl(){
		 itemDao.updateItemPicUrl();
	}
}
