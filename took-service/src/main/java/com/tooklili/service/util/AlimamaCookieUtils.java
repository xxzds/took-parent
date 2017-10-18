package com.tooklili.service.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.tooklili.util.JsonFormatTool;

/**
 * 获取阿里妈妈登录cookie的工具类
 * 测试通过客户端频繁阿里妈妈，如果时间间隔很短，不会每次都成功，便于单元测试，将cookies存入redis中
 * @author ding.shuai
 * @date 2017年10月14日下午4:58:25
 */
public class AlimamaCookieUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(AlimamaCookieUtils.class);
	
	/**
	 * cookie存储时间
	 */
	private static final long TTL = 1800;

	/**
	 * cookie 存入redis的key
	 */
	private static final String key="alimama_cookie";
	
	
	/**
	 * 从redis中获取cookie
	 * @author shuai.ding
	 * @return
	 */
	public static String getCookiesFromRedis(){
		return RedisUtils.getString(key);
	}
	
	/**
	 * 获取登录的cookies
	 * @author shuai.ding
	 * @return
	 */
	public static String getLoginCookies() {
		//1.先从redis中获取cookie，如果没有再登录获取
		String cookies = RedisUtils.getString(key);
		if(StringUtils.isNotEmpty(cookies)){
			//更新ttl
			RedisUtils.setString(key, cookies, TTL);			
			return cookies;
		}
		return loginAlimama();
	}
	
	private static Object lock = new Object();
	private static String loginAlimama() {
		//解决高并发，同时调用登录接口，导致获取的cookie被覆盖。
		synchronized (lock) {
			String cookiesFromRedis = RedisUtils.getString(key);
			if(StringUtils.isNotEmpty(cookiesFromRedis)){
				return cookiesFromRedis;
			}
			
			StringBuilder cookies = new StringBuilder();			
			try{
				WebClient webclient = new WebClient();    
		        webclient.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true  
		        webclient.getOptions().setCssEnabled(false); //禁用css支持  
		        // 当出现Http error时，程序不抛异常继续执行
		        webclient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		        webclient.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常  
		        // 设置Ajax异步处理控制器即启用Ajax支持
		        webclient.setAjaxController(new NicelyResynchronizingAjaxController());
		        webclient.getOptions().setTimeout(10000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待
		        
			    loginByTaobaoAccount(webclient);
			    LOGGER.info("阿里妈妈登录成功");
		        	        
			    webclient.getPage("https://www.alimama.com/index.htm");
		        Set<Cookie> cookieSet = webclient.getCookieManager().getCookies();
		        for(Cookie cookie:cookieSet){
		        	String name = cookie.getName();
		        	String value = cookie.getValue();
		        	cookies.append(name + "=" + value + ";");
		        }	
		        //存储cookie至redis中
		        RedisUtils.setString(key, cookies.toString(), TTL);				
				return cookies.toString();	
			}catch(Exception e){
				LOGGER.error("exception",e);
				return null;
			}		
		}	
	}
	
	
	/**
	 * 通过淘宝账户登录，如果登录不成功，再重新模拟登录一次，知道登录成功为止
	 * @author shuai.ding
	 * @param webclient
	 * @throws FailingHttpStatusCodeException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private static void loginByTaobaoAccount(WebClient webclient) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		//通过淘宝账户登录
        HtmlPage htmlpage = webclient.getPage("https://login.taobao.com/member/login.jhtml?style=mini&newMini2=true&from=alimama&redirectURL=http%3A%2F%2Flogin.taobao.com%2Fmember%2Ftaobaoke%2Flogin.htm%3Fis_login%3d1&full_redirect=true");   
        //获取提交的表单
        final HtmlForm form = (HtmlForm)htmlpage.getElementById("J_Form");
        		
        final HtmlTextInput username = form.getInputByName("TPL_username");   
        username.setValueAttribute("tb6915945");  
        final HtmlPasswordInput password = form.getInputByName("TPL_password");   
        password.setValueAttribute("ds7918092");  
        
        // 设置好之后，模拟点击按钮行为。  
        Page nextPage = htmlpage.executeJavaScript("document.getElementById('J_Form').submit()").getNewPage();
//        HtmlButton submit = (HtmlButton) htmlpage.getElementById("J_SubmitStatic");
//        HtmlPage nextPage = submit.click();
        LOGGER.info("登录后跳转的url：{}",nextPage.getUrl().toString());
        
        //判断是否登录成功
        webclient.getOptions().setJavaScriptEnabled(false);
        Page page =webclient.getPage("http://pub.alimama.com/common/getUnionPubContextInfo.json");
	    LOGGER.info(JsonFormatTool.formatJson(page.getWebResponse().getContentAsString()));
	    Boolean isNoLogin =JSON.parseObject(page.getWebResponse().getContentAsString()).getJSONObject("data").getBoolean("noLogin");
	    if(isNoLogin!=null && isNoLogin==true){
	    	LOGGER.info("阿里妈妈没有登录成功");
	    	webclient.getOptions().setJavaScriptEnabled(true);
	    	loginByTaobaoAccount(webclient);
	    }
	}
}
