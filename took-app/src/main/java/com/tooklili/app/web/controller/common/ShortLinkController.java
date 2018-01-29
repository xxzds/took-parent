package com.tooklili.app.web.controller.common;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tooklili.service.biz.intf.common.ShortLinkService;
import com.tooklili.util.result.PlainResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 短连接控制器
 * @author shuai.ding
 * @date 2017年10月31日下午1:55:36
 */
@Controller
public class ShortLinkController {
	
	@Resource
	private ShortLinkService shortLinkService;
	
	@ApiOperation(value = "生成短连接接口",notes = "生成短连接接口")
	@ApiImplicitParam(name = "url", value = "url地址", required = true, dataType = "String",paramType="query")
	@RequestMapping(value = "/getShortLinkUrl" , method = RequestMethod.POST)
	@ResponseBody
	public PlainResult<String> getShortLinkUrl(String url){
		return shortLinkService.getShortLinkUrl(url);
	}

}
