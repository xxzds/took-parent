package com.tooklili.service.test.tbk;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.taobao.api.ApiException;
import com.taobao.api.internal.util.StringUtils;
import com.taobao.api.request.JuItemsSearchRequest.TopItemQuery;
import com.taobao.api.request.TbkDgItemCouponGetRequest;
import com.taobao.api.request.TbkItemGetRequest;
import com.taobao.api.request.TbkItemInfoGetRequest;
import com.taobao.api.request.TbkItemRecommendGetRequest;
import com.taobao.api.request.TbkJuTqgGetRequest;
import com.taobao.api.request.TbkShopGetRequest;
import com.taobao.api.request.TbkShopRecommendGetRequest;
import com.taobao.api.request.TbkSpreadGetRequest.TbkSpreadRequest;
import com.taobao.api.request.TbkTpwdCreateRequest;
import com.taobao.api.request.TbkUatmEventGetRequest;
import com.taobao.api.request.TbkUatmFavoritesGetRequest;
import com.taobao.api.response.JuItemsSearchResponse;
import com.taobao.api.response.TbkDgItemCouponGetResponse;
import com.taobao.api.response.TbkItemGetResponse;
import com.taobao.api.response.TbkItemInfoGetResponse;
import com.taobao.api.response.TbkItemRecommendGetResponse;
import com.taobao.api.response.TbkJuTqgGetResponse;
import com.taobao.api.response.TbkShopGetResponse;
import com.taobao.api.response.TbkShopRecommendGetResponse;
import com.taobao.api.response.TbkSpreadGetResponse;
import com.taobao.api.response.TbkTpwdCreateResponse;
import com.taobao.api.response.TbkUatmEventGetResponse;
import com.taobao.api.response.TbkUatmFavoritesGetResponse;
import com.tooklili.service.biz.intf.taobao.TbkApiService;
import com.tooklili.service.test.BaseTest;
import com.tooklili.util.JsonFormatTool;

/**
 * 淘宝客服务测试
 * @author shuai.ding
 *
 * @date 2017年6月3日下午5:01:53
 */
public class TbkApiServiceTest extends BaseTest{

	@Resource
	private TbkApiService tbkApiService;
	
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
			TbkItemGetResponse rsp = tbkApiService.getItem(req);			
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
			TbkItemRecommendGetResponse rsp = tbkApiService.getRecommendItem(req);
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
			TbkItemInfoGetResponse rsp = tbkApiService.getInfo(req);
			logger.info(JsonFormatTool.formatJson(rsp.getBody()));
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
			
			TbkShopGetResponse rsp = tbkApiService.getShop(req);
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
			TbkShopRecommendGetResponse rsp = tbkApiService.getRecommendShop(req);
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
			TbkUatmEventGetResponse rsp = tbkApiService.getEventUatm(req);
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
			TbkUatmFavoritesGetResponse rsp = tbkApiService.getFavoritesUatm(req);
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
			TbkJuTqgGetResponse rsp = tbkApiService.getTqgJu(req);
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
			obj3.setUrl("http://item.taobao.com/item.htm?id=548090168967");
			list.add(obj3);			
			TbkSpreadGetResponse rsp = tbkApiService.getSpread(list);
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
			JuItemsSearchResponse rsp = tbkApiService.searchItemsJu(topItemQuery);
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
			TbkDgItemCouponGetResponse rsp = tbkApiService.getCouponItem(req);
			logger.info(JsonFormatTool.formatJson(JSON.toJSONString(rsp.getResults())));
			logger.info("总个数:"+rsp.getTotalResults());
		}catch(ApiException e){
			logger.error("exception",e);
		}
	}
	
	/**
	 * 淘宝客淘口令
	 * @author shuai.ding
	 */
	@Test
	public void createTpwdTest(){
		try{
			TbkTpwdCreateRequest req = new TbkTpwdCreateRequest();
			//粘贴淘口令，到淘宝app中，弹出层提示信息
			req.setText("歌兔呢 ");
			req.setUrl("https://uland.taobao.com/coupon/edetail?activityId=779e7c90a5d146bca4cbd0482538b0fb&pid=mm_120259453_19682654_69036167&itemId=556837356380&src=cd_cdll");			
			TbkTpwdCreateResponse rsp = tbkApiService.createTpwd(req);
			
			if(StringUtils.areNotEmpty(rsp.getErrorCode())){
				logger.info(rsp.getSubMsg());
			}else{
				logger.info(JsonFormatTool.formatJson(JSON.toJSONString(rsp.getData())));
			}
		}catch(ApiException e){
			logger.error("exception",e);
		}
	}
}
