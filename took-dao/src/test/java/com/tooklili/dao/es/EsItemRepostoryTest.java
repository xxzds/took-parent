package com.tooklili.dao.es;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.tooklili.model.tooklili.Item;
import com.tooklili.util.JsonFormatTool;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-dao-test.xml" })
@ActiveProfiles("es")
public class EsItemRepostoryTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(EsItemRepostoryTest.class);

	@Autowired
	private EsItemRepository esItemRepository;

	@Test
	public void queryItemByIdTest() {
		Item item = esItemRepository.queryItemById(1517465083937L);
		LOGGER.info(JsonFormatTool.formatJson(JSON.toJSONString(item)));
	}

	@Test
	public void queryItemsByCateId() {
		try {
			List<Item> result = esItemRepository.queryItemsByCateId(35, 1, 10);
			long count = esItemRepository.countItemsByCateId(35, 1, 10);
			LOGGER.info(JsonFormatTool.formatJson(JSON.toJSONString(result)));
			LOGGER.info("总个数:{}", count);
		} catch (Exception e) {
			LOGGER.error("exception", e);
		}
	}

	@Test
	public void queryItemsByKeywordTest() {
		try {
			String keyword = "男士上衣";
			int currentPage = 1;
			int pageSize = 10;
			List<Item> result = esItemRepository.queryItemsByKeyword(keyword, currentPage, pageSize);
			long count = esItemRepository.countItemsByKeyword(keyword, currentPage, pageSize);
			LOGGER.info(JsonFormatTool.formatJson(JSON.toJSONString(result)));
			LOGGER.info("总个数:{}", count);
		} catch (Exception e) {
			LOGGER.error("exception", e);
		}
	}
	
	@Test
	public void queryRandomItemByCateIdTest(){
		try {
			List<Item> result = esItemRepository.queryRandomItemByCateId(null, 1);
			LOGGER.info(JsonFormatTool.formatJson(JSON.toJSONString(result)));
		} catch (Exception e) {
			LOGGER.error("exception", e);
		}
	}
	
	@Test
	public void delExpiredItemsTest(){
		try{
			esItemRepository.delExpiredItems();
		}catch(Exception e){
			LOGGER.error("exception", e);
		}
	}
}
