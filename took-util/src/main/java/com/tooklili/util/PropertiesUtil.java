package com.tooklili.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

/**
 * 读取properties文件的工具类
 * @author ding.shuai
 * @date 2016年7月28日下午9:06:56
 */
public class PropertiesUtil {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(PropertiesUtil.class);
	
	private static Map<String, PropertiesUtil> resoures = Maps.newHashMap();
	
	private Properties properties=new Properties();
	
	private PropertiesUtil(){		
	}
	
	private PropertiesUtil(String path){
		try{
			String pathString = PropertiesUtil.class.getClassLoader().getResource(path).getFile();
			if("/".equals(File.separator)){   
				pathString = pathString.replace("\\",File.separator);
			}
			pathString =  pathString.replace("%20"," ");
			FileInputStream inputStream=new FileInputStream(pathString);
			properties.load(inputStream);
		}catch(IOException e){
			LOGGER.error(e.getMessage(),e);
		}
		
	}
	
	public static PropertiesUtil getInstance(String path){
		if(StringUtils.isEmpty(path)){
			throw new RuntimeException("路径不能为空");
		}
		
		if(resoures.get(path) ==null){
			resoures.put(path, new PropertiesUtil(path));
		}
		return resoures.get(path);
	}
	
	
	public String getValue(String key){		
		String value=null;
		try {
			//解决中文乱码问题
			 value = new String(properties.getProperty(key).getBytes("ISO-8859-1"), "UTF-8");		
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("get key["+key+"] value UnsupportedEncodingException",e);
		}catch(Exception e){
			LOGGER.error("get key["+key+"] value exception",e);
		}
		if (value == null) {
			return "";
		}
		return value;
	}
	
	public static void main(String[] args) {
		System.out.println(PropertiesUtil.getInstance("mail.properties").getValue("mail.from.alias"));
	}
	
}
