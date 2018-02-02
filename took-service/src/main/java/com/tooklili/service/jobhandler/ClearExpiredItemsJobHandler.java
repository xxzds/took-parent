package com.tooklili.service.jobhandler;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tooklili.service.biz.intf.tooklili.ItemOperService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;


/**
 * 清除过期商品
 * @author shuai.ding
 *
 * @date 2018年2月2日上午10:20:43
 */
@JobHander(value="clearExpiredItemsJobHandler")
@Service
public class ClearExpiredItemsJobHandler extends IJobHandler{
	
	@Resource(name = "itemEsOperServiceImpl")
	private ItemOperService itemOperService;

	@Override
	public ReturnT<String> execute(String... params) throws Exception {
		itemOperService.clearExpiredItems();
		return ReturnT.SUCCESS;
	}

}
