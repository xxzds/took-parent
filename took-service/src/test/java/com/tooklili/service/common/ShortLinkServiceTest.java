package com.tooklili.service.common;

import java.net.URLEncoder;

import javax.annotation.Resource;

import org.junit.Test;

import com.tooklili.service.BaseTest;
import com.tooklili.service.biz.intf.common.ShortLinkService;
import com.tooklili.util.result.PlainResult;

/**
 * 短连接服务测试
 * @author shuai.ding
 *
 * @date 2017年10月31日下午1:52:09
 */
public class ShortLinkServiceTest extends BaseTest{
	
	@Resource
	private ShortLinkService shortLinkService;
	
	@Test
	public void getShortLinkUrlTest(){
		try{
			String url="https://uland.taobao.com/coupon/edetail?e=%2Fthreg4FuegGQASttHIRqUm%2Fa4OKWQBtqLM9FIF8Be%2FB76UlhXoJ9wDhSE%2B8QiBtHJkYAFASt7ZtAfpUFy8Wo8qYNygNm47hDfqEFBOhTcyf6baGMjjH0pqJNTxMte1OWW2afrZ%2F5vn%2BbUPK91WnZRslvvZeHXLQR8PCCGIhN8cy2cj%2FjrDhaA%3D%3D&traceId=0bb78af715087996803295171";
			String finalUrl =  "http://www.tooklili.com:81/taobao?backurl=" +URLEncoder.encode(url,"utf-8");
			logger.info(finalUrl);
			PlainResult<String> result =  shortLinkService.getShortLinkUrl(finalUrl);
			logger.info(result.getData());
		}catch(Exception e){
			logger.error("exception",e);
		}		
	}

}
