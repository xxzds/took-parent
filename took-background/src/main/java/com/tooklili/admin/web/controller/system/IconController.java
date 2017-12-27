package com.tooklili.admin.web.controller.system;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tooklili.model.admin.SysIcon;
import com.tooklili.service.biz.intf.admin.system.IconService;
import com.tooklili.util.result.PageResult;

/**
 * 图标控制器
 * @author shuai.ding
 * @date 2017年12月26日下午2:23:31
 */
@Controller
@RequestMapping("/system/icon")
public class IconController {
	
	@Resource
	private IconService iconService;
	
	/**
	 * 获取图标列表
	 * @author shuai.ding
	 * @param icon    图标实体
	 * @param page    当前页
	 * @param rows    页面大小
	 * @return
	 */
	@RequestMapping("/getIcons")
	@ResponseBody
	public PageResult<SysIcon> getIcons(SysIcon icon,Integer page,Integer rows){
		return iconService.queryIcons(icon, page, rows);
	}

}
