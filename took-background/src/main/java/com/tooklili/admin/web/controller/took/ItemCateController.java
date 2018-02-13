package com.tooklili.admin.web.controller.took;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tooklili.admin.web.interceptor.annotation.RequiresPermissions;
import com.tooklili.model.admin.TookItemCate;
import com.tooklili.service.biz.intf.admin.took.ItemCateService;
import com.tooklili.util.result.BaseResult;
import com.tooklili.util.result.PageResult;

/**
 * 商品分类控制器
 * @author ding.shuai
 * @date 2018年2月9日下午4:08:06
 */
@Controller
@RequestMapping("took/itemcate")
public class ItemCateController {
	
	@Resource
	private ItemCateService itemCateService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String main(){
		return "took/item_cate";
	} 
	
	/**
	 * 商品分类列表
	 * @param tookItemCate   商品分类实体
	 * @param page           当前页
	 * @param rows           页面大小
	 * @return
	 */
	@RequestMapping("/getItemCates")
	@ResponseBody
	@RequiresPermissions("item:cate:view")
	public PageResult<TookItemCate> getItemCates(TookItemCate tookItemCate,Integer page,Integer rows){
		return itemCateService.getItemCates(tookItemCate, page, rows);
	}
	
	
	/**
	 * 添加商品分类
	 * @param tookItemCate
	 * @return
	 */
	@RequestMapping("/addItemCate")
	@ResponseBody
	@RequiresPermissions("item:cate:add")
	public BaseResult addItemCate(TookItemCate tookItemCate){
		return itemCateService.addItemCate(tookItemCate);
	}
	
	/**
	 * 修改商品分类
	 * @param tookItemCate
	 * @return
	 */
	@RequestMapping("/modifyItemCate")
	@ResponseBody
	@RequiresPermissions("item:cate:modify")
	public BaseResult modifyItemCate(TookItemCate tookItemCate){
		return itemCateService.modifyItemCate(tookItemCate);
	}
	
	/**
	 * 删除商品分类
	 * @param itemCateId
	 * @return
	 */
	@RequestMapping("/delItemCate/{itemCateId}")
	@ResponseBody
	@RequiresPermissions("item:cate:delete")
	public BaseResult delItemCate(@PathVariable Long itemCateId){
		return itemCateService.delItemCate(itemCateId);
	}	
}
