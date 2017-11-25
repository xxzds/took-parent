package com.tooklili.app.web.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author shuai.ding
 * @date 2017年11月21日上午9:48:07
 */
public class TestDemo {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestDemo.class);
	
	@Test
	public void test1(){
		try{
			RestTemplate restTemplate = new RestTemplate();

	        HttpHeaders headers = new HttpHeaders();
		    //  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
		    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		    //  封装参数，千万不要替换为Map与HashMap，否则参数无法传递
		    MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
		    //  也支持中文
		    params.add("volumnCode", "OUP001");
		    HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
		    //  执行HTTP请求
		    ResponseEntity<String> response = restTemplate.exchange("http://api.koukao.cn/baseResource/textbook/getLessonList", HttpMethod.POST, requestEntity, String.class);
	        LOGGER.info(response.getBody());
		}catch(Exception e){
			LOGGER.error("exception",e);
//			StringHttpMessageConverter AllEncompassingFormHttpMessageConverter
		}
	}

}
