package com.tooklili.service.test;

import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tooklili.util.DateUtil;
import com.tooklili.util.RandomUtils;
import com.tooklili.util.UUIDUtils;
import com.tooklili.util.security.Md5Utils;

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
	
	@Test
	public void regularTest(){
		String str="【优线围巾女秋冬季新款韩版百搭条纹披肩两用冬天保暖加厚超大长款】http://m.tb.cn/h.BpGsLI 点击链接，再选择浏览器打开；或复制这条信息￥79pf0jmXL59￥后打开手淘";
		String pattern ="^【(.+)】";
		
		// 创建 Pattern 对象
	    Pattern r = Pattern.compile(pattern);
	    
	    Matcher m = r.matcher(str);
	    
	    logger.info(m.groupCount()+"");
	    if(m.find()){
	    	logger.info(m.group(0));
	    	logger.info(m.group(1));
	    }  
	}
	
	@Test
	public void splitTest(){
		String str ="system:user:view";
		String[] strs =  str.split(":");
		logger.info("长度：{}",strs.length);
		strs[strs.length-1] ="*";
		logger.info(StringUtils.join(strs, ":"));
	}
	
	@Test
	public void md5Test(){
		logger.info(Md5Utils.hash("value"));
	}
	
	@Test
	public  void hashCodeTest() {
		logger.info(UUIDUtils.generateUuid32().hashCode()+"");
		logger.info( new Random().nextInt( 2 )+"");
	}
	

	@Test
	public void getItemIDTest() {
		for(int i=0;i<10;i++) {
			logger.info(RandomUtils.randomCharAndNum(5));
		}		
	}
}
