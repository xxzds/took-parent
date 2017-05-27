package com.tooklili.app.web.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.tooklili.util.result.BaseResult;
import com.tooklili.util.result.CommonResultCode;
/**
 * 自定义异常处理类
 * @author shuai.ding
 *
 * @date 2017年2月13日下午2:38:39
 */
public class MyExceptionHandler implements HandlerExceptionResolver {
	
	private static final Logger log = LoggerFactory.getLogger(MyExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {		
		BaseResult result = new BaseResult();
		result.setErrorMessage(CommonResultCode.EXCEPTION,ex.getMessage());
		this.printWriter(response, JSON.toJSONString(result));
		
		//异常信息
		String errorMesage="";
		if(handler instanceof HandlerMethod){
			HandlerMethod handlerMethod =(HandlerMethod)handler;
			errorMesage="类名："+handlerMethod.getBean().getClass().getName()+",方法名："+handlerMethod.getMethod().getName();
		}		
		log.error(errorMesage, ex);
		return null;
	}
	
	/**
	 * 将content输出
	 * @param response
	 * @param content
	 * @throws IOException
	 */
	private void printWriter(HttpServletResponse response,String content){
		try {
//			response.setContentType("text/html;charset=UTF-8"); 
			response.setContentType("application/json;charset=UTF-8");			
			PrintWriter out = response.getWriter();
			out.println(content);
			out.flush();
		} catch (IOException e) {
			log.error("异常：",e);
		}
		
	}

}