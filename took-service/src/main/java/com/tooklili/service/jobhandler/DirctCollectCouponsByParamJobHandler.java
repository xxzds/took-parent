package com.tooklili.service.jobhandler;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.tooklili.enums.tooklili.ItemCateEnum;
import com.tooklili.model.taobao.AlimamaItem;
import com.tooklili.model.taobao.AlimamaReqItemModel;
import com.tooklili.service.biz.intf.taobao.AlimamaService;
import com.tooklili.service.biz.intf.tooklili.ItemDBService;
import com.tooklili.util.JsonFormatTool;
import com.tooklili.util.result.PageResult;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;

/**
 * 通过参数，定向查询商品
 * @author shuai.ding
 * @date 2017年10月25日下午2:09:59
 */
@JobHander(value="dirctCollectCouponsByParamJobHandler")
@Service
public class DirctCollectCouponsByParamJobHandler extends IJobHandler{	
	private static final Logger LOGGER = LoggerFactory.getLogger(DirctCollectCouponsByParamJobHandler.class);
	
	@Resource
	private AlimamaService alimamaService;
	
	@Resource
	private ItemDBService itemDBService;

	/**
	 * 参数顺序 :关键字或网址，当前页，页面大小，存储的类别
	 * 存储类别：35、服装  36、母婴  37、化妆品 38、居家 39、鞋包配饰 40、美食 41、文体车品 42 数码家电
	 */
	@Override
	public ReturnT<String> execute(String... params) throws Exception {
		ReturnT<String> result = new ReturnT<String>(ReturnT.SUCCESS_CODE,null);
		if(params==null || params.length != 4){
			LOGGER.info("参数不正确，正确的顺序为:关键字或网址，当前页，页面大小，存储的类别");
			result.setCode(ReturnT.FAIL_CODE);
			result.setMsg("参数不正确，正确的顺序为:关键字或网址，当前页，页面大小，存储的类别");
			return result;
		}
		String keyword = params[0];
		String currentPageStr = params[1];
		String pageSizeStr = params[2];
		String cateStr = params[3];
		
		Integer currentPage=1;
		Integer pageSize=20;
		Integer cate;
		if(StringUtils.isEmpty(keyword)){
			LOGGER.info("关键字或网址不能为空");
			result.setCode(ReturnT.FAIL_CODE);
			result.setMsg("关键字或网址不能为空");
			return result;
		}
		
		if(StringUtils.isNotEmpty(currentPageStr)){
			currentPage = Integer.parseInt(currentPageStr);
		}
		
		if(StringUtils.isNotEmpty(pageSizeStr)){
			pageSize = Integer.parseInt(pageSizeStr);
		}
		
		if(StringUtils.isEmpty(cateStr)){
			LOGGER.info("存储的类别不能为空");
			result.setCode(ReturnT.FAIL_CODE);
			result.setMsg("存储的类别不能为空");
			return result;
		}
		cate = Integer.parseInt(cateStr);
		
		if(ItemCateEnum.valueOf(cate)==null){
			LOGGER.info("存储的类别非法，35、服装  36、母婴  37、化妆品 38、居家 39、鞋包配饰 40、美食 41、文体车品 42 数码家电");
			result.setCode(ReturnT.FAIL_CODE);
			result.setMsg("存储的类别非法，35、服装  36、母婴  37、化妆品 38、居家 39、鞋包配饰 40、美食 41、文体车品 42 数码家电");
			return result;
		}
		
		AlimamaReqItemModel alimamaReqItemModel = new AlimamaReqItemModel();
		alimamaReqItemModel.setQ(keyword);
		alimamaReqItemModel.setToPage(currentPage);
		alimamaReqItemModel.setPerPageSize(pageSize);
		//包含店铺优惠券
		alimamaReqItemModel.setDpyhq(1);
		//天猫
		alimamaReqItemModel.setUserType(1);
		//销量从高到低
		alimamaReqItemModel.setSortType(9);
		LOGGER.info("关键字:{},当前页:{},页面大小:{}",keyword,currentPage,pageSize);
		PageResult<AlimamaItem> pageResult = alimamaService.superSearchItems(alimamaReqItemModel);
		
		List<AlimamaItem> alimamaItems = pageResult.getData();
		LOGGER.info("超级接口查询结果:{}",JsonFormatTool.formatJson(JSON.toJSONString(alimamaItems)));
			
		if(alimamaItems==null || alimamaItems.size()==0){
			LOGGER.info("没有查询到商品");
			result.setCode(ReturnT.SUCCESS_CODE);
			result.setMsg("没有查询到商品");
			return result;
		}
		
		for(AlimamaItem alimamaItem:alimamaItems){
			itemDBService.insertOrUpdate(alimamaItem, cate);
			
			//为防止频繁调用阿里妈妈推广接口，被禁，此处休眠10s
			Thread.sleep(10000);
		}
		return result;
	}
}
