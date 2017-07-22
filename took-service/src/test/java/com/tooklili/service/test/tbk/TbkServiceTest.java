package com.tooklili.service.test.tbk;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.taobao.api.ApiException;
import com.taobao.api.internal.util.StringUtils;
import com.taobao.api.request.TbkDgItemCouponGetRequest;
import com.taobao.api.request.TbkItemGetRequest;
import com.taobao.api.request.TbkItemInfoGetRequest;
import com.taobao.api.request.TbkItemRecommendGetRequest;
import com.taobao.api.request.TbkJuTqgGetRequest;
import com.taobao.api.request.TbkShopGetRequest;
import com.taobao.api.request.TbkShopRecommendGetRequest;
import com.taobao.api.request.TbkSpreadGetRequest.TbkSpreadRequest;
import com.taobao.api.request.TbkUatmEventGetRequest;
import com.taobao.api.request.TbkUatmFavoritesGetRequest;
import com.taobao.api.request.JuItemsSearchRequest.TopItemQuery;
import com.taobao.api.response.JuItemsSearchResponse;
import com.taobao.api.response.TbkDgItemCouponGetResponse;
import com.taobao.api.response.TbkItemGetResponse;
import com.taobao.api.response.TbkItemInfoGetResponse;
import com.taobao.api.response.TbkItemRecommendGetResponse;
import com.taobao.api.response.TbkJuTqgGetResponse;
import com.taobao.api.response.TbkShopGetResponse;
import com.taobao.api.response.TbkShopRecommendGetResponse;
import com.taobao.api.response.TbkSpreadGetResponse;
import com.taobao.api.response.TbkUatmEventGetResponse;
import com.taobao.api.response.TbkUatmFavoritesGetResponse;
import com.tooklili.service.tbk.TbkService;
import com.tooklili.service.test.BaseTest;
import com.tooklili.util.JsonFormatTool;

/**
 * 淘宝客服务测试
 * @author shuai.ding
 *
 * @date 2017年6月3日下午5:01:53
 */
public class TbkServiceTest extends BaseTest{

	@Resource
	private TbkService tbkService;
	
	/**
	 * 淘宝客商品查询
	 * @author shuai.ding
	 */
	@Test
	public void getItemTest(){		
		try {
			TbkItemGetRequest req=new TbkItemGetRequest();
			req.setPageNo(1L);
			req.setPageSize(10L);
			req.setQ("衬衫");
			TbkItemGetResponse rsp = tbkService.getItem(req);			
			logger.info(JsonFormatTool.formatJson(JSON.toJSONString(rsp.getResults())));			
			logger.info("总个数:"+rsp.getTotalResults());
		} catch (ApiException e) {
			logger.error("exception",e);
		}
		
	}
	
	/**
	 * 淘宝客商品关联推荐查询
	 * @author shuai.ding
	 */
	@Test
	public void getRecommendItem(){
		try{
			TbkItemRecommendGetRequest req = new TbkItemRecommendGetRequest();
			req.setNumIid(547392707209L);
			TbkItemRecommendGetResponse rsp = tbkService.getRecommendItem(req);
			logger.info(JsonFormatTool.formatJson(JSON.toJSONString(rsp.getResults())));
		}catch(ApiException e){
			logger.error("exception",e);
		}
	}
	
	
	/**
	 * 淘宝客商品详情（简版）
	 * @author shuai.ding
	 */
	@Test
	public void getInfoTest(){
		try{
			TbkItemInfoGetRequest req = new TbkItemInfoGetRequest();
			req.setNumIids("527247846041");
			TbkItemInfoGetResponse rsp = tbkService.getInfo(req);
			logger.info(rsp.getBody());
		}catch(ApiException e){
			logger.error("exception",e);
		}
		
	}
	
	/**
	 * 淘宝客店铺查询
	 * @author shuai.ding
	 */
	@Test
	public void getShopTest(){
		try{
			TbkShopGetRequest req = new TbkShopGetRequest();
			req.setQ("女装");
			
			TbkShopGetResponse rsp = tbkService.getShop(req);
			logger.info(JsonFormatTool.formatJson(JSON.toJSONString(rsp.getResults())));
			logger.info("总个数:"+rsp.getTotalResults());
		}catch(ApiException e){
			logger.error("exception",e);
		}
	}
	
	/**
	 * 淘宝客店铺关联推荐查询
	 * @author shuai.ding
	 */
	@Test
	public void getRecommendShopTest(){
		try{
			TbkShopRecommendGetRequest req = new TbkShopRecommendGetRequest();
			req.setUserId(1092431738L);
			TbkShopRecommendGetResponse rsp = tbkService.getRecommendShop(req);
			logger.info(JsonFormatTool.formatJson(JSON.toJSONString(rsp.getResults())));
		}catch(ApiException e){
			logger.error("exception",e);
		}
	}
	
	/**
	 * 枚举正在进行中的定向招商的活动列表
	 * @author shuai.ding
	 */
	@Test
	public void getEventUatmTest(){
		try{
			TbkUatmEventGetRequest req = new TbkUatmEventGetRequest();
			TbkUatmEventGetResponse rsp = tbkService.getEventUatm(req);
			logger.info(JsonFormatTool.formatJson(JSON.toJSONString(rsp.getResults())));
			logger.info("总个数:"+rsp.getTotalResults());
		}catch(ApiException e){
			logger.error("exception",e);
		}
	}	
	
	/**
	 * 获取淘宝联盟选品库列表
	 * @author shuai.ding
	 */
	@Test
	public void getFavoritesUatmTest(){
		try{
			TbkUatmFavoritesGetRequest req = new TbkUatmFavoritesGetRequest();
			TbkUatmFavoritesGetResponse rsp = tbkService.getFavoritesUatm(req);
			logger.info(JsonFormatTool.formatJson(JSON.toJSONString(rsp.getResults())));
			logger.info("总个数:"+rsp.getTotalResults());
		}catch(ApiException e){
			logger.error("exception",e);
		}
	}
	
	/**
	 * 
	 * @author shuai.ding
	 */
	@Test
	public void getTqgJuTest(){
		try{
			TbkJuTqgGetRequest req = new TbkJuTqgGetRequest();
			req.setAdzoneId(68664126L);
			req.setStartTime(StringUtils.parseDateTime("2017-07-21 09:00:00"));
			req.setEndTime(StringUtils.parseDateTime("2017-08-09 16:00:00"));
			TbkJuTqgGetResponse rsp = tbkService.getTqgJu(req);
			logger.info(rsp.getBody());
			logger.info(JsonFormatTool.formatJson(JSON.toJSONString(rsp.getResults())));
			logger.info("总个数:"+rsp.getTotalResults());
		}catch(ApiException e){
			logger.error("exception",e);
		}
	}
	
	
	/**
	 * 物料传播方式获取
	 * @author shuai.ding
	 */
	@Test
	public void getSpreadTest(){
		try{
			List<TbkSpreadRequest> list = new ArrayList<TbkSpreadRequest>();
			TbkSpreadRequest obj3 = new TbkSpreadRequest();
			obj3.setUrl("http://temai.taobao.com");
			list.add(obj3);			
			TbkSpreadGetResponse rsp = tbkService.getSpread(list);
			logger.info(JsonFormatTool.formatJson(JSON.toJSONString(rsp.getResults())));
			logger.info("总个数:"+rsp.getTotalResults());
		}catch(ApiException e){
			logger.error("exception",e);
		}
	}
	
	/**
	 * 聚划算商品搜索接口
	 * @author shuai.ding
	 */
	@Test
	public void searchItemsJuTest(){
		try{
			TopItemQuery topItemQuery = new TopItemQuery();
			topItemQuery.setCurrentPage(1L);
			topItemQuery.setPageSize(20L);
			topItemQuery.setPid("120259453_19682654_68664126");
			topItemQuery.setWord("电风扇");
			JuItemsSearchResponse rsp = tbkService.searchItemsJu(topItemQuery);
			logger.info(JsonFormatTool.formatJson(JSON.toJSONString(rsp.getResult())));
		}catch(ApiException e){
			logger.error("exception",e);
		}
	}
	
	/**
	 * 好券清单API【导购】
	 * @author shuai.ding
	 */
	@Test
	public void getCouponItemTest(){
		try{
			TbkDgItemCouponGetRequest req = new TbkDgItemCouponGetRequest();
			req.setAdzoneId(68664126L);
			req.setQ("手机");
			TbkDgItemCouponGetResponse rsp = tbkService.getCouponItem(req);
			logger.info(JsonFormatTool.formatJson(JSON.toJSONString(rsp.getResults())));
			logger.info("总个数:"+rsp.getTotalResults());
		}catch(ApiException e){
			logger.error("exception",e);
		}
	}
}
