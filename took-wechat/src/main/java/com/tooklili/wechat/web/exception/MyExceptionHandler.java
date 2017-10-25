package com.tooklili.wechat.web.exception;

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
 * 此方法只对在执行controller中的方法时，出现异常，才会执行，
 * 如对数据绑定时的异常，不会捕获,报400，可由web.xml配置400状态跳转的页面。
 * 全局异常处理，可参照这篇文章
 * http://blog.csdn.net/shaokai132333/article/details/52712846
 * @author shuai.ding
 *
 * @date 2017年2月13日下午2:38:39
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
			errorMesage = "类名：" + handlerMethod.getBean().getClass().getName() + ",方法名："
					+ handlerMethod.getMethod().getName();
		}
		LOGGER.error(errorMesage, ex);

		ModelAndView mv = new ModelAndView();
		HandlerMethod method = (HandlerMethod) handler;
		ResponseBody responseBody = method.getMethodAnnotation(ResponseBody.class);
		if (responseBody != null) {
			BaseResult result = new BaseResult();
			result.setErrorMessage(CommonResultCode.EXCEPTION, ex.getMessage());
			mv.addObject("result", JSON.toJSONString(result));
			mv.setViewName("/common/jsonException");
		} else {
			mv.addObject("exceptionMsg", ex.toString().replaceAll("\n", "<br/>"));
			mv.setViewName("/common/exception");
		}
		return mv;
	}
}