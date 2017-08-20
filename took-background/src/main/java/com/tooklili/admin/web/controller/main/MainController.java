package com.tooklili.admin.web.controller.main;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 主页控制器
 * @author shuai.ding
 * @date 2017年8月20日下午4:01:15
 */
@Controller
public class MainController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

	@RequestMapping("/")
	public String main() {
		return "main/index";
	}

	
	@RequestMapping("/getMenu")
	@ResponseBody
	public String getMenu() throws IOException{
		
		String pathStr = MainController.class.getClassLoader().getResource("json/menu.json").getFile();
		String content=FileUtils.readFileToString(new File(pathStr), "utf-8");
		LOGGER.info(content.split("\r\n").length+"");
		
		return content;

	}
}
