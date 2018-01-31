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

import com.tooklili.dao.db.intf.tooklili.ItemDao;
import com.tooklili.model.tooklili.Item;

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

}
