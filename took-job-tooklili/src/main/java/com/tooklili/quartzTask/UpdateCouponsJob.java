package com.tooklili.quartzTask;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.tooklili.http.HttpCallService;

/**
 * 更新优惠券
 * @author shuai.ding
 * @date 2017年7月20日下午3:12:34
 */
@Service
public class UpdateCouponsJob {
	
	@Resource
	private HttpCallService httpCallService;

	
	@Scheduled(cron = "0 0/5 * * * ?")
	public void execute(){		
		httpCallService.httpGet("http://www.tooklili.com?m=huameiquancaiji");				
	}

}
