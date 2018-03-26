package com.tooklili.admin.web.controller.common;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tooklili.service.biz.intf.common.FileUploadService;
import com.tooklili.util.PropertiesUtil;
import com.tooklili.util.result.PlainResult;

/**
 * 异步上传控制器
 * @author ding.shuai
 * @date 2018年3月1日下午4:40:22
 */
@Controller
public class AjaxUploadController {
	private String[] IMAGE_EXTENSION = {
            "bmp", "gif", "jpg", "jpeg", "png"
    };
	
	@Resource
	private FileUploadService fileUploadService;
	
	/**
	 * 上传单个文件
	 * @param file
	 * @return
	 */
	@RequestMapping(value="/ajax/uploadImage" , method = RequestMethod.POST)
	@ResponseBody
	public PlainResult<String> uploadImage(@RequestParam(value="file") MultipartFile file){
		PlainResult<String> result = new PlainResult<String>();
		PlainResult<String> uploadResult = fileUploadService.uploadFile(file,IMAGE_EXTENSION);
		if (!uploadResult.isSuccess()) {
			return uploadResult;
		}
		//设置图片url路径
		String prefixDir = PropertiesUtil.getInstance("config.properties").getValue("sftp.httpBaseUrl");
		String suffixDir = uploadResult.getData();
		result.setData(prefixDir+suffixDir);
		return result;
	}

}
