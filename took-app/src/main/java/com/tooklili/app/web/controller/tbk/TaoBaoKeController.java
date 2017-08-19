package com.tooklili.app.web.controller.tbk;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobao.api.ApiException;
import com.taobao.api.request.TbkItemGetRequest;
import com.taobao.api.response.TbkItemGetResponse;
import com.tooklili.app.web.util.AppUtil;
import com.tooklili.service.biz.api.tbk.TbkApiService;
import com.tooklili.service.biz.tbk.TbkService;
import com.tooklili.util.PropertiesUtil;
import com.tooklili.util.result.PageResult;
import com.tooklili.vo.tbk.TbkItemReqVo;
import com.tooklili.vo.tbk.TbkItemRespVo;

/**
 * 淘宝客控制器
 * @author shuai.ding
 *
 * @date 2017年6月14日上午11:18:44
 */	
@Controller
@RequestMapping("/tbk")
public class TaoBaoKeController {
	@Resource
	private TbkApiService tbkApiService;
	
	@Resource
	private TbkService tbkService;

	@RequestMapping("/getItem")
	@ResponseBody
	public Object getItem(String searchName) throws ApiException{
		TbkItemGetRequest req=new TbkItemGetRequest();
		if(StringUtils.isNotEmpty(searchName)){
			req.setQ(searchName);
		}else{
			req.setQ("红酒");
		}
	
		TbkItemGetResponse rsp = tbkApiService.getItem(req);
		return AppUtil.conversionJsonp(rsp.getBody());
	}
	
	
	/**
	 * 查询商品列表
	 * @param tbkItemReqVo
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping("/getItems")
	@ResponseBody
	public PageResult<TbkItemRespVo> getItems(TbkItemReqVo tbkItemReqVo) throws ApiException{
		
		PageResult<TbkItemRespVo> result = tbkService.getItems(tbkItemReqVo);
		return result;
	}
	
	@RequestMapping("/tojump/{itemId}")
	public String jumpToTaobao(@PathVariable String itemId,Model model){
		model.addAttribute("itemId", itemId);
		model.addAttribute("pid", PropertiesUtil.getInstance("tbk.properties").getValue("tbk.pid"));
		return "tbk/jumpToTb";
	}
}
