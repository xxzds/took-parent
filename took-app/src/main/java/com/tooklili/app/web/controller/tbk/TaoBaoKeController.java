package com.tooklili.app.web.controller.tbk;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobao.api.ApiException;
import com.taobao.api.request.TbkItemGetRequest;
import com.taobao.api.response.TbkItemGetResponse;
import com.tooklili.app.web.util.AppUtil;
import com.tooklili.service.tbk.TbkService;

/**
 * 淘宝客控制器
 * @author shuai.ding
 *
 * @date 2017年6月14日上午11:18:44
 */	
@Controller
@RequestMapping("/tbk")
public class TaoBaoKeController {
	private static final Logger LOGGER =LoggerFactory.getLogger(TaoBaoKeController.class);
	@Resource
	private TbkService tbkService;

	@RequestMapping("/getItem")
	@ResponseBody
	public Object getItem(String searchName) throws ApiException{
		LOGGER.info("getItem请求参数：{}",searchName);
		TbkItemGetRequest req=new TbkItemGetRequest();
		if(StringUtils.isNotEmpty(searchName)){
			req.setQ(searchName);
		}else{
			req.setQ("红酒");
		}
	
		TbkItemGetResponse rsp = tbkService.getItem(req);
		return AppUtil.conversionJsonp(rsp.getBody());
	}
}
