package com.tooklili.admin.web.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 菜单控制器
 * @author shuai.ding
 *
 * @date 2017年12月8日下午5:10:56
 */
@Controller
@RequestMapping("system/menu")
public class MenuController {
	
	/**
	 * 主页
	 * @author shuai.ding
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String main(){
		return "system/menu";
	}

}
