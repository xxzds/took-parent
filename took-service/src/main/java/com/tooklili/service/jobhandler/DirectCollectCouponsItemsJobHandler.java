package com.tooklili.service.jobhandler;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tooklili.enums.common.ChannelEnum;
import com.tooklili.model.taobao.AlimamaItem;
import com.tooklili.model.taobao.DirectionalAlimamaReqItemModel;
import com.tooklili.service.biz.intf.admin.took.ItemNineService;
import com.tooklili.service.biz.intf.admin.took.ItemTwentyService;
import com.tooklili.service.biz.intf.taobao.AlimamaService;
import com.tooklili.service.biz.intf.tooklili.ItemOperService;
import com.tooklili.service.util.RandomGetCateUtil;
import com.tooklili.service.util.RandomGetCateUtil.Cate;
import com.tooklili.util.result.PageResult;
import com.tooklili.util.result.PlainResult;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;

/**
 * 定向采集优惠券商品
 * 
 * @author ding.shuai
 * @date 2018年5月13日下午6:28:54
 */
@JobHander(value = "directCollectCouponsItemsJobHandler")
@Service
public class DirectCollectCouponsItemsJobHandler extends IJobHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(DirectCollectCouponsItemsJobHandler.class);
	private static final Integer PAGESIZE = 20;

	@Resource
	private AlimamaService alimamaService;

	@Resource(name = "itemEsOperServiceImpl")
	private ItemOperService itemOperService;
	
	@Resource
	private ItemNineService itemNineService;
	
	@Resource
	private ItemTwentyService itemTwentyService;

	@Override
	public ReturnT<String> execute(String... arg0) throws Exception {
		Cate cate = RandomGetCateUtil.getCate();
		LOGGER.info("选择的分类：{}", cate);
		XxlJobLogger.log("选择的分类：" + cate);

		DirectionalAlimamaReqItemModel directionalAlimamaReqItemModel = new DirectionalAlimamaReqItemModel();
		// 分类
		directionalAlimamaReqItemModel.setCatIds(Integer.parseInt(cate.getCateId()));
		directionalAlimamaReqItemModel.setLevel(1);
		//商品优惠券
		if(cate.getChannel() != ChannelEnum.NINE && cate.getChannel() != ChannelEnum.TWENTY) {
			directionalAlimamaReqItemModel.setDpyhq(1);
		}
		
		// 销量由高到低
		directionalAlimamaReqItemModel.setSortType(9);
		// 当前页
		directionalAlimamaReqItemModel.setToPage(cate.getCurrentPage());
		if(cate.getChannel() == ChannelEnum.NINE || cate.getChannel() == ChannelEnum.TWENTY) {
			directionalAlimamaReqItemModel.setPerPageSize(40);
		}else {
			directionalAlimamaReqItemModel.setPerPageSize(PAGESIZE);
		}
		
		directionalAlimamaReqItemModel.setChannel(cate.getChannel());
		PageResult<AlimamaItem> result = alimamaService.directionalSuperSearchItems(directionalAlimamaReqItemModel);

		if (!result.isSuccess()) {
			XxlJobLogger.log(result.getMessage());
			return ReturnT.FAIL;
		}

		// 插入数据库
		switch(cate.getChannel()) {
		  case NINE:
			  //此处去cateId，为阿里妈妈中的分类
			  PlainResult<String> resultNine = itemNineService.insertOrUpdate(result.getData(),Integer.parseInt(cate.getCateId()));			  
				 if(!resultNine.isSuccess()) {
					 XxlJobLogger.log(resultNine.getMessage());
					 return ReturnT.FAIL;
				 }else {
					 XxlJobLogger.log(resultNine.getData());
				 }
			  break;
		  case TWENTY:
			  PlainResult<String> resultTwenty = itemTwentyService.insertOrUpdate(result.getData(),Integer.parseInt(cate.getCateId()));			
				 if(!resultTwenty.isSuccess()) {
					  XxlJobLogger.log(resultTwenty.getMessage());
					 return ReturnT.FAIL;
				 }else {
					  XxlJobLogger.log(resultTwenty.getData());
				 }
			  break;
		   default:
			 PlainResult<String> resultDefalut = itemOperService.insertOrUpdate(result.getData(), Integer.parseInt(cate.getTargetCateId()));			 
			 if(!resultDefalut.isSuccess()) {
				 XxlJobLogger.log(resultDefalut.getMessage());
				 return ReturnT.FAIL;
			 }else {
				 XxlJobLogger.log(resultDefalut.getData());
			 }
			 break;
		}
		return ReturnT.SUCCESS;
	}

}
