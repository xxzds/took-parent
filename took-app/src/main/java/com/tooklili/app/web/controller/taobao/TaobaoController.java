package com.tooklili.app.web.controller.taobao;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tooklili.service.biz.intf.taobao.TaobaoService;
import com.tooklili.util.result.ListResult;

/**
 * @author shuai.ding
 * @date 2017年9月25日下午8:35:54
 */
@Controller
public class TaobaoController {
	
	@Resource
	private TaobaoService taobaoService;
	
	/**
	 * 通过商品id，查询商品详情页的图片列表
	 * @author shuai.ding
	 * @param numIid
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/taobao/getItemImages")
	public ListResult<String> getItemImages(String numIid) throws UnsupportedEncodingException{
		ListResult<String> result = taobaoService.getItemImages(numIid);		
		return result;
	}

}
