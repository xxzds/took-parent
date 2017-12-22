package com.tooklili.admin.web.interceptor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限注解
 * @author shuai.ding
 * @date 2017年12月21日下午2:22:30
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresPermissions {

	/**
     * The permission string which will be passed to {@link org.apache.shiro.subject.Subject#isPermitted(String)}
     * to determine if the user is allowed to invoke the code protected by this annotation.
     */
	String[] value();
	
	/**
     * The logical operation for the permission checks in case multiple roles are specified. AND is the default
     */
	Logical logical() default Logical.AND; 
}
