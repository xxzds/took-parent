package com.tooklili.admin.web.controller.showcase;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 上传管理
 * @author ding.shuai
 * @date 2018年3月9日下午4:20:56
 */
@Controller
@RequestMapping("/showcase/upload")
public class UploadController {
	
	/**
	 * 主页
	 * @author shuai.ding
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String main(){
		return "showcase/upload";
	}

}
