package com.tooklili.service.dataoke;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * unicode码转换测试
 * 
 * @author shuai.ding
 *
 * @date 2017年5月31日下午5:41:15
 */
public class UnicodeTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(UnicodeTest.class);

	/**
	 * unicode编码测试
	 * 
	 * @author shuai.ding
	 */
	@Test
	public void unicodeEncodeTest() {
		String s = "你好";
		String unicode = string2Unicode(s);
		LOGGER.info("中文字符串:{}",s);
		LOGGER.info("编码后的字符串:{}",unicode);
	}

	/**
	 * unicode解码测试
	 * 
	 * @author shuai.ding
	 */
	@Test
	public void unicodeDecodeTest() {
		// 在java中"\"是转义字符，所以防止转义，必须写成下面的形式
		// String s = "\\u0061\\u0041\\u4f60\\u597d\\u0024\\u006e\\u0067\\u006e\\u0031";
		String s = "\\u5168\\u7ad9\\u63a5\\u53e3\\u5df2\\u5347\\u7ea7\\u4e3a\\u5206\\u9875\\u6a21\\u5f0f\\uff0c\\u6bcf\\u987550\\u6761\\u6570\\u636e\\uff0c\\u5206\\u9875\\u53c2\\u6570\\uff1a&page";
		LOGGER.info("unicode码:{}",s);
		String str = unicode2String(s);
		LOGGER.info("转码后的字符串:{}",str);
	}
	
	@Test
	public void unicodeToOutTest(){
		String s="\u4f60\u597d";
		LOGGER.info("unicode字符串在java中输出会自动转化:{}",s);
	}

	
	/**
	 * 将字符串转化成unicode码
	 * @author shuai.ding
	 * @param string
	 * @return
	 */
	private String string2Unicode(String string) {

		if (StringUtils.isBlank(string)) {
			return null;
		}

		char[] bytes = string.toCharArray();
		StringBuffer unicode = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			char c = bytes[i];

			// 标准ASCII范围内的字符，直接输出
			if (c >= 0 && c <= 127) {
				unicode.append(c);
				continue;
			}
			String hexString = Integer.toHexString(bytes[i]);

			unicode.append("\\u");

			// 不够四位进行补0操作
			if (hexString.length() < 4) {
				unicode.append("0000".substring(hexString.length(), 4));
			}
			unicode.append(hexString);
		}
		return unicode.toString();
	}

	
	/**
	 * 将unicode码转化成字符串
	 * @author shuai.ding
	 * @param unicode
	 * @return
	 */
	private String unicode2String(String unicode) {
		if (StringUtils.isBlank(unicode)) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		int i = -1;
		int pos = 0;

		while ((i = unicode.indexOf("\\u", pos)) != -1) {
			sb.append(unicode.substring(pos, i));
			if (i + 5 < unicode.length()) {
				pos = i + 6;
				sb.append((char) Integer.parseInt(unicode.substring(i + 2, i + 6), 16));
			}
		}
		//如果pos位置后，有非中文字符，直接添加
		sb.append(unicode.substring(pos));

		return sb.toString();
	}
}
