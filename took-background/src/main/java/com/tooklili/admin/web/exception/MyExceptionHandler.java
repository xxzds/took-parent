package com.tooklili.admin.web.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.tooklili.util.result.BaseResult;
import com.tooklili.util.result.CommonResultCode;

/**
 * 自定义异常处理类
 * @author shuai.ding
 * @date 2017年8月20日下午3:32:59
 */
public class MyExceptionHandler implements HandlerExceptionResolver {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		// 异常信息
		String errorMesage = "sprngmvc catch exception:";
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			errorMesage = "类名：" + handlerMethod.getBean().getClass().getName() + ",方法名："+handlerMethod.getMethod().getName();
		}
		LOGGER.error(errorMesage, ex);

		ModelAndView mv = new ModelAndView();
		HandlerMethod method = (HandlerMethod) handler;
		ResponseBody responseBody = method.getMethodAnnotation(ResponseBody.class);
		if (responseBody != null) {
			BaseResult result = new BaseResult();
			result.setErrorMessage(CommonResultCode.EXCEPTION, ex.getMessage());
			mv.addObject("result", JSON.toJSONString(result));
			mv.setViewName("/error/jsonException");
		} else {
			mv.addObject("exceptionMsg", ex.toString().replaceAll("\n", "<br/>"));
			mv.setViewName("/error/exception");
		}
		return mv;
	}
}