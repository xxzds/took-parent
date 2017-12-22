package com.tooklili.admin.web.interceptor.annotation;

import java.lang.annotation.ElementType;  
import java.lang.annotation.Retention;  
import java.lang.annotation.RetentionPolicy;  
import java.lang.annotation.Target;

/**
 * 一个用户 相同url 同时提交 相同数据 验证 
 * @author shuai.ding
 *
 * @date 2017年11月29日下午4:06:44
 */
@Target(ElementType.METHOD)  
@Retention(RetentionPolicy.RUNTIME) 
public @interface SameUrlData {

}
