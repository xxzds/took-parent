package com.tooklili.service.alimama;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.tooklili.http.HttpCallService;
import com.tooklili.service.BaseTest;
import com.tooklili.util.HttpClientUtil;
import com.tooklili.util.JsonFormatTool;
import com.tooklili.util.result.PlainResult;

public class AlimamaServiceTest  extends BaseTest{
	
	@Resource
	private HttpCallService httpCallService;
	
	
	/**
	 * q:电风扇
_t:1506412177959
auctionTag:
perPageSize:40
shopTag:yxjh
t:1506412177968
_tb_token_:b3a7ee05ae6b
pvid:10_220.178.25.22_1388_1506412083091



q:电风扇 落地
_t:1506413980279
auctionTag:
perPageSize:40
shopTag:yxjh
t:1506413980282
_tb_token_:b3a7ee05ae6b
pvid:10_220.178.25.22_1590_1506413974418


q:手机
_t:1506418056375
auctionTag:
perPageSize:40
shopTag:yxjh
t:1506418056378
_tb_token_:b3a7ee05ae6b
pvid:10_220.178.25.22_1622_1506418050811

spm:a219t.7664554.1998457203.dfb730492.484397ae2XLta3
toPage:1
queryType:2
type:table
dpyhq:1
auctionTag:
perPageSize:40
shopTag:yxjh,dpyhq
t:1506419402654
_tb_token_:b3a7ee05ae6b
pvid:10_220.178.25.22_584_1506419397407

spm:a219t.7664554.1998457203.dfb730492.484397ae2XLta3
toPage:1
queryType:2
type:table
auctionTag:
perPageSize:40
shopTag:
t:1506419487994
_tb_token_:b3a7ee05ae6b
pvid:10_220.178.25.22_1773_1506419402203
	 * @author shuai.ding
	 */
	@Test
	public void millsToDateStr(){
		Date date =new Date(1506412177959L);   //毫秒数
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(simpleDateFormat.format(date));
	}
	
	/**
	 * 超级搜索接口
	 * @author shuai.ding
	 */
	@Test
	public void superSearchTest(){
		
		Map<String, String> params = Maps.newHashMap();
		params.put("q", "手机");
		params.put("perPageSize","40");
		params.put("dpyhq", "1");  //店铺优惠券
		params.put("shopTag","dpyhq");
		
		PlainResult<String> result = httpCallService.httpGet("https://pub.alimama.com/items/search.json",params);
		
		
		String finalResult = JSON.parseObject(result.getData()).getJSONObject("data").getJSONArray("pageList").toJSONString();
		
		logger.info(JsonFormatTool.formatJson(finalResult));
		
	}
	
	@Test
	public void a(){
		
		Map<String, String> params = Maps.newHashMap();
		params.put("auctionid", "556495218786");
		params.put("adzoneid","2");
		params.put("siteid","2");
		params.put("scenes","2");
		params.put("t","2");
		params.put("_tb_token_","2");
		params.put("pvid","2");
		
		PlainResult<String> result = httpCallService.httpGet("http://pub.alimama.com/common/code/getAuctionCode.json",params);
		
		logger.info(JsonFormatTool.formatJson(result.getData()));
		
	}
	
	
	/**
	 * 获取短链接、长链接、二维码、淘口令
	 */
	@Test
	public void getLianjie(){
//		Map<String, String> params = Maps.newHashMap();
//		params.put("auctionid", "558386463934");
//		params.put("adzoneid","69036167");
//		params.put("siteid","19682654");
//		params.put("scenes","1");
//		params.put("t","1507970430118");
//		params.put("_tb_token_","fed381b34a3e3");
//		params.put("pvid", "10_211.162.8.113_2145_1507970029219");
//		PlainResult<String> result = httpCallService.httpGet("https://pub.alimama.com/common/code/getAuctionCode.json",params);
		
		String url="https://pub.alimama.com/common/code/getAuctionCode.json?pvid=10_211.162.8.113_2145_1507970029219&auctionid=558386463934&t=1507970430118&scenes=1&adzoneid=69036167&siteid=19682654&_tb_token_=fed381b34a3e3";
		String cookies="t=bad3a9ecdcc777d375f4c3f03182e121; cookie2=192561358b84d264b6378d35491b140f; v=0; _tb_token_=fed381b34a3e3; alimamapwag=TW96aWxsYS81LjAgKE1hY2ludG9zaDsgSW50ZWwgTWFjIE9TIFggMTBfMTJfNikgQXBwbGVXZWJLaXQvNTM3LjM2IChLSFRNTCwgbGlrZSBHZWNrbykgQ2hyb21lLzYxLjAuMzE2My4xMDAgU2FmYXJpLzUzNy4zNg%3D%3D; cookie32=188ac1e5db32c5916d7e368d0254cbbf; alimamapw=QwADXQkDDg0GbFEHVgYEUVZTBwAAUwEEVF8DBlIFAQFQAVZVUVYEAF1U; cookie31=MTIwMjU5NDUzLHRiNjkxNTk0NSwxMTE0MzMyOTA1QHFxLmNvbSxUQg%3D%3D; login=V32FPkk%2Fw0dUvg%3D%3D; account-path-guide-s1=true; 120259453_yxjh-filter-1=true; cna=jIhOEvvOPDwCAdOiCPf1QSjA; apushf38b938a083cdb84fd19afeeb6a41a19=%7B%22ts%22%3A1507970429287%2C%22parentId%22%3A1507967366375%7D; isg=AoyMHhLFOT4vJy1CV2C5ZvHhXex-bTEKFGQZc-ZUjjH_cRB7LtdO_uGHZ08y";
		String content = HttpClientUtil.get(url, cookies);
		logger.info(JsonFormatTool.formatJson(content));
	}

}
