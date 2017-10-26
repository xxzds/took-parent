package com.tooklili.service.biz.impl.wechat.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.tooklili.util.PropertiesUtil;

/**
 * 调用图灵机器人api接口，获取智能回复内容
 * @author ding.shuai
 * @date 2016年9月27日下午11:05:17
 */
public class TulingApiProcess {
	
	private static final Logger logger=LoggerFactory.getLogger(TulingApiProcess.class);
	
	/**
	 * 调用图灵机器人api接口，获取智能回复内容，解析获取自己所需结果
	 * @param content
	 * @return
	 */
	public static String getTulingResult(String content){
		//此处为图灵api接口
		String apiUrl = PropertiesUtil.getInstance("wechat.properties").getValue("tuling.api.url");
		String param = "";
		try {
			param = apiUrl+URLEncoder.encode(content,"utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		// 发送httpget请求
		HttpGet request = new HttpGet(param);
		String result = "";
		try {
			HttpResponse response = HttpClients.createDefault().execute(request);
			if(response.getStatusLine().getStatusCode()==200){
				result = EntityUtils.toString(response.getEntity(),"utf-8");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 请求失败处理
		if(null==result){
			return "对不起，你说的话真是太高深了……";
		}
		
		try {
			logger.info("图灵机器人的响应数据{}",result);
			JSONObject json = JSON.parseObject(result);
			//以code=100000为例，参考图灵机器人api文档
			if(100000==json.getInteger("code")){
				result = json.getString("text");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
}