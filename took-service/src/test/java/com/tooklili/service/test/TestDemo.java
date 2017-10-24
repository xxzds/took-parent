package com.tooklili.service.test;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tooklili.util.DateUtil;

/**
 * 测试
 * @author shuai.ding
 *
 * @date 2017年6月8日下午2:16:37
 */
public class TestDemo {
	
	private static final Logger logger = LoggerFactory.getLogger(TestDemo.class);
	
	@Test
	public void test(){
		Object object =new Object();
		logger.info(object.hashCode()+"");
		
		logger.info("Str".hashCode()+"");
		
		String url="https://uland.taobao.com/coupon/edetail?e=SN2ltNLnRaI8Clx5mXPEKpVaTevdr8KO52byC6A2NtMr6crFk2KoXOvNXSiguVVUdyCKxGCNPHZ25HMRCRMIxdBRtZ5z41kz%2BMUwzxYlSKFauagim%2F7qiSBU9wR5muo5YwSwz7WAymSTj6mSLsKhY7tfy9H%2BT7FNgxZ5fSU6mqLNWdzmw3WZLg%3D%3D&traceId=0b8a57e415087996786104757";
		logger.info(url.split("\\?").toString());
	}
	
	
	@Test
	public void test2(){
		Date date = new Date(1508814901552L);
		logger.info(DateUtil.formatDate(date));
		
		logger.info(new Date().getTime()+"");
		
	}
}
