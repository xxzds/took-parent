package com.tooklili.admin.web.controller.took;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tooklili.admin.web.controller.BaseController;
import com.tooklili.admin.web.interceptor.annotation.RequiresPermissions;
import com.tooklili.enums.admin.IsAvailableEnum;
import com.tooklili.model.admin.TookItemSearchKeyword;
import com.tooklili.service.biz.intf.admin.took.ItemSearchKeywordService;
import com.tooklili.util.result.BaseResult;
import com.tooklili.util.result.PageResult;

/**
 * 商品搜索关键字控制器
 * @author ding.shuai
 * @date 2018年2月9日下午11:12:30
 */
@Controller
@RequestMapping("took/itemSearchKeyword")
public class ItemSearchKeywordController extends BaseController{
	
	@Autowired
	private ItemSearchKeywordService itemSearchKeywordService;
	
	/**
	 * 设置通用数据
	 */
	@Override
	protected void setCommonData(Model model) {
		//是否可用
		model.addAttribute("isAvailables", IsAvailableEnum.values());
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String main(Model model){
		this.setCommonData(model);
		return "took/item_search_keyword";
	}
	
	/**
	 * 分页查询列表
	 * @param tookItemSearchKeyword
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/getItemSearchKeywords")
	@ResponseBody
	@RequiresPermissions("item:search:view")
	public PageResult<TookItemSearchKeyword> getItemSearchKeywords(TookItemSearchKeyword tookItemSearchKeyword,Integer page,Integer rows){
		return itemSearchKeywordService.queryItemSearchKeywords(tookItemSearchKeyword, page, rows);
	}
	
	/**
	 * 增加商品搜索关键字
	 * @param tookItemSearchKeyword
	 * @return
	 */
	@RequestMapping("/addItemSearchKeyword")
	@ResponseBody
	@RequiresPermissions("item:search:add")
	public BaseResult addItemSearchKeyword(TookItemSearchKeyword tookItemSearchKeyword){
		return itemSearchKeywordService.addItemSearchKeyword(tookItemSearchKeyword);
	}
	
	/**
	 * 修改商品搜索关键字
	 * @param tookItemSearchKeyword
	 * @return
	 */
	@RequestMapping("/modifyItemSearchKeyword")
	@ResponseBody
	@RequiresPermissions("item:search:modify")
	public BaseResult modifyItemSearchKeyword(TookItemSearchKeyword tookItemSearchKeyword){
		return itemSearchKeywordService.modifyItemSearchKeyword(tookItemSearchKeyword);
	}
	
	@RequestMapping("/delItemSearchKeyword/{itemSearchKeywordId}")
	@ResponseBody
	@RequiresPermissions("item:search:delete")
	public BaseResult delItemSearchKeyword(@PathVariable Long itemSearchKeywordId){
		return itemSearchKeywordService.delItemSearchKeyword(itemSearchKeywordId);
	}
	
	@RequestMapping("/cacheSyn")
	@ResponseBody
	@RequiresPermissions("item:search:cacheSyn")
	public BaseResult cacheSyn(){
		return itemSearchKeywordService.synToRedisAboutKewyword();
	}
}
