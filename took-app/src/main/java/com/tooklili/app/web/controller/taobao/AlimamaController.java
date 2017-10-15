package com.tooklili.app.web.controller.taobao;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tooklili.model.taobao.AlimamaItem;
import com.tooklili.model.taobao.AlimamaItemLink;
import com.tooklili.model.taobao.AlimamaReqItemModel;
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

}
