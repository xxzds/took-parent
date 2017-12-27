package com.tooklili.service.constant;

/**
 * 接口中的属性默认是static final
 * 常量 
 * @author shuai.ding
 *
 * @date 2017年8月20日下午4:29:12
 */
public interface Constants {
	/**
	 * 当前的登录用户
	 */
	public static final String CURRENT_USER = "user";
	
	/**
	 * 上下文路径，存入项目名称的键
	 */
	public static final String CONTEXT_PATH = "ctx";
    
    /**
     * 编码格式
     */
    String UTF8 = "UTF-8";
    
    /**
     * 角色对应的菜单存入redis的键的前缀
     */
    String MENU_REDIS_PREFIX = "menu-roleId-";
    
    /**
     * 角色对应的权限存入redis的键的前缀
     */
    String PERMISSION_REDIS_PREFIX = "permission-roleId-";
	
}
