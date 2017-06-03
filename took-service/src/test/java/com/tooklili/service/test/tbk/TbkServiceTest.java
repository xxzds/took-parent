package com.tooklili.service.test.tbk;

import javax.annotation.Resource;

import org.junit.Test;

import com.taobao.api.ApiException;
import com.taobao.api.request.TbkItemGetRequest;
import com.taobao.api.request.TbkItemInfoGetRequest;
import com.taobao.api.response.TbkItemGetResponse;
import com.taobao.api.response.TbkItemInfoGetResponse;
import com.tooklili.service.tbk.TbkService;
import com.tooklili.service.test.BaseTest;

/**
 * 淘宝客服务测试
 * @author shuai.ding
 *
 * @date 2017年6月3日下午5:01:53
 */
public class TbkServiceTest extends BaseTest{

	@Resource
	private TbkService tbkService;
	
	@Test
	public void getItemTest() throws ApiException{
		TbkItemGetRequest req=new TbkItemGetRequest();
		req.setQ("红酒");
		TbkItemGetResponse rsp = tbkService.getItem(req);
		logger.info(rsp.getBody());
	}
	
	@Test
	public void getInfoTest() throws ApiException{
		TbkItemInfoGetRequest req = new TbkItemInfoGetRequest();
		req.setNumIids("527247846041");
		TbkItemInfoGetResponse rsp = tbkService.getInfo(req);
		logger.info(rsp.getBody());
	}
	
}
