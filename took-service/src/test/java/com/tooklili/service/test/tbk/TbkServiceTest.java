package com.tooklili.service.test.tbk;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.taobao.api.ApiException;
import com.tooklili.http.HttpCallService;
import com.tooklili.model.taobao.ItemImageDetailModel;
import com.tooklili.service.biz.intf.taobao.TbkService;
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
	
	@Resource
	private HttpCallService httpCallService;
	
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
	
	/**
	 * https://hws.m.taobao.com/cache/mtop.wdetail.getItemDescx/4.1/?data=%7Bitem_num_id%3A%2240545413689%22%7D
	 * 查询商品详情图片
	 * @author shuai.ding
	 */
	@Test
	public void getItemImages(){
		try{
			String url ="https://hws.m.taobao.com/cache/mtop.wdetail.getItemDescx/4.1/";
			Map<String, String> params = new HashMap<String, String>();
			String itemNumId="528081340923";
			params.put("data",URLEncoder.encode("{item_num_id:\""+itemNumId+"\"}", "utf-8"));
			//此接口支持jsonp
//			params.put("type", "jsonp");
//			params.put("dataType", "jsonp");
			PlainResult<String> responseResult = httpCallService.httpGet(url,params);
			ItemImageDetailModel itemImageDetailModel = JSON.parseObject(responseResult.getData(), ItemImageDetailModel.class);
			logger.info(itemImageDetailModel.getData().getImages().toString());
		}catch(Exception e){
			logger.error("exception",e);
		}
	}
}
