package com.tooklili.service.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试
 * @author shuai.ding
 *
 * @date 2017年6月8日下午2:16:37
 */
public class TestDemo {
	
	private static final Logger logger = LoggerFactory.getLogger(TestDemo.class);
	
	@Test
	public void test(){
		Object object =new Object();
		logger.info(object.hashCode()+"");
		
		logger.info("Str".hashCode()+"");
	}
}
