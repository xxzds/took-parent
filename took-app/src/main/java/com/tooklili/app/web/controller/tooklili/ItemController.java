package com.tooklili.app.web.controller.tooklili;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tooklili.enums.admin.IsAvailableEnum;
import com.tooklili.model.admin.TookItemCate;
import com.tooklili.model.tooklili.Item;
import com.tooklili.service.biz.intf.admin.took.ItemCateService;
import com.tooklili.service.biz.intf.tooklili.ItemService;
import com.tooklili.util.result.ListResult;
import com.tooklili.util.result.PageResult;
import com.tooklili.util.result.PlainResult;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * tooklili控制器
 * @author ding.shuai
 * @date 2017年9月16日上午11:36:21
 */
@Controller
public class ItemController {	
	
	@Resource(name="itemEsServiceImpl")
	private ItemService itemService;
	
	@Resource
	private ItemCateService itemCateService;
	
	@ApiOperation(value = "从本地数据库中查询商品列表",notes = "从本地数据库中查询商品列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "cateId", value = "分类id", required = false, dataType = "Integer",paramType="query"),
		@ApiImplicitParam(name = "currentPage", value = "当前页", required = false, dataType = "Long",paramType="query"),
		@ApiImplicitParam(name = "pageSize", value = "页面大小", required = false, dataType = "Long",paramType="query")
	})	
	@RequestMapping(value = "/couponItems",method = RequestMethod.POST)
	@ResponseBody
	public PageResult<Item> queryCouponItems(Integer cateId,Long currentPage,Long pageSize){
		return itemService.queryCouponItemsByCateId(cateId, currentPage, pageSize);
	}
	
	@ApiOperation(value = "通过主键id获取商品信息",notes = "通过主键id获取商品信息")
	@ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long",paramType="path")
	@RequestMapping(value = "/getitem/{id}",method = RequestMethod.POST)
	@ResponseBody
	public PlainResult<Item> queryItemById(@PathVariable Long id){
		return itemService.queryItemById(id);
	}
	
	/**
	 * 通过cateId随机获取size个商品
	 * @author shuai.ding
	 * @param cateId    商品类别
	 * @param size      获取商品的个数
	 * @return
	 */
	@ApiOperation(value = "通过cateId随机获取size个商品",notes = "通过cateId随机获取size个商品")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "cateId", value = "商品类别", required = false, dataType = "Integer",paramType="query"),
		@ApiImplicitParam(name = "size", value = "获取商品的个数", required = false, dataType = "Integer",paramType="query")
	})	
	@RequestMapping(value = "/getRandomItemByCateId",method = RequestMethod.POST)
	@ResponseBody
	public ListResult<Item> getRandomItemByCateId(Integer cateId, Integer size){
		return itemService.getRandomItemByCateId(cateId, size);
	}
	
	/**
	 * 通过关键词查询商品列表
	 * @author shuai.ding
	 * @param keyWords      关键字
	 * @param currentPage   当前页
	 * @param pageSize      页面大小
	 * @return
	 */
	@ApiOperation(value = "通过关键词查询商品列表",notes = "通过关键词查询商品列表")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "keyWords", value = "关键字", required = true, dataType = "String",paramType="query"),
		@ApiImplicitParam(name = "currentPage", value = "当前页", required = false, dataType = "Long",paramType="query"),
		@ApiImplicitParam(name = "pageSize", value = "页面大小", required = false, dataType = "Long",paramType="query")
	})
	@RequestMapping(value = "/queryCouponItemsByKeyWords",method = RequestMethod.POST)
	@ResponseBody
	public PageResult<Item> queryCouponItemsByKeyWords(String keyWords,Long currentPage,Long pageSize){
		return itemService.queryCouponItemsByKeyWords(keyWords, currentPage, pageSize);
	}
	
	/**
	 * 查询商品分类列表
	 * @return
	 */
	@ApiOperation(value = "查询商品分类列表",notes = "查询商品分类列表")
	@RequestMapping(value = "/queryItemCates",method = RequestMethod.POST)
	@ResponseBody
	public ListResult<TookItemCate> queryItemCates(){
		TookItemCate tookItemCate = new TookItemCate();
		tookItemCate.setIsAvailable(IsAvailableEnum.YES_AVAILIABLE.getCode());
		return itemCateService.getItemCates(tookItemCate);
	}

}
