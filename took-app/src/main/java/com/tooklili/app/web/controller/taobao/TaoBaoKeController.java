package com.tooklili.app.web.controller.taobao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobao.api.ApiException;
import com.taobao.api.request.TbkDgItemCouponGetRequest;
import com.taobao.api.response.TbkDgItemCouponGetResponse.TbkCoupon;
import com.tooklili.model.taobao.TpwdAndShortUrlModel;
import com.tooklili.service.biz.intf.common.ShortLinkService;
import com.tooklili.service.biz.intf.taobao.TbkService;
import com.tooklili.util.PropertiesUtil;
import com.tooklili.util.result.PageResult;
import com.tooklili.util.result.PlainResult;
import com.tooklili.vo.tbk.TbkItemDetailRespVo;
import com.tooklili.vo.tbk.TbkItemReqVo;
import com.tooklili.vo.tbk.TbkItemRespVo;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

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
	private TbkService tbkService;
	
	@Resource
	private ShortLinkService shortLinkService;
		
	/**
	 * 查询商品列表
	 * @param tbkItemReqVo
	 * @return
	 * @throws ApiException
	 */
	@ApiOperation(value = "查询商品列表",notes = "查询商品列表")
	@RequestMapping(value = "/getItems",method = RequestMethod.POST)
	@ResponseBody
	public PageResult<TbkItemRespVo> getItems(TbkItemReqVo tbkItemReqVo) throws ApiException{
		PageResult<TbkItemRespVo> result = tbkService.getItems(tbkItemReqVo);
		return result;
	}
	
	
	/**
	 * 通过商品id，查询商品信息
	 * @author shuai.ding
	 * @param numIid
	 * @return
	 * @throws ApiException
	 */
	@ApiOperation(value = "通过商品id，查询商品信息",notes = "通过商品id，查询商品信息")
	@ApiImplicitParam(name = "numIid", value = "商品id", required = true, dataType = "String",paramType="path")
	@RequestMapping(value = "/getItemDetail/{numIid}",method = RequestMethod.POST)
	@ResponseBody
	public PlainResult<TbkItemDetailRespVo> getItemDetail(@PathVariable String numIid) throws ApiException{
		if(StringUtils.isEmpty(numIid)){
			return new PlainResult<TbkItemDetailRespVo>().setErrorMessage("商品id不能为空");
		}
		
		PlainResult<TbkItemDetailRespVo> result = tbkService.getItemDetail(numIid);
		return result;
	}
	/**
	 * 通过商品id,转化淘宝客的商品链接
	 * @author shuai.ding
	 * @param itemId
	 * @param model
	 * @return
	 */
	@ApiOperation(value = "通过商品id，查询商品信息",notes = "通过商品id，查询商品信息")
	@ApiImplicitParam(name = "itemId", value = "商品id", required = true, dataType = "String",paramType="path")
	@RequestMapping(value = "/tojump/{itemId}",method = RequestMethod.POST)
	public String jumpToTaobao(@PathVariable String itemId,Model model){
		model.addAttribute("itemId", itemId);
		model.addAttribute("pid", PropertiesUtil.getInstance("tbk.properties").getValue("tbk.pid"));
		return "tbk/jumpToTb";
	}
	
	/**
	 * 获取好券清单列表
	 * @author shuai.ding
	 * @param q              查询词
	 * @param pageNo         当前页
	 * @param pageSize       页面大小
	 * @return
	 * @throws ApiException
	 */
	@ApiOperation(value = "获取好券清单列表",notes = "获取好券清单列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "q", value = "查询词", required = true, dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "pageNo", value = "当前页", required = false, dataType = "Long",paramType="query"),
		@ApiImplicitParam(name = "pageSize", value = "页面大小", required = false, dataType = "Long",paramType="query")
	})	
	@RequestMapping(value = "/getCouponItems",method = RequestMethod.POST)
	@ResponseBody
	public PageResult<TbkCoupon> getCouponItems(String q,Long pageNo,Long pageSize) throws ApiException{
		TbkDgItemCouponGetRequest req = new TbkDgItemCouponGetRequest();
		req.setQ(q);
		req.setPageNo(pageNo);
		req.setPageSize(pageSize);
		PageResult<TbkCoupon> result = tbkService.getCouponItems(req);
		return result;		
	}
	
	/**
	 * 获取淘口令
	 * @author shuai.ding
	 * @param text       口令弹框内容
	 * @param url        口令跳转目标页
	 * @param logo        口令弹框logoURL 可选 如https://uland.taobao.com/
	 * @return
	 * @throws ApiException
	 */
	@ApiIgnore
	@ApiOperation(value = "获取淘口令",notes = "获取淘口令")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "text", value = "口令弹框内容", required = true, dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "url", value = "口令跳转目标页", required = true, dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "logo", value = "口令弹框logoURL 可选 如https://uland.taobao.com/", required = false, dataType = "String",paramType="query")
	})	
	@RequestMapping(value = "/getTPwd",method = RequestMethod.POST)
	@ResponseBody
	public PlainResult<String> getTPwd(String text,String url,String logo) throws ApiException{
		PlainResult<String> result = tbkService.createTpwd(text, url,logo);
		return result;
	}
	
	/**
	 * @throws UnsupportedEncodingException 
	 * 获取淘口令和短链接地址
	 * @author shuai.ding
	 * @param text       口令弹框内容
	 * @param url        口令跳转目标页
	 * @param logo       口令弹框logoURL 可选 如https://uland.taobao.com/
	 * @return
	 * @throws ApiException 
	 * @throws   
	 */
	@ApiOperation(value = "获取淘口令和短链接地址",notes = "获取淘口令和短链接地址")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "text", value = "口令弹框内容", required = true, dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "url", value = "口令跳转目标页", required = true, dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "logo", value = "口令弹框logoURL 可选 如https://uland.taobao.com/", required = false, dataType = "String",paramType="query")
	})	
	@RequestMapping(value = "/getTpwdAndShortLink",method = RequestMethod.POST)
	@ResponseBody
	public PlainResult<TpwdAndShortUrlModel> getTpwdAndShortLink(String text,String url,String logo) throws ApiException, UnsupportedEncodingException{
		PlainResult<TpwdAndShortUrlModel> result = new PlainResult<TpwdAndShortUrlModel>();
		
		TpwdAndShortUrlModel tpwdAndShortUrlModel = new TpwdAndShortUrlModel();
		PlainResult<String> tpwdResult = tbkService.createTpwd(text, url,logo);
		if(!tpwdResult.isSuccess()){
			return result.setErrorMessage("生成淘口令失败");
		}
		tpwdAndShortUrlModel.setCouponLinkTaoToken(tpwdResult.getData());
				
		String finalUrl =  "http://www.tooklili.com:81/taobao?backurl=" +URLEncoder.encode(url,"utf-8");
		PlainResult<String> shortLinkResult = shortLinkService.getShortLinkUrl(finalUrl);
		if(!shortLinkResult.isSuccess()){
			return result.setErrorMessage("生成短链接失败");
		}
		tpwdAndShortUrlModel.setCouponShortLinkUrl(shortLinkResult.getData());	
		result.setData(tpwdAndShortUrlModel);
		return result;
	}
}
