package com.tooklili.dao.mongodb;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.tooklili.model.tooklili.Item;
import com.tooklili.util.JsonFormatTool;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-dao-test.xml" })
@ActiveProfiles("mongodb")
public class MongoItemRepositoryTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(MongoItemRepositoryTest.class);
	
	@Resource
	private MongoItemRepository mongoItemRepository;
	
	@Test
	public void queryItemsByCateIdTest(){
		try{
			List<Item> items = mongoItemRepository.queryItemsByCateId(35,1, 10);
			Long count = mongoItemRepository.countItemsByCateId(35);
			LOGGER.info("商品信息\n{}",JsonFormatTool.formatJson(JSON.toJSONString(items)));
			LOGGER.info("商品总个数:{}",count);
		}catch(Exception e){
			LOGGER.error("exception",e);
		}
	}
	
	@Test
	public void queryItemByIdTest(){
		Item item = mongoItemRepository.queryItemById(808828L);
		LOGGER.info(JsonFormatTool.formatJson(JSON.toJSONString(item)));
	}
	
	@Test
	public void queryItemsByKeyword(){
		try{
			List<Item> items = mongoItemRepository.queryItemsByKeyword("衬衫",1, 10);
			Long count = mongoItemRepository.countItemsByKeyword("衬衫");
			LOGGER.info("商品信息\n{}",JsonFormatTool.formatJson(JSON.toJSONString(items)));
			LOGGER.info("商品总个数:{}",count);
		}catch(Exception e){
			LOGGER.error("exception",e);
		}
	}

}
