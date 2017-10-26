package com.tooklili.service.biz.impl.wechat.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tooklili.enums.wechat.MsgType;
import com.tooklili.model.wechat.ReceiveXmlEntity;

/**
 * 解析从微信接收到的xml消息(通过反射)
 * @author ding.shuai
 * @date 2016年9月27日下午10:13:13
 */
public class ReciveXmlParse {
	
	private static final Logger logger=LoggerFactory.getLogger(ReciveXmlParse.class);
	
	/**
	 * 解析xml消息
	 * @param strXml
	 * @return
	 */
	public static ReceiveXmlEntity getMsgEntity(String strXml){
		ReceiveXmlEntity msg = null;
		try {
			if (StringUtils.isEmpty(strXml))
				return null;
			 
			// 将字符串转化为XML文档对象
			Document document = DocumentHelper.parseText(strXml);
			// 获得文档的根节点
			Element root = document.getRootElement();
			// 遍历根节点下所有子节点
			Iterator<?> iter = root.elementIterator();
			
			// 遍历所有结点,利用反射机制，调用set方法
			// 获取该实体的元类型
//			Class.forName("com.anjz.core.model.wechat.ReceiveXmlEntity");
			Class<ReceiveXmlEntity> c = ReceiveXmlEntity.class;
			msg = c.newInstance();//创建这个实体的对象
						
			while(iter.hasNext()){
				Element ele = (Element)iter.next();
				//获取字段属性
				Field field = c.getDeclaredField(ele.getName());
				//获取方法
				Method method;
				try{
					method = c.getDeclaredMethod("set"+ele.getName(), field.getType());
				}catch(NoSuchMethodException e){
					logger.warn("set"+ele.getName()+"方法不存在");
					continue;
				}
								
				//调用set方法				
				if(field.getType().getName().equals("java.lang.Long")){
					method.invoke(msg, Long.valueOf(ele.getText()));
				}else if(field.getType().getName().equals(MsgType.class.getName())){					
					method.invoke(msg, MsgType.valueOf(ele.getText()));
				}else if(field.getType().getName().equals("double")){
					method.invoke(msg, Double.valueOf(ele.getText()).doubleValue());
				}else{
					method.invoke(msg, ele.getText());
				}
				
				
			}
		} catch (Exception e) {
			logger.info("xml 格式异常: "+ strXml);
		}
		return msg;
	}
}
