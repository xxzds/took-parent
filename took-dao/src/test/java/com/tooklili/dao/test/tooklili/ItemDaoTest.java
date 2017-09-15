package com.tooklili.dao.test.tooklili;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.tooklili.dao.intf.tooklili.ItemDao;
import com.tooklili.dao.test.BaseTest;
import com.tooklili.model.tooklili.Item;

public class ItemDaoTest extends BaseTest{
	
	@Resource
	private ItemDao itemDao;

	@Test
	public void queryItemsTest(){
		List<Item> list =  itemDao.queryItems(35);
		for(Item item:list){
			logger.info(JSON.toJSONString(item));
		}
	}
}
