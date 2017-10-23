package com.tooklili.service.tbk;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.taobao.api.ApiException;
import com.taobao.api.request.TbkDgItemCouponGetRequest;
import com.taobao.api.response.TbkDgItemCouponGetResponse.TbkCoupon;
import com.tooklili.dao.intf.tooklili.ItemDao;
import com.tooklili.http.HttpCallService;
import com.tooklili.model.taobao.ItemImageDetailModel;
import com.tooklili.model.tooklili.Item;
import com.tooklili.model.tooklili.ItemModel;
import com.tooklili.service.BaseTest;
import com.tooklili.service.biz.intf.taobao.TbkService;
import com.tooklili.util.Arith;
import com.tooklili.util.DateUtil;
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
	
	@Resource
	private ItemDao itemDao;
	
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
	
	@Test
	public void getCouponItemsTest(){
		try{
			TbkDgItemCouponGetRequest req = new TbkDgItemCouponGetRequest();
			req.setPageNo(1L);
			req.setPageSize(2L);
			//男装、女装/女士精品
			req.setCat("30,16");
			PageResult<TbkCoupon> result = tbkService.getCouponItems(req);
			logger.info(JsonFormatTool.formatJson(JSON.toJSONString(result)));
			
			TbkCoupon tbkCoupon = result.getData().get(0);
			
			Long numIid = tbkCoupon.getNumIid();
			Item item = itemDao.queryItemBynumId(numIid);
			
			ItemModel itemModel = new ItemModel();
			itemModel.setCouponStartTime(String.valueOf(DateUtil.parseDate(tbkCoupon.getCouponStartTime(),DateUtil.DEFAULT_DAY_STYLE).getTime()/1000));
			itemModel.setCouponEndTime(String.valueOf(DateUtil.parseDate(tbkCoupon.getCouponEndTime(),DateUtil.DEFAULT_DAY_STYLE).getTime()/1000));
			itemModel.setQuanSurplus(tbkCoupon.getCouponTotalCount());
			itemModel.setQuanReceive(tbkCoupon.getCouponTotalCount()-tbkCoupon.getCouponRemainCount());
			itemModel.setCouponRate(String.valueOf(tbkCoupon.getCouponRemainCount()));
			itemModel.setVolume(tbkCoupon.getVolume().toString());
			itemModel.setAddTime(String.valueOf(new Date().getTime()/1000));
			String zkFinalPrice = tbkCoupon.getZkFinalPrice();
			itemModel.setPrice(tbkCoupon.getZkFinalPrice());
			
			String couponInfo =tbkCoupon.getCouponInfo();			
			String pattern="满(\\d+?)元减(\\d+?)元";			
			Matcher m = Pattern.compile(pattern).matcher(couponInfo);
			 if (m.find()) {
				 if(StringUtils.isNotEmpty(m.group(1))){
					 itemModel.setQuanCondition(m.group(1));
				 }else{
					 itemModel.setQuanCondition("");
				 }
				
				 itemModel.setQuan(m.group(2));
			 }		
			double couponPrice = Arith.sub(Double.valueOf(zkFinalPrice),Double.valueOf(itemModel.getQuan()));
			itemModel.setCouponPrice(String.valueOf(couponPrice));
			if(item!=null){ //更新
				itemModel.setId(item.getId());
				itemDao.updateItemById(itemModel);
				logger.info("更新数据库的商品主键为：{}",itemModel.getId());
			}else{  //insert
				itemModel.setCateId(35);
				itemModel.setNumIid(tbkCoupon.getNumIid());
				itemModel.setTitle(tbkCoupon.getTitle());
				itemModel.setPicUrl(tbkCoupon.getPictUrl());
				
				itemModel.setQuanUrl(tbkCoupon.getCouponClickUrl());
				
				itemModel.setIntro(tbkCoupon.getItemDescription());
				itemModel.setNick(tbkCoupon.getNick());
				itemModel.setSellerId(tbkCoupon.getSellerId().toString());
				itemModel.setClickUrl(tbkCoupon.getCouponClickUrl());
				itemModel.setIsq(1);
				itemModel.setItemUrl(tbkCoupon.getItemUrl());
				String commissionRate = tbkCoupon.getCommissionRate();
				itemModel.setCommissionRate(tbkCoupon.getCommissionRate());

				double commission = Arith.mul(Double.parseDouble(zkFinalPrice), Double.parseDouble(commissionRate)/100);
				itemModel.setCommission(String.valueOf(Arith.round(commission, 2)));
				
				
				itemDao.insertItem(itemModel);
				logger.info("插入数据库的商品主键为：{}",itemModel.getId());
			}
			
						
			
			
			
		}catch(Exception e){
			logger.error("exception",e);
		}
	}
	
	
	@Test
	public void getCouponItemsTest2(){
		try{
			TbkDgItemCouponGetRequest req = new TbkDgItemCouponGetRequest();
			req.setPageNo(1L);
			req.setPageSize(1L);
			req.setQ("如果 zakka杂货红色电话亭欧式创意家居装饰小摆件办公桌摆件可爱");
			PageResult<TbkCoupon> result = tbkService.getCouponItems(req);
			logger.info(JsonFormatTool.formatJson(JSON.toJSONString(result)));
			
			TbkCoupon tbkCoupon = result.getData().get(0);
			logger.info(JsonFormatTool.formatJson(JSON.toJSONString(tbkCoupon)));
		}catch(Exception e){
			logger.error("exception",e);
		}
	}
}
