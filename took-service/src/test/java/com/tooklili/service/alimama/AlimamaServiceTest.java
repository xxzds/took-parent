	package com.tooklili.service.alimama;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.google.common.collect.Maps;
import com.tooklili.http.HttpCallService;
import com.tooklili.service.BaseTest;
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
		params.put("perPageSize","2");
		params.put("dpyhq", "1");  //店铺优惠券
		params.put("shopTag","yxjh,dpyhq");
		
		PlainResult<String> result = httpCallService.httpGet("https://pub.alimama.com/items/search.json",params);
		
		logger.info(JsonFormatTool.formatJson(result.getData()));
		
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

}
