package com.tooklili.service.test.tbk;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.taobao.api.ApiException;
import com.tooklili.service.biz.api.tbk.TbkService;
import com.tooklili.service.test.BaseTest;
import com.tooklili.util.JsonFormatTool;
import com.tooklili.util.result.PageResult;
import com.tooklili.util.result.PlainResult;
import com.tooklili.vo.tbk.TbkItemDetailRespVo;
import com.tooklili.vo.tbk.TbkItemReqVo;
import com.tooklili.vo.tbk.TbkItemRespVo;

/**
 * 淘宝客服务测试
 * @author shuai.ding
 *
 * @date 2017年8月20日上午9:53:18
 */
public class TbkServiceTest extends BaseTest{

	@Resource
	private TbkService tbkService;
	
	/**
	 * 获取商品测试
	 * @author shuai.ding
	 */
	@Test
	public void getItemsTest(){		
		try {
			TbkItemReqVo tbkItemReqVo = new TbkItemReqVo();
			tbkItemReqVo.setItemName("杯子");
			PageResult<TbkItemRespVo> result = tbkService.getItems(tbkItemReqVo);
			logger.info(JsonFormatTool.formatJson(JSON.toJSONString(result.getData())));
		} catch (ApiException e) {
			logger.error("exception",e);
		}
	}
	
	/**
	 * 通过商品id，查询商品信息
	 * @author shuai.ding
	 */
	@Test
	public void getItemDetail(){
		try{
			PlainResult<TbkItemDetailRespVo> result = tbkService.getItemDetail("556837356380");
			logger.info(JsonFormatTool.formatJson(JSON.toJSONString(result.getData())));
		}catch(Exception e){
			logger.error("exception",e);
		}
	}
}
