package com.tooklili.service.tooklili;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.tooklili.model.tooklili.Item;
import com.tooklili.service.BaseTest;
import com.tooklili.service.biz.intf.tooklili.ItemService;
import com.tooklili.util.JsonFormatTool;
import com.tooklili.util.result.PageResult;
import com.tooklili.util.result.PlainResult;

/**
 * @author shuai.ding
 * @date 2017年10月16日下午2:55:26
 */
public class ItemMongoServiceTest extends BaseTest{
	
	@Resource
	private ItemService itemService;
	
	@Test
	public void queryCouponItemsByCateId(){
		PageResult<Item> result = itemService.queryCouponItemsByCateId(35, null, null);
		logger.info(JsonFormatTool.formatJson(JSON.toJSONString(result)));
	}
	
	@Test
	public void queryItemById(){
		PlainResult<Item> result = itemService.queryItemById(321864L);
		logger.info(JsonFormatTool.formatJson(JSON.toJSONString(result)));
	}

}
