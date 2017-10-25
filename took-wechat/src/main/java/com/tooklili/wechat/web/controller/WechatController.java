package com.tooklili.wechat.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tooklili.util.PropertiesUtil;
import com.tooklili.wechat.web.util.SignUtil;
import com.tooklili.wechat.web.util.encrypt.WXBizMsgCrypt;

/**
 * 微信控制器
 * @author shuai.ding
 * @date 2017年10月25日下午5:44:16
 */
@Controller
@RequestMapping("/wechat")
public class WechatController {
	private static final Logger LOGGER = LoggerFactory.getLogger(WechatController.class);
	
	private final PropertiesUtil propertiesUtil =PropertiesUtil.getInstance("wechat.properties");
	//令牌
	private final String token = propertiesUtil.getValue("wechat.token");
	
	//应用id
	private final String appId = propertiesUtil.getValue("wechat.appid");
	
	//加解密秘钥
	private final String encodingAesKey = propertiesUtil.getValue("wechat.encodingAESKey");
	
	@RequestMapping("")
	public void accessAuth(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String result ="";
		try{
			request.setCharacterEncoding("UTF-8");  
	        response.setCharacterEncoding("UTF-8");  
	        
			// 微信加密签名
			String signature = request.getParameter("signature");
			// 时间戳
			String timestamp = request.getParameter("timestamp");
			// 随机数
			String nonce = request.getParameter("nonce");
			// 随机字符串
			String echostr = request.getParameter("echostr"); 
			//密文的安全签名串
			String msg_signature=request.getParameter("msg_signature");
			
			LOGGER.info("微信加密签名:{},时间戳：{},随机数:{},随机字符串:{},令牌:{},密文的安全签名串:{}"
					,signature,timestamp,nonce,echostr,token,msg_signature);
	  		
			
			// 通过检验signature对请求进行校验
			if (SignUtil.checkSignature(signature, timestamp, nonce,token)) {			
				// 判断是否是微信接入激活验证，只有首次接入验证时才会收到echostr参数，此时需要把它直接返回
				if(StringUtils.isNotEmpty(echostr)){
					result=echostr;
					LOGGER.info("微信接入成功！");
				}else{				
					//读取接收到的xml消息
			        StringBuffer sb = new StringBuffer();  
			        InputStream is = request.getInputStream();  
			        InputStreamReader isr = new InputStreamReader(is, "UTF-8");  
			        BufferedReader br = new BufferedReader(isr);  
			        String s = "";  
			        while ((s = br.readLine()) != null) {  
			            sb.append(s);  
			        }  
			        String xml = sb.toString();
			        LOGGER.info("微信端发送的数据:"+xml);
			        
			        //如果msg_signature不为空，则微信发送的数据是加密的，此处进行解密
			        if(StringUtils.isNotEmpty(msg_signature)){
			        	WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
			        	xml = pc.decryptMsg(msg_signature, timestamp, nonce, xml);
			        	LOGGER.info("解密后的xml:"+xml);
			        }
					//正常的微信处理流程  
		            
		            //加密
		            if(StringUtils.isNotEmpty(msg_signature)){
		            	WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
		            	result = pc.encryptMsg(result, timestamp, nonce);
		            }
				}				
			}
			
		}catch(Exception e){
			result="";
			LOGGER.info("微信请求响应异常{}",e.getMessage());
		}
		LOGGER.info("服务端响应的数据:"+result);
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		out.close();
		out = null;
	}	

}
