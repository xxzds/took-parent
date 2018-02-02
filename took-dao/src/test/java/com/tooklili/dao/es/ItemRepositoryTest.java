package com.tooklili.dao.es;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.tooklili.dao.db.intf.tooklili.ItemDao;
import com.tooklili.dao.es.intf.ItemRepository;
import com.tooklili.model.tooklili.Item;
import com.tooklili.util.JsonFormatTool;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringJUnitConfig(locations = {"classpath:spring/spring-dao-test.xml" })
@ActiveProfiles("mybatisAndEs")
public class ItemRepositoryTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemRepositoryTest.class);
	
	@Autowired
	private ItemDao itemDao;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Test
	public void saveItemToEs(){
		try{
			List<Item> items = itemDao.queryItemsByCateId(35);
			itemRepository.saveAll(items);
		}catch(Exception e){
			LOGGER.error("exception",e);
		}
		
	}
	
	@Test
	public void queryItemBynumIid(){
		try{
			Item item = itemRepository.queryItemBynumIid(560539907137L);
			LOGGER.info(JsonFormatTool.formatJson(JSON.toJSONString(item)));
		}catch(Exception e){
			LOGGER.error("exception",e);
		}
	}
	
	@Test
	public void updateItemById(){
		try{
			Item item = new Item();
			item.setId(806166L);
			itemRepository.save(item);
		}catch(Exception e){
			LOGGER.error("exception",e);
		}
	}
	
	@Test
	public void del(){
		Item item = new Item();
		item.setId(1517471536511L);
		itemRepository.delete(item);
	}

}
