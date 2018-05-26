package com.tooklili.service.jobhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tooklili.service.biz.intf.admin.took.ItemNineService;
import com.tooklili.service.biz.intf.admin.took.ItemTwentyService;
import com.tooklili.util.result.PlainResult;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;

/**
 * 9块9 20元商品 过期清除
 * @author ding.shuai
 * @date 2018年5月26日上午11:59:26
 */
@JobHander(value="clearExpiredNineAndTwentyItemsJobHandler")
@Service
public class ClearExpiredNineAndTwentyItemsJobHandler extends IJobHandler{
	
	@Autowired
	private ItemNineService itemNineService;
	
	@Autowired
	private ItemTwentyService itemTwentyService;
	
	
	@Override
	public ReturnT<String> execute(String... params) throws Exception {
		PlainResult<Integer> nineResult = itemNineService.delExpiredItems();
		if(!nineResult.isSuccess()) {
			XxlJobLogger.log(nineResult.getMessage());
			return ReturnT.FAIL;
		}
		XxlJobLogger.log("删除"+nineResult.getData()+"个过期的9块9商品");
		
		PlainResult<Integer> twentyResult = itemTwentyService.delExpiredItems();
		if(!twentyResult.isSuccess()) {
			XxlJobLogger.log(twentyResult.getMessage());
			return ReturnT.FAIL;
		}
		XxlJobLogger.log("删除"+twentyResult.getData()+"个过期的20元商品");		
		return ReturnT.SUCCESS;
	}
}
