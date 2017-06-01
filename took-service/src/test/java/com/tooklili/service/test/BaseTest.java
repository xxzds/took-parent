package com.tooklili.service.test;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试基类
 * @author shuai.ding
 * @date 2017年5月31日下午4:39:40
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-service.xml" })
public class BaseTest  {
    public static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

}
