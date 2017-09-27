package com.tooklili.service.spring;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tooklili.http.HttpCallService;

/**
 * spring 加载配置测试
 * @author shuai.ding
 *
 * @date 2017年6月9日下午1:33:11
 */
public class SpringTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringTest.class);

	@SuppressWarnings("resource")
	@Test
	public void springTest(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:spring/spring-service.xml");
//		HttpCallService httpCallService =  (HttpCallService)ctx.getBean("httpCallService");
//		LOGGER.info(httpCallService.getClass().getName());
		
		HttpCallService httpCallService1 =  (HttpCallService)ctx.getBean("httpCallService1");
		LOGGER.info(httpCallService1.getClass().getName());
		
		
	}
}
