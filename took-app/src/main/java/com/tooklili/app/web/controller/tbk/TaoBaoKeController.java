package com.tooklili.app.web.controller.tbk;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobao.api.ApiException;
import com.taobao.api.request.TbkDgItemCouponGetRequest;
import com.taobao.api.response.TbkDgItemCouponGetResponse.TbkCoupon;
import com.tooklili.service.biz.api.tbk.TbkService;
import com.tooklili.util.PropertiesUtil;
import com.tooklili.util.result.PageResult;
import com.tooklili.util.result.PlainResult;
import com.tooklili.vo.tbk.TbkItemDetailRespVo;
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
	private TbkService tbkService;
		
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
	
	
	/**
	 * 通过商品id，查询商品信息
	 * @author shuai.ding
	 * @param numIid
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping("/getItemDetail/{numIid}")
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
	@RequestMapping("/tojump/{itemId}")
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
	@RequestMapping("/getCouponItems")
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
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping("/getTPwd")
	@ResponseBody
	public PlainResult<String> getTPwd(String text,String url) throws ApiException{
		PlainResult<String> result = tbkService.createTpwd(text, url);
		return result;
	}
}
