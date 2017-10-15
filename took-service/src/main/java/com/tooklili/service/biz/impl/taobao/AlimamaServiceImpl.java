package com.tooklili.service.biz.impl.taobao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.tooklili.http.HttpCallService;
import com.tooklili.model.taobao.AlimamaItem;
import com.tooklili.model.taobao.AlimamaItemLink;
import com.tooklili.model.taobao.AlimamaReqItemModel;
import com.tooklili.service.biz.intf.taobao.AlimamaService;
import com.tooklili.service.util.AlimamaCookieUtils;
import com.tooklili.util.HttpClientUtil;
import com.tooklili.util.JsonFormatTool;
import com.tooklili.util.result.PageResult;
import com.tooklili.util.result.PlainResult;

/**
 * alimama服务
 * @author shuai.ding
 * @date 2017年9月26日下午3:51:59
 */
@Service
public class AlimamaServiceImpl implements AlimamaService{
	private static final Logger LOGGER = LoggerFactory.getLogger(AlimamaServiceImpl.class);
	
	@Resource
	private HttpCallService httpCallService;

	@Override
	public PageResult<AlimamaItem> superSearchItems(AlimamaReqItemModel alimamaReqItemModel) throws UnsupportedEncodingException {
		PageResult<AlimamaItem> result = new PageResult<AlimamaItem>();
		
		
		Map<String, String> params = Maps.newHashMap();
		
		//页面大小
		Integer pageSize = alimamaReqItemModel.getPerPageSize();
		if(pageSize==null){
			params.put("perPageSize", "40");
		}else{
			params.put("perPageSize", pageSize.toString());
		}
		
		//当前页
		Integer toPage = alimamaReqItemModel.getToPage();
		if(toPage!=null){
			params.put("toPage", toPage.toString());
		}
		
		//搜索的商品名称
		if(StringUtils.isNotEmpty(alimamaReqItemModel.getQ())){
			LOGGER.info("所搜商品的参数：{}",alimamaReqItemModel.getQ());
			params.put("q", URLEncoder.encode(alimamaReqItemModel.getQ(), "utf-8"));
		}
		
		//查询类型
		Integer queryType = alimamaReqItemModel.getQueryType();
		if(queryType==null){
			params.put("queryType", "0");  //默认排序
		}else{
			params.put("queryType", queryType.toString());
		}
		
		//商品标签,默认为空
		String shopTag = "";
				
		//是否包邮
		Integer freeShipment = alimamaReqItemModel.getFreeShipment();
		if(freeShipment!=null){
			params.put("freeShipment", freeShipment.toString());
		}
		
		//是否是天猫旗舰店
		Integer b2c = alimamaReqItemModel.getB2c();
		if(b2c!=null){
			params.put("b2c", b2c.toString());
			shopTag += shopTag=="" ? "b2c":",b2c";
		}
		
		//是否包含店铺优惠券
		Integer dpyhq = alimamaReqItemModel.getDpyhq();
		if(dpyhq!=null){
			params.put("dpyhq", dpyhq.toString());
			shopTag += shopTag=="" ? "dpyhq":",dpyhq";
		}
		
		//是否月成交转化率高于行业均值
		Integer hPayRate30 = alimamaReqItemModel.gethPayRate30();
		if(hPayRate30!=null){
			params.put("hPayRate30", hPayRate30.toString());
		}
		
		//是否是营销和定向计划
		Integer yxjh = alimamaReqItemModel.getYxjh();
		if(yxjh!=null && yxjh==1){
			shopTag += shopTag=="" ? "yxjh":",yxjh";
		}
		
		//商品标签
		params.put("shopTag", shopTag);
		
		PlainResult<String> responseResult = httpCallService.httpGet("https://pub.alimama.com/items/search.json",params);
		
		JSONObject data = JSON.parseObject(responseResult.getData()).getJSONObject("data");
		//分页信息
		JSONObject head = data.getJSONObject("head");
		JSONObject paginator = data.getJSONObject("paginator");
		result.setCurrentPage(head.getLong("pageNo"));
		result.setPageSize(head.getLong("pageSize"));
		result.setTotalCount(paginator.getLong("items"));

		//商品信息
		List<AlimamaItem> datas = JSON.parseArray(data.getJSONArray("pageList").toJSONString(), AlimamaItem.class);
		result.setData(datas);
		
		return result;
	}

	@Override
	public PlainResult<AlimamaItemLink> generatePromoteLink(String auctionid) {
		PlainResult<AlimamaItemLink> result = new PlainResult<AlimamaItemLink>();
				
		String url="https://pub.alimama.com/common/code/getAuctionCode.json";
		//推广类型、网站名称、投放推广位
		url+=("?adzoneid=69036167&siteid=19682654&scenes=1");
		url+=("&auctionid="+auctionid);
		LOGGER.info("请求地址:{}",url);
		String cookies=AlimamaCookieUtils.getLoginCookies();
		String content = HttpClientUtil.get(url, cookies);
		LOGGER.info(JsonFormatTool.formatJson(content));
		
		AlimamaItemLink alimamaItemLink = JSON.parseObject(content).getObject("data", AlimamaItemLink.class);
		result.setData(alimamaItemLink);
		return result;
	}

}