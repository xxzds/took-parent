package com.tooklili.app.web.controller.taobao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.tooklili.app.web.util.WebUtils;
import com.tooklili.convert.taobao.AlimamaItemConverter;
import com.tooklili.model.taobao.AlimamaItem;
import com.tooklili.model.taobao.AlimamaItemLink;
import com.tooklili.model.taobao.AlimamaReqItemModel;
import com.tooklili.model.tooklili.Item;
import com.tooklili.service.biz.intf.common.ShortLinkService;
import com.tooklili.service.biz.intf.taobao.AlimamaService;
import com.tooklili.util.result.ListResult;
import com.tooklili.util.result.PageResult;
import com.tooklili.util.result.PlainResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 调用alimama接口控制器
 * @author shuai.ding
 *
 * @date 2017年10月15日下午5:05:04
 */
//@Api(tags={"超级搜索接口"},produces="application/json")
@RestController
public class AlimamaController {
	
	@Resource
	private AlimamaService alimamaService;
	
	@Resource
	private ShortLinkService shortLinkService;
	
	@ApiOperation(value = "超级搜索商品接口", notes = "超级搜索商品接口")
	@RequestMapping(value = "/superSearchItems",method=RequestMethod.POST)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "currentPage", value = "当前页", required = false, dataType = "Integer",paramType="query"),
		@ApiImplicitParam(name = "pageSize", value = "页面大小", required = false, dataType = "Integer",paramType="query")
	})
	public PageResult<Item> superSearchItems(AlimamaReqItemModel alimamaReqItemModel,Integer currentPage,Integer pageSize) throws UnsupportedEncodingException{
		PageResult<Item> result = new PageResult<Item>();
		if(alimamaReqItemModel != null) {
			if(currentPage != null) alimamaReqItemModel.setToPage(currentPage);
			if(pageSize != null)  alimamaReqItemModel.setPerPageSize(pageSize);
		}
		PageResult<AlimamaItem> pageResult = alimamaService.superSearchItems(alimamaReqItemModel);
		
		result.setCode(pageResult.getCode());
		result.setMessage(pageResult.getMessage());
		result.setPageSize(pageResult.getPageSize());
		result.setCurrentPage(pageResult.getCurrentPage());
		result.setTotalCount(pageResult.getTotalCount());
		result.setData(Lists.transform(pageResult.getData(), new Function<AlimamaItem, Item>() {
			@Override
			public Item apply(AlimamaItem input) {
				return AlimamaItemConverter.alimamaItemConverItem(input);
			}
		}));		
		return result;
	}
	
	@ApiIgnore
	@RequestMapping(value = "/generatePromoteLink", method = RequestMethod.POST)
	public PlainResult<AlimamaItemLink> generatePromoteLink(String auctionid,String userFlag){
		//非空，且为正整数
		if(StringUtils.isEmpty(userFlag) || !Pattern.matches("^[0-9]*[1-9][0-9]*$", userFlag)){
			userFlag = "1";
		}		
		Long cookieId = Long.parseLong(userFlag);
		return alimamaService.generatePromoteLink(auctionid,cookieId);
	}
	
	
	/**
	 * 获取淘口令和短链接信息
	 * @author shuai.ding
	 * @param auctionid
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ApiOperation(value = "获取淘口令和短链接信息", notes = "获取淘口令和短链接信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "auctionid", value = "商品id", required = true, dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "userFlag", value = "用户标识", dataType = "String",paramType="query")
	})	
	@RequestMapping(value = "/getTwdAndShortLinkInfo",method=RequestMethod.POST)
	public PlainResult<AlimamaItemLink> getTwdInfo(String auctionid,String userFlag,HttpServletRequest request) throws UnsupportedEncodingException{
		PlainResult<AlimamaItemLink> result = new PlainResult<AlimamaItemLink>();
		//为空，默认为1
		if(StringUtils.isEmpty(userFlag)){
			userFlag = "1";
		}
		if(!Pattern.matches("^[0-9]*[1-9][0-9]*$", userFlag)){
			return result.setErrorMessage("userFlag必须为正整数");
		}
		Long cookieId = Long.parseLong(userFlag);
			
		result = alimamaService.generatePromoteLink(auctionid,cookieId);
		if(!result.isSuccess()){
			return result;
		}

		AlimamaItemLink alimamaItemLink = result.getData();
			
		if(StringUtils.isNotEmpty(alimamaItemLink.getCouponShortLinkUrl())){
			alimamaItemLink.setCustomCouponShortLinkUrl(WebUtils.getHomeUrl(request)+"/s/"+shortLinkService.getShortLinkUrl("http://www.tooklili.com:81/taobao?backurl=" +URLEncoder.encode(alimamaItemLink.getCouponShortLinkUrl(),"utf-8"))
					.getData());
		}
		
		if(StringUtils.isNotEmpty(alimamaItemLink.getShortLinkUrl())){
			alimamaItemLink.setCustomShortLinkUrl(WebUtils.getHomeUrl(request)+"/s/"+shortLinkService.getShortLinkUrl("http://www.tooklili.com:81/taobao?backurl=" +URLEncoder.encode(alimamaItemLink.getShortLinkUrl(),"utf-8"))
					.getData());
		}
		
		return result;
	}
	
	/**
	 * 搜索框智能提示
	 * @param q   关键字
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ApiOperation(value = "搜索框智能提示", notes = "搜索框智能提示")
	@ApiImplicitParam(name = "q", value = "关键字", required = false, dataType = "String",paramType="query")
	@RequestMapping(value = "/suggest",method=RequestMethod.POST)
	public ListResult<String> suggest(String q) throws UnsupportedEncodingException{
		return alimamaService.suggest(q);
	}

}
