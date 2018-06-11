package com.tooklili.util;

import java.util.Random;

/**
 * 随机数生成工具
 * @author shuai.ding
 * @date: 2018年6月11日 下午7:31:59
 */
public class RandomUtils {

	/**
	 * 生成随机数（包含数字、字母）
	 * @param n  长度
	 * @return
	 */
	public static String randomCharAndNum(int n) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < n; i++) {
			String str = random.nextInt(2) % 2 == 0 ? "num" : "char";
			if ("char".equalsIgnoreCase(str)) { // 产生字母
				int nextInt = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (nextInt + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(str)) { // 产生数字
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}
	
	
	/**
	 * 生成随机数（包含字母）
	 * @param length
	 * @return
	 */
	public static String randomChar(int length) {
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for ( int i = 0; i < length; i++ )
        {
            int number = random.nextInt( base.length() );
            sb.append( base.charAt( number ) );
        }
        return sb.toString();
	}

}
