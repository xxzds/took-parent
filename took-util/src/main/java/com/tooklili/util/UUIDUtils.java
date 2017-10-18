package com.tooklili.util;


import java.util.UUID;

/**
 * 
 * @author ding.shuai
 * @date 2016年7月21日下午1:33:35
 */
public class UUIDUtils {
	/**
	 * 36位
	 * @return
	 */
    public static String generateUuid36() {
    	//随机产生一个[0,10)的数字
    	int random=(int)(Math.random()*10);
        return UUID.randomUUID().toString().replaceAll("-", String.valueOf(random));
    }
    
    /**
     * 32位
     * @return
     */
    public static String  generateUuid32(){
    	return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
