package com.tooklili.service.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;

import com.tooklili.service.exception.BusinessException;
import com.tooklili.util.result.BaseResult;
import com.tooklili.util.result.CommonResultCode;
import com.tooklili.util.result.IErrorCode;
import com.tooklili.util.result.ListResult;
import com.tooklili.util.result.PlainResult;

/**
 * Service层异常拦截
 */
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE)
@SuppressWarnings("rawtypes")
public class ExceptionHandleAspect {
    private final Logger logger = LoggerFactory.getLogger(ExceptionHandleAspect.class);

    @Around("within(com.tooklili.service.biz..*)")
    public Object handleException(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
                
        //目标对象
        String className = joinPoint.getTarget().getClass().getName();
        //目标方法
        String methodName = signature.getName();

        Class returnType = signature.getReturnType();
        try {
            result = joinPoint.proceed();
        } catch (Throwable ex) {
            //log error msg
            logError(className,methodName, ex);

            //translate exception to BaseResult
            result = ExceptionTranslator.translate(ex, returnType);
            
            //如果拦截的方法，返回值不是BaseResult自己或父类，仅需抛异常，交给springmvc处理
            if(result==null){
            	throw ex;
            }
        }

        return result;
    }

    private void logError(String className, String methodName, Throwable ex) {
    	String errorMesage="exception throwed by class："+className+", method: "+methodName;
        logger.error(errorMesage, ex);
        if (logger.isDebugEnabled()) {
            logger.debug(errorMesage, ex);
        }
    }

    private static class ExceptionTranslator {
        public static Object translate(Throwable ex, Class returnType) {
            IErrorCode rc = CommonResultCode.DEFAULT_INTERNAL_ERROR;

            if (ex instanceof DataAccessException) {
                rc = CommonResultCode.ERROR_DB;
            } else if (ex instanceof BusinessException) {
                return ResultFactory.createResult(((BusinessException) ex).getCode(), returnType, ex.getMessage());
            }

            return ResultFactory.createResult(rc, returnType);
        }
    }

    private static class ResultFactory {
        static Object createResult(IErrorCode rc, Class returnType, Object... args) {
        	String message = String.format(rc.getMessage(), args);
            return createResult(rc.getCode(), returnType, message);
        }
        
        static Object createResult(int code, Class returnType, String message) {
        	BaseResult result = null;
            if (PlainResult.class.equals(returnType)) {
                result = new PlainResult();
            } else if (ListResult.class.equals(returnType)) {
                result = new ListResult();
            } else if(BaseResult.class.equals(returnType)){
                result = new BaseResult();
            }else{
            	//如果返回值不是返回值不是BaseResult自己或父类，返回null
            	return null;
            }
            result.setErrorMessage(code, message);
            return result;
        }
    }
}
