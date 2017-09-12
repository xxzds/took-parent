package com.tooklili.dao.extension;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * 多数据源自由切换AOP实现类
 * @author shuai.ding
 * @date 2017年9月11日上午10:29:54
 * 
 * 此处说明一下，使用低版本的spring，如4.1.4,报Cannot subclass final class class com.sun.proxy.$Proxy17，如果出现这种问题
 * 1.检查spring配置,开启代理的配置，是否是这样：<aop:aspectj-autoproxy proxy-target-class="false"/>
 * 2.检查spring版本，使用高版本
 */
@Aspect
public class MultipleDataSourceAspectAdvice {
	
	@Before("execution(* com.tooklili.dao.intf.tooklili.*.*(..))")
	public void handleSetDataSource(JoinPoint joinPoint){
		MultipleDataSource.setDataSourceKey("tookliliDataSource");
	}
}
