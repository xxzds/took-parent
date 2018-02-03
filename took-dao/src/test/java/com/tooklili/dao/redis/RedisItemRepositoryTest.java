package com.tooklili.dao.redis;

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
import com.tooklili.enums.tooklili.ItemCateEnum;
import com.tooklili.model.tooklili.Item;
import com.tooklili.util.JsonFormatTool;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-dao-test.xml" })
@ActiveProfiles("redis")
public class RedisItemRepositoryTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisItemRepository.class);
	
	@Autowired
	private RedisItemRepository redisItemRepository;
	
	@Test
	public void queryItemsByCateIdTest(){
		try{
			List<Item> items = redisItemRepository.queryItemsByCateId(ItemCateEnum.CONSTUME, 1L, 10L);
			Long count = redisItemRepository.countItemsByCateId(ItemCateEnum.CONSTUME);
			LOGGER.info("商品信息\n{}",JsonFormatTool.formatJson(JSON.toJSONString(items)));
			LOGGER.info("商品总个数:{}",count);
		}catch(Exception e){
			LOGGER.error("exception",e);
		}		
	}
	
	@Test
	public void randomItemByCateIdTest(){
		try{
			List<Item> items = redisItemRepository.randomItemByCateId(ItemCateEnum.CONSTUME, 4);
			LOGGER.info("商品信息\n{}",JsonFormatTool.formatJson(JSON.toJSONString(items)));
		}catch(Exception e){
			LOGGER.error("exception",e);
		}		
	}
}
