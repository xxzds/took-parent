package com.tooklili.dao.redis;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.tooklili.model.taobao.TookKeywordInfo;
import com.tooklili.util.JsonFormatTool;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-dao-test.xml" })
@ActiveProfiles("redis")
public class RedisTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisTest.class);
	
	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	
	@Resource(name = "updateKeywordSearchCurrentPage")
	private RedisScript<TookKeywordInfo> updateKeywordSearchCurrentPage;
	
	@Test
	public void scriptExcuteTest(){
		try{
			List<String> keys = Lists.newArrayList();
//			keys.add("item_cate_keyword");
			keys.add("tbk_item_cate_keyword_detail");
			
		    TookKeywordInfo tookKeywordInfo = redisTemplate.execute(updateKeywordSearchCurrentPage,new StringRedisSerializer(),
		    		new Jackson2JsonRedisSerializer<>(TookKeywordInfo.class),
					keys,"8_男士假两件_20");
		    LOGGER.info(JsonFormatTool.formatJson(JSON.toJSONString(tookKeywordInfo)));
		}catch(Exception e){
			LOGGER.error("exception",e);
		}		
	}

}
