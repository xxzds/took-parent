package com.tooklili.admin.web.controller.took;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tooklili.admin.web.interceptor.annotation.RequiresPermissions;
import com.tooklili.model.taobao.AlimamaItem;
import com.tooklili.model.taobao.AlimamaReqItemModel;
import com.tooklili.service.biz.intf.taobao.AlimamaService;
import com.tooklili.service.biz.intf.tooklili.ItemOperService;
import com.tooklili.util.result.PageResult;
import com.tooklili.util.result.PlainResult;

/**
 * 超级搜管理
 * @author ding.shuai
 * @date 2018年2月16日下午3:36:54
 */
@Controller
@RequestMapping("took/superSearch")
public class SuperSearchController {
	
	@Resource
	private AlimamaService alimamaService;
	
	@Resource(name = "itemEsOperServiceImpl")
	private ItemOperService itemOperService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String main(){
		return "took/super_search";
	} 
	
	/**
	 * 超级搜商品查询
	 * @param alimamaReqItemModel  参数实体
	 * @param page   当前页
	 * @param rows   页面大小
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/getAlimamaItems",method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("item:superSearch:view")
	public PageResult<AlimamaItem> getAlimamaItems(AlimamaReqItemModel alimamaReqItemModel,Integer page,Integer rows) throws UnsupportedEncodingException{
		if(alimamaReqItemModel == null){
			alimamaReqItemModel = new AlimamaReqItemModel();
		}
		alimamaReqItemModel.setToPage(page);
		alimamaReqItemModel.setPerPageSize(rows);
		return alimamaService.superSearchItems(alimamaReqItemModel);
	}
	
	/**
	 * 采集商品
	 * @param alimamaItems  商品集合
	 * @param cateId        商品分类id
	 * @return
	 * @throws ParseException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/collectItems",method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("item:superSearch:collect")
	public PlainResult<String> collectItems(@RequestBody List<AlimamaItem> alimamaItems,Integer cateId) throws UnsupportedEncodingException, ParseException{
		return itemOperService.insertOrUpdate(alimamaItems, cateId);
	}

}
