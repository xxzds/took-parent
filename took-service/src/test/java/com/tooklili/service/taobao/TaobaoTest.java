package com.tooklili.service.taobao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.tooklili.http.HttpCallService;
import com.tooklili.service.BaseTest;
import com.tooklili.util.JsonFormatTool;
import com.tooklili.util.result.PlainResult;

public class TaobaoTest extends BaseTest{
	
	@Resource
	private HttpCallService httpCallService;
	
	/**
	 * https://acs.m.taobao.com/h5/mtop.taobao.detail.getdetail/6.0/?data=%7B%22itemNumId%22%3A%22556559034844%22%7D&qq-pf-to=pcqq.group
	 * https://acs.m.taobao.com/h5/mtop.taobao.detail.getdetail/6.0/?data=%7B%22itemNumId%22%3A%22556559034844%22%7D&qq-pf-to=pcqq.group
	 * @author shuai.ding
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void getDetail() throws UnsupportedEncodingException{
		String url="https://acs.m.taobao.com/h5/mtop.taobao.detail.getdetail/6.0/";
		Map<String, String> params = new HashMap<String, String>();
		params.put("qq-pf-to", "pcqq.group");
		params.put("data",URLEncoder.encode("{\"itemNumId\":\""+"556559034844"+"\"}", "utf-8"));		
		PlainResult<String> result =  httpCallService.httpGet(url,params);
		logger.info(JsonFormatTool.formatJson(result.getData()));
		
//		
	
		String intro = JSON.parseObject(result.getData()).getJSONObject("data").getJSONObject("item").getString("subtitle");
		logger.info(intro);
		
		
	}

}
