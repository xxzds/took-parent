package com.tooklili.service.biz.impl.wechat;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.tooklili.enums.wechat.MsgType;
import com.tooklili.model.taobao.AlimamaItem;
import com.tooklili.model.taobao.AlimamaReqItemModel;
import com.tooklili.model.wechat.ImageText;
import com.tooklili.model.wechat.ReceiveXmlEntity;
import com.tooklili.service.biz.impl.wechat.util.FormatXml;
import com.tooklili.service.biz.impl.wechat.util.ReciveXmlParse;
import com.tooklili.service.biz.intf.taobao.AlimamaService;
import com.tooklili.service.biz.intf.wechat.WechatMessageService;
import com.tooklili.util.Arith;
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
		
		
		//接收的文本类型
		if(xmlEntity.getMsgType()==MsgType.text){
			switch (reciveContent) {
			case "1": 
				String content = "http://www.tooklili.com/took-app";
				result = FormatXml.formatXmlAboutText(xmlEntity.getFromUserName(), xmlEntity.getToUserName(), content);
				break;
			default: 
				//中文、英文逗号，都可进行分割
				String[] params = reciveContent.split(",");
				if(params!=null && params.length==1){
					params = reciveContent.split("，");
				}
				
				//关键字过滤(如果用户直接从手淘中复制的链接，需要过滤到标题)
				String pattern ="^【(.+)】";
				// 创建 Pattern 对象
			    Pattern r = Pattern.compile(pattern);			    
			    Matcher m = r.matcher(reciveContent);
			    if(m.find()){
			    	params[0] = m.group(1);
			    }
			    
				
				AlimamaReqItemModel alimamaReqItemModel = new AlimamaReqItemModel();
				alimamaReqItemModel.setQ(params[0]);
				//销量从高到低
				alimamaReqItemModel.setSortType(9);
				//包含店铺优惠券（选中为1）
				if(params.length>=2 && "1".equals(params[params.length-1])){
					alimamaReqItemModel.setDpyhq(params.length-1);
				}			
				PageResult<AlimamaItem> pageResult = alimamaService.superSearchItems(alimamaReqItemModel);
				List<AlimamaItem> alimamaItems = pageResult.getData();
				if(alimamaItems != null && alimamaItems.size()>0){
					
					List<ImageText> imageTexts = Lists.newArrayList();
					int count =alimamaItems.size()>=8 ? 8 : alimamaItems.size();
					for(int i=0;i<count;i++){
						AlimamaItem alimamaItem = alimamaItems.get(i);

						ImageText imageText=new ImageText();
						imageText.setTitle(alimamaItem.getTitle());
						imageText.setDescription(alimamaItem.getCouponInfo());
						imageText.setPicUrl(alimamaItem.getPictUrl());
						
						String clickUrl="http://www.tooklili.com/took-app/superSearchDetail.html?title="+URLEncoder.encode(alimamaItem.getTitle(), "utf-8")
						+"&picUrl="+URLEncoder.encode(alimamaItem.getPictUrl(),"utf-8")
						+"&zkPrice="+URLEncoder.encode(alimamaItem.getZkPrice(),"utf-8")
	                    +"&numIid="+URLEncoder.encode(alimamaItem.getAuctionId().toString(),"utf-8")
	                    +"&couponPrice="+URLEncoder.encode(Arith.sub(Double.valueOf(alimamaItem.getZkPrice()),Double.valueOf(alimamaItem.getCouponAmount()))+"","utf-8")
	                    +"&quan="+URLEncoder.encode(alimamaItem.getCouponAmount()+"","utf-8");
						imageText.setUrl(clickUrl);
						
						imageTexts.add(imageText);
					}
												
					result = FormatXml.formatXmlAboutImageText(xmlEntity.getFromUserName(), xmlEntity.getToUserName(), imageTexts);
				}
				break;
			}
		}
		
		if(xmlEntity.getMsgType() ==MsgType.event){
			String event = xmlEntity.getEvent();
			if("subscribe".equals(event)){   //订阅
				String content="欢迎关注dingshuai订阅号，海量淘宝天猫优惠券任你挑选,回复如下：\n"+
								"1 -推送网站链接\n"+
								"任意关键字 -推送商品列表\n"+
								"任意关键字,1 -推送带优惠券的商品列表\n"+
								"淘宝商品链接或商品标题 -查询此商品是否有优惠券\n"+
								"您的关注，是作者不断完善公众号的最大动力，在此再次感谢！";
				result = FormatXml.formatXmlAboutText(xmlEntity.getFromUserName(), xmlEntity.getToUserName(), content);
			}
			if("unsubscribe".equals(event)){  //取消订阅
				
			}
		}
		

		return result;
	}
}
