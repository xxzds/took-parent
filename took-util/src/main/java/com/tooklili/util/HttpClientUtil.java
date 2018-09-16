package com.tooklili.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

/**
 * httpClient工具类
 * 
 * @author shuai.ding
 *
 * @date 2017年5月27日下午3:24:49
 */
public class HttpClientUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);
	
	public static String get(String url,String cookies){
		String content=null;
		// 创建默认的httpclient实例
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httpget
		HttpGet httpGet = new HttpGet(url);
		
		//设置cookies
		if(StringUtils.isNotEmpty(cookies)){
			httpGet.setHeader("Cookie", cookies);
		}
		
		//模拟浏览器头部
		httpGet.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
//		httpGet.setHeader("referer","https://pub.alimama.com/?spm=a2320.7388781.a214tr8.d006.6f3720306RMUZv");
		httpGet.setHeader("x-requested-with","XMLHttpRequest");
		httpGet.setHeader(":authority","pub.alimama.com");
		httpGet.setHeader(":method","GET");
//		httpGet.setHeader(":path","/getLogInfo.htm?callback=__jp0");
		httpGet.setHeader(":scheme","https");
		
		
		try{
			// 执行get请求
			CloseableHttpResponse response = httpclient.execute(httpGet);
			//返回内容
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				content =EntityUtils.toString(entity, "UTF-8");				 
			}
	
		}catch(IOException e){
			LOGGER.error("exception",e);
		}		
		return content;
	}
	
	public static String post(String url,Map<String, String> params, String cookies){
		String content=null;
		// 创建默认的httpclient实例
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost
		HttpPost httpPost = new HttpPost(url);
		
		//设置cookies
		if(StringUtils.isNotEmpty(cookies)){
			httpPost.setHeader("Cookie", cookies);
		}	
		
		// 创建参数
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		if (params != null) {
			for (Entry<String, String> param : params.entrySet()) {
				paramsList.add(new BasicNameValuePair(param.getKey(), param.getValue()));
			}
		}
		
		try{
			//设置编码格式，防止乱码
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(paramsList, "UTF-8");
			httpPost.setEntity(uefEntity);
			// 执行post请求
			CloseableHttpResponse response = httpclient.execute(httpPost);
			//返回内容
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				content =EntityUtils.toString(entity, "UTF-8");				 
			}
	
		}catch(IOException e){
			LOGGER.error("exception",e);
		}		
		return content;
	}

	public static Map<String, String> getCookiesByGet(String url){
		Map<String, String> cookies = Maps.newHashMap();

		// 创建默认的httpclient实例
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httpget
		HttpGet httpGet = new HttpGet(url);
		
		try{
			// 执行get请求
			CloseableHttpResponse response = httpclient.execute(httpGet);
			Header[] headers = response.getAllHeaders();

			for (Header header : headers) {
				String name = header.getName();
				String value = header.getValue();

				if ("Set-Cookie".equals(name)) {
					String[] values = value.split(";");
					if (value != null || value.length() > 0) {
						String[] strs = values[0].split("=");
						cookies.put(strs[0], strs[1]);
					}
				}
			}
		}catch(IOException e){
			LOGGER.error("exception",e);
		}		
		return cookies;
	}

	public static Map<String, String> getCookiesByPost(String url, Map<String, String> params, String cookies) {
		Map<String, String> responseCookies = Maps.newHashMap();
		
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost
		HttpPost httpPost = new HttpPost(url);

		// 创建参数
		List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
		if (params != null) {
			for (Entry<String, String> param : params.entrySet()) {
				paramsList.add(new BasicNameValuePair(param.getKey(), param.getValue()));
			}
		}
		
		//设置cookies
		if(StringUtils.isNotEmpty(cookies)){
			httpPost.setHeader("Cookie", cookies);
		}
		
		CloseableHttpResponse response = null;
		try {
			//设置编码格式，防止乱码
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(paramsList, "UTF-8");
			httpPost.setEntity(uefEntity);
		    response = httpclient.execute(httpPost);	
			
			//响应头部
			Header[] responseHeaders = response.getAllHeaders();
			for (Header header : responseHeaders) {
				String name = header.getName();
				String value = header.getValue();

				if ("Set-Cookie".equals(name)) {
					String[] values = value.split(";");
					if (value != null || value.length() > 0) {
						String[] strs = values[0].split("=");
						responseCookies.put(strs[0], strs[1]);
					}
				}
			}

			//返回内容
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				  String content =EntityUtils.toString(entity, "UTF-8");
				  LOGGER.info("url[{}],返回内容为：{}",url,content);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}finally {			
			try {
				if(response!=null){
					response.close();
				}
				//关闭连接,释放资源
				httpclient.close();
			} catch (IOException e) {
				LOGGER.error("exception",e);
			}
		}
		return responseCookies;
	}
}
