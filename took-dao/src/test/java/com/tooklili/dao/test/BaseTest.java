package com.tooklili.dao.test;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试基类
 * 说明：注解ignore，在执行测试时，忽略此类，防止测试出现：java.lang.Exception: No runnable methods
 * @author shuai.ding
 * @date 2017年5月31日下午4:39:40
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-dao-test.xml" })
@Ignore
public class BaseTest  {
    public static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

}
