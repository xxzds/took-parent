package com.tooklili.service.jobhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tooklili.service.biz.intf.admin.took.ItemSelectService;
import com.tooklili.util.result.PlainResult;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;

/**
 * 定向选择的商品，过期清除
 * @author ding.shuai
 * @date 2018年6月30日上午10:39:01
 */
@JobHander(value="clearExpiredSelectItemsJobHandler")
@Service
public class ClearExpiredSelectItemsJobHandler extends IJobHandler{
	
	@Autowired
	private ItemSelectService itemSelectService;

	@Override
	public ReturnT<String> execute(String... params) throws Exception {
		PlainResult<Integer> result = itemSelectService.delExpiredItems();
		if(!result.isSuccess()) {
			XxlJobLogger.log(result.getMessage());
			return ReturnT.FAIL;
		}
		XxlJobLogger.log("删除"+result.getData()+"个过期的定向商品");
		return ReturnT.SUCCESS;
	}

}
