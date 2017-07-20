package com.tooklili.jobhandler;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tooklili.http.HttpCallService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;

/**
 * 更新优惠券
 * @author shuai.ding
 * @date 2017年7月20日下午3:12:34
 */
@JobHander(value="updateCouponsJobHandler")
@Service
public class UpdateCouponsJobHandler extends IJobHandler{
	
	@Resource
	private HttpCallService httpCallService;

	@Override
	public ReturnT<String> execute(String... params) throws Exception {		
		httpCallService.httpGet("http://www.tooklili.com?m=huameiquancaiji");				
		return ReturnT.SUCCESS;
	}

}
