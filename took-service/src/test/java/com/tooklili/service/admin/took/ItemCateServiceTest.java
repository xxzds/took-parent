package com.tooklili.service.admin.took;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.tooklili.model.admin.TookItemCate;
import com.tooklili.service.biz.intf.admin.took.ItemCateService;
import com.tooklili.util.JsonFormatTool;
import com.tooklili.util.result.PageResult;

/**
 * 商品分类服务测试
 * @author ding.shuai
 * @date 2018年2月9日下午4:40:23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-service-admin.xml"})
@ActiveProfiles("development")
public class ItemCateServiceTest{
	
	private static final Logger logger = LoggerFactory.getLogger(ItemCateServiceTest.class);
	
	@Resource
	private ItemCateService itemCateService;
	
	@Test
	public void getItemCates(){
		PageResult<TookItemCate> result = itemCateService.getItemCates(null, 1, 10);
		logger.info(JsonFormatTool.formatJson(JSON.toJSONString(result)));
	}

}
