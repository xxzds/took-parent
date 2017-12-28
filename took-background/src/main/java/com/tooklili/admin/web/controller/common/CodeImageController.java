package com.tooklili.admin.web.controller.common;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tooklili.service.constant.Constants;
import com.tooklili.util.CodeImageUtils;

/**
 * 验证码生成控制器
 * @author shuai.ding
 * @date 2017年12月28日下午2:53:01
 */
@Controller
public class CodeImageController {
	
	@RequestMapping("/getCodeImage")
	public void getCodeImage(HttpServletResponse response,HttpSession session) throws IOException{
		response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
		response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expire", 0);
		
		OutputStream out = response.getOutputStream();
		String random = CodeImageUtils.getRandcode(out);
		out.flush();
		
		//将产生的随机数，存入session中
		session.setAttribute(Constants.CODE_IMAGE_KEY, random);
	}

}
