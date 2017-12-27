package com.tooklili.service.admin.system;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.tooklili.model.admin.SysIcon;
import com.tooklili.service.biz.intf.admin.system.IconService;
import com.tooklili.util.JsonFormatTool;
import com.tooklili.util.result.PageResult;

/**
 * 图标服务测试
 * @author shuai.ding
 * @date 2017年12月26日下午2:19:31
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-service-admin.xml"})
@ActiveProfiles("development")
public class IconServiceTest {
	
	public static final Logger logger = LoggerFactory.getLogger(IconServiceTest.class);
	
	@Resource
	private IconService iconService;
	
	@Test
	public void queryIconsTest(){
		PageResult<SysIcon> result = iconService.queryIcons(null, 1, 10);
		logger.info(JsonFormatTool.formatJson(JSON.toJSONString(result)));
	}

}
