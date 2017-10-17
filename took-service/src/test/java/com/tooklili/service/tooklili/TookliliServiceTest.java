package com.tooklili.service.tooklili;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.tooklili.model.tooklili.Item;
import com.tooklili.service.BaseTest;
import com.tooklili.service.biz.intf.tooklili.ItemService;
import com.tooklili.util.JsonFormatTool;
import com.tooklili.util.result.ListResult;
import com.tooklili.util.result.PageResult;
import com.tooklili.util.result.PlainResult;

/**
 * 
 * @author ding.shuai
 * @date 2017年9月16日上午11:23:51
 */
public class TookliliServiceTest extends BaseTest{

	@Resource(name="itemRedisServiceImpl")
	private ItemService itemService;
	
	@Test
	public void queryCouponItemsByCateId(){
		PageResult<Item> result =  itemService.queryCouponItemsByCateId(35, 2L, 1069L);		
		logger.info(JsonFormatTool.formatJson(JSON.toJSONString(result)));
	}
	
	@Test
	public void queryItemByIdTest(){
		PlainResult<Item> result = itemService.queryItemById(156801L);
		logger.info(JsonFormatTool.formatJson(JSON.toJSONString(result)));
	}
	
	@Test
	public void getRandomItemByCateIdTest(){
		ListResult<Item> result = itemService.getRandomItemByCateId(36, 3);
		logger.info(JsonFormatTool.formatJson(JSON.toJSONString(result)));
	}
}
