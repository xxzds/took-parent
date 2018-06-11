package com.tooklili.app.web.controller.common;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tooklili.app.web.util.WebUtils;
import com.tooklili.dao.redis.RedisCommonRepository;
import com.tooklili.service.biz.intf.common.ShortLinkService;
import com.tooklili.util.result.PlainResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 短连接控制器
 * @author shuai.ding
 * @date 2017年10月31日下午1:55:36
 */
@Controller
public class ShortLinkController {
	
	@Resource(name="customShortLinkServiceImpl")
	private ShortLinkService shortLinkService;
	
	@Resource
	private RedisCommonRepository redisCommonRepository;
	
	@ApiOperation(value = "生成短连接接口",notes = "生成短连接接口")
	@ApiImplicitParam(name = "url", value = "url地址", required = true, dataType = "String",paramType="query")
	@RequestMapping(value = "/getShortLinkUrl" , method = RequestMethod.POST)
	@ResponseBody
	public PlainResult<String> getShortLinkUrl(String url,HttpServletRequest request){
		PlainResult<String> result = new PlainResult<String>();
		result.setData(WebUtils.getHomeUrl(request)+"/s/"+shortLinkService.getShortLinkUrl(url).getData());
		return result;
	}
	
	
	/**
	 * 短链接跳转
	 * @param key
	 * @return
	 */
	@RequestMapping("s/{key}")
	@ApiIgnore
	public String toTargetUrlByShortLink(@PathVariable String key) {
		String url = redisCommonRepository.getString(key,2);
		if(StringUtils.isEmpty(url)) {
			return "/common/404";
		}
		return "redirect:"+url;
	}

}
