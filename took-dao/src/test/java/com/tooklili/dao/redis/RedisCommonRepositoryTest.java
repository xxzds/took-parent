package com.tooklili.dao.redis;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tooklili.util.RandomUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-dao-test.xml" })
@ActiveProfiles("redis")
public class RedisCommonRepositoryTest {

	@Resource
	private RedisCommonRepository redisCommonRepository;
	
	
	@Test
	public void setStringTest() {
		redisCommonRepository.setString(RandomUtils.randomCharAndNum(5), "http://www.baidu.com", 60,2);
	}
}
