package com.tooklili.admin.web.controller.monitor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tooklili.admin.web.interceptor.annotation.RequiresPermissions;

/**
 * jvm监控控制器
 * @author shuai.ding
 *
 * @date 2018年1月2日上午10:40:14
 */
@Controller
@RequestMapping("/monitor/jvm")
@RequiresPermissions("monitor:jvm:*")
public class JvmMonitorController {
	
	@RequestMapping("")
	public String main(){
		return "monitor/jvm";
	}
}
