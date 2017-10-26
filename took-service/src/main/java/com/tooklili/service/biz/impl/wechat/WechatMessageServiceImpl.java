package com.tooklili.service.biz.impl.wechat;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.tooklili.model.taobao.AlimamaItem;
import com.tooklili.model.taobao.AlimamaReqItemModel;
import com.tooklili.model.wechat.ImageText;
import com.tooklili.model.wechat.ReceiveXmlEntity;
import com.tooklili.service.biz.impl.wechat.util.FormatXml;
import com.tooklili.service.biz.impl.wechat.util.ReciveXmlParse;
import com.tooklili.service.biz.intf.taobao.AlimamaService;
import com.tooklili.service.biz.intf.wechat.WechatMessageService;
import com.tooklili.util.result.PageResult;


/**
 * 微信xml消息推送的服务
 * @author shuai.ding
 * @date 2017年10月26日下午3:28:45
 */
@Service
public class WechatMessageServiceImpl implements WechatMessageService{
	private static final Logger LOGGER = LoggerFactory.getLogger(WechatMessageServiceImpl.class);
	
	@Resource
	private AlimamaService alimamaService;

	/**
	 * 处理xml数据
	 * @param xml  微信传入的数据
	 * @return  返回xml格式的数据
	 * @throws UnsupportedEncodingException 
	 */
	@Override
	public String  processWechatMag(String xml) throws UnsupportedEncodingException{
		String result="";
		//解析xml数据
		ReceiveXmlEntity xmlEntity=ReciveXmlParse.getMsgEntity(xml);
		LOGGER.info(JSON.toJSONString(xmlEntity));
		
		//接收的内容
		String reciveContent = xmlEntity.getContent();
		
		switch (reciveContent) {
		case "1": 
			String content = "http://www.tooklili.com:81/took-app";
			result = FormatXml.formatXmlAboutText(xmlEntity.getFromUserName(), xmlEntity.getToUserName(), content);
			break;
		default: 
			AlimamaReqItemModel alimamaReqItemModel = new AlimamaReqItemModel();
			alimamaReqItemModel.setQ(reciveContent);
			PageResult<AlimamaItem> pageResult = alimamaService.superSearchItems(alimamaReqItemModel);
			List<AlimamaItem> alimamaItems = pageResult.getData();
			if(alimamaItems != null && alimamaItems.size()>0){
				
				List<ImageText> imageTexts = Lists.newArrayList();
				for(int i=0;i<8;i++){
					AlimamaItem alimamaItem = alimamaItems.get(i);

					ImageText imageText=new ImageText();
					imageText.setTitle(alimamaItem.getTitle());
					imageText.setDescription(alimamaItem.getCouponInfo());
					imageText.setPicUrl(alimamaItem.getPictUrl());
					imageText.setUrl("http://www.tooklili.com:81/took-app");
					
					imageTexts.add(imageText);
				}
											
				result = FormatXml.formatXmlAboutImageText(xmlEntity.getFromUserName(), xmlEntity.getToUserName(), imageTexts);
			}
			break;
		}

		return result;
	}
}
