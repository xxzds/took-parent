package com.tooklili.app.web.controller.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 测试控制器
 * @author shuai.ding
 * @date 2017年5月26日下午5:45:25
 */
@Controller
@RequestMapping("/test")
public class TestController {

	@RequestMapping("/test")
	@ResponseBody
	public String test(){
		return "test";
	}
	
	@RequestMapping("toView")
	public String toView(){
		return "view";
	}
}
