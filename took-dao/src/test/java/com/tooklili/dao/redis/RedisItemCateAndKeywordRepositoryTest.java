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
import com.tooklili.dao.db.intf.admin.TookItemSearchKeywordDao;
import com.tooklili.enums.admin.ApiTypeEnum;
import com.tooklili.model.admin.TookItemSearchKeyword;
import com.tooklili.model.admin.TookKeywordDetail;
import com.tooklili.model.taobao.TookKeywordInfo;
import com.tooklili.util.JsonFormatTool;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-dao-test.xml" })
@ActiveProfiles("mybatisAndRedis")
public class RedisItemCateAndKeywordRepositoryTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisItemCateAndKeywordRepositoryTest.class);
	
	@Autowired
	private TookItemSearchKeywordDao tookItemSearchKeywordDao;
	
	@Autowired
	private RedisItemCateAndKeywordRepository redisItemCateAndKeywordRepository;

	@Test
	public void saveItemCateAndKeywordTest(){
		try{
			TookItemSearchKeyword tookItemSearchKeyword = new TookItemSearchKeyword();
			tookItemSearchKeyword.setIsAvailable(1);
			List<TookItemSearchKeyword> tookItemSearchKeywords = tookItemSearchKeywordDao.find(tookItemSearchKeyword);
			
			redisItemCateAndKeywordRepository.saveItemCateAndKeyword(tookItemSearchKeywords);
		}catch(Exception e){
			LOGGER.error("exception",e);
		}
		
	}
	
	@Test
	public void queryKeywordDetailTest(){
		try{
			List<TookKeywordDetail> result = redisItemCateAndKeywordRepository.queryKeywordDetail(ApiTypeEnum.TBK_API);
			LOGGER.info(JsonFormatTool.formatJson(JSON.toJSONString(result)));
		}catch(Exception e){
			LOGGER.error("exception",e);
		}
	}
	
	@Test
	public void getRandomKeywordInfoTest(){
		try{
			TookKeywordInfo tookKeywordInfo = redisItemCateAndKeywordRepository.getRandomKeywordInfo(ApiTypeEnum.TBK_API);
			LOGGER.info(JsonFormatTool.formatJson(JSON.toJSONString(tookKeywordInfo)));
		}catch(Exception e){
			LOGGER.error("exception",e);
		}
	}
}
