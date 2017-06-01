package com.tooklili.service.test.dataoke;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * unicode码转换测试
 * @author shuai.ding
 *
 * @date 2017年5月31日下午5:41:15
 */
public class UnicodeTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UnicodeTest.class);

	/**
	 * unicode编码测试
	 * @author shuai.ding
	 */
	@Test
	public void unicodeEncodeTest(){
		String s="aA你好$ngn1";		
		String unicode = unicodeEncode(s);		
		LOGGER.info(unicode);		
	}
	
	
	/**
	 * unicode解码测试
	 * @author shuai.ding
	 */
	@Test
	public void unicodeDecodeTest(){
		//在java中"\"是转义字符，所以防止转义，必须写成下面的形式
		String s = "\\u0061\u0041\\u4f60\\u597d\\u0024\\u006e\\u0067\\u006e\\u0031";
		System.out.println(s);
		String str = unicodeDecode(s);
		LOGGER.info(str);
	}
	
	
	
	/**
	 * 将字符串转成unicode码
	 * @author shuai.ding
	 * @param s
	 * @return
	 */
	private String unicodeEncode(String s){
		char[] bytes = s.toCharArray();
		StringBuffer unicode =new StringBuffer();
		for(int i=0;i<bytes.length;i++){
			String hexString = Integer.toHexString(bytes[i]);
			
			unicode.append("\\u");
			
			//不够四位进行补0操作
			if(hexString.length()<4){
				unicode.append("0000".substring(hexString.length(),4));
			}			
			unicode.append(hexString);
		}
		return unicode.toString();
	}
	
	
	/**
	 * 将unicode码转成字符串
	 * @author shuai.ding
	 * @param s
	 * @return
	 */
	private String unicodeDecode(String s){
		StringBuffer buffer = new StringBuffer();
		
		String[] strs = s.split("\\\\u");
		for(String str:strs){
			if(StringUtils.isNotEmpty(str)){
				buffer.append((char)Integer.parseInt(str, 16));
			}
		}		
		return buffer.toString();
	}
}
