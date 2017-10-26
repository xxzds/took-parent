package com.tooklili.service.biz.impl.wechat.util;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import com.tooklili.model.wechat.ImageText;
import com.tooklili.model.wechat.Music;
/**
 * 封装最终的xml格式结果
 * @author ding.shuai
 * @date 2016年9月27日下午11:12:26
 */
public class FormatXml {
	/**
	 * 回复文本消息
	 * @param to		 接收者
	 * @param from       发送者
	 * @param content    内容
	 * @return
	 */
	public static String formatXmlAboutText(String to, String from, String content) {
		StringBuffer sb = new StringBuffer();
		Date date = new Date();
		sb.append("<xml><ToUserName><![CDATA[");
		sb.append(to);
		sb.append("]]></ToUserName><FromUserName><![CDATA[");
		sb.append(from);
		sb.append("]]></FromUserName><CreateTime>");
		sb.append(date.getTime());
		sb.append("</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[");
		sb.append(content);
		sb.append("]]></Content><FuncFlag>0</FuncFlag></xml>");
		return sb.toString();
	}
	
	/**
	 * 回复音乐消息
	 * @return
	 */
	public static String formatXmlAboutMusic(String to, String from,Music music){
		
		String musicXml="<xml>"+
				"<ToUserName><![CDATA[{0}]]></ToUserName>"+
				"<FromUserName><![CDATA[{1}]]></FromUserName>"+
				"<CreateTime>{2}</CreateTime>"+
				"<MsgType><![CDATA[music]]></MsgType>"+
				"<Music>"+
				"<Title><![CDATA[{3}]]></Title>"+
				"<Description><![CDATA[{4}]]></Description>"+
				"<MusicUrl><![CDATA[{5}]]></MusicUrl>"+
				"<HQMusicUrl><![CDATA[{6}]]></HQMusicUrl>"+
				"<ThumbMediaId><![CDATA[{7}]]></ThumbMediaId>"+
				"</Music>"+
				"</xml>";
		
		musicXml = MessageFormat.format(musicXml, 
										to, 
										from, 
										new Date().getTime()+"", 
										music.getTitle(),
										music.getDescription(), 
										music.getMusicURL(), 
										music.getHQMusicUrl(),
										music.getThumbMediaId());
		return musicXml;
	}
	
	/**
	 * 回复图文消息
	 * @author shuai.ding
	 * @param to           接收者
	 * @param from         发送者
	 * @param imageTexts   图文内容集合，多条图文消息信息，默认第一个item为大图,注意，如果图文数超过8，则将会无响应
	 * @return
	 */
	public static String formatXmlAboutImageText(String to, String from,List<ImageText> imageTexts){
		String imageTextXml1="<xml>"+
			"<ToUserName><![CDATA[{0}]]></ToUserName>"+
			"<FromUserName><![CDATA[{1}]]></FromUserName>"+
			"<CreateTime>{2}</CreateTime>"+
			"<MsgType><![CDATA[news]]></MsgType>"+
			"<ArticleCount>{3}</ArticleCount>"+
			"<Articles>";
		
		String imageTextXml2="</Articles></xml>";
		
		String imageTextXml3="<item>"+
			"<Title><![CDATA[{0}]]></Title>"+
			"<Description><![CDATA[{1}]]></Description>"+
			"<PicUrl><![CDATA[{2}]]></PicUrl>"+
			"<Url><![CDATA[{3}]]></Url>"+
			"</item>";
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(MessageFormat.format(imageTextXml1,
				to,
				from,
				new Date().getTime(), 
				imageTexts.size()));
		
		for(ImageText imageText:imageTexts){
			sb.append(MessageFormat.format(imageTextXml3, 
					imageText.getTitle(),
					imageText.getDescription(),
					imageText.getPicUrl(),
					imageText.getUrl()));
		}
		
		sb.append(imageTextXml2);
		
		
		return sb.toString();
	}
}
