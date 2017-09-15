package com.tooklili.task;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tooklili.http.HttpCallService;
import com.tooklili.util.PropertiesUtil;

/**
 * 更新优惠券
 * @author shuai.ding
 * @date 2017年7月20日下午3:12:34
 */
@Service
public class UpdateCouponsJob extends BaseJob{
	
	@Resource
	private HttpCallService httpCallService;

	public UpdateCouponsJob() {
		this.setCorn(PropertiesUtil.getInstance("system.properties").getValue("update_coupons_cron"));
	}
	
	@Override
	public void execute(){		
		httpCallService.httpGet("http://www.tooklili.com?m=huameiquancaiji");				
	}

}
