package com.tooklili.app.web.controller.taobao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tooklili.model.taobao.AlimamaItem;
import com.tooklili.model.taobao.AlimamaItemLink;
import com.tooklili.model.taobao.AlimamaReqItemModel;
import com.tooklili.service.biz.intf.common.ShortLinkService;
import com.tooklili.service.biz.intf.taobao.AlimamaService;
import com.tooklili.util.result.PageResult;
import com.tooklili.util.result.PlainResult;

/**
 * 调用alimama接口控制器
 * @author shuai.ding
 *
 * @date 2017年10月15日下午5:05:04
 */
@Controller
public class AlimamaController {
	
	@Resource
	private AlimamaService alimamaService;
	
	@Resource
	private ShortLinkService shortLinkService;
	
	@RequestMapping("/superSearchItems")
	@ResponseBody
	public PageResult<AlimamaItem> superSearchItems(AlimamaReqItemModel alimamaReqItemModel) throws UnsupportedEncodingException{
		return alimamaService.superSearchItems(alimamaReqItemModel);
	}
	
	@RequestMapping("/generatePromoteLink")
	@ResponseBody
	public PlainResult<AlimamaItemLink> generatePromoteLink(String auctionid){
		return alimamaService.generatePromoteLink(auctionid);
	}
	
	
	/**
	 * 获取淘口令和短链接信息
	 * @author shuai.ding
	 * @param auctionid
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/getTwdAndShortLinkInfo")
	@ResponseBody
	public PlainResult<AlimamaItemLink> getTwdInfo(String auctionid) throws UnsupportedEncodingException{
		PlainResult<AlimamaItemLink> result = alimamaService.generatePromoteLink(auctionid);
		if(!result.isSuccess()){
			return result;
		}

		AlimamaItemLink alimamaItemLink = result.getData();
			
		if(StringUtils.isNotEmpty(alimamaItemLink.getCouponShortLinkUrl())){
			alimamaItemLink.setCustomCouponShortLinkUrl(shortLinkService.getShortLinkUrl("http://www.tooklili.com:81/taobao?backurl=" +URLEncoder.encode(alimamaItemLink.getCouponShortLinkUrl(),"utf-8"))
					.getData());
		}
		
		if(StringUtils.isNotEmpty(alimamaItemLink.getShortLinkUrl())){
			alimamaItemLink.setCustomShortLinkUrl(shortLinkService.getShortLinkUrl("http://www.tooklili.com:81/taobao?backurl=" +URLEncoder.encode(alimamaItemLink.getShortLinkUrl(),"utf-8"))
					.getData());
		}
		
		return result;
	}

}
