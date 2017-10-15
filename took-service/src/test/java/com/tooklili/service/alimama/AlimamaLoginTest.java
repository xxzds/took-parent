package com.tooklili.service.alimama;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.tooklili.http.HttpCallService;
import com.tooklili.service.BaseTest;
import com.tooklili.service.util.AlimamaCookieUtils;
import com.tooklili.util.HttpClientUtil;
import com.tooklili.util.JsonFormatTool;

public class AlimamaLoginTest extends BaseTest{
	
	@Resource
	private HttpCallService httpCallService;
	
	
	@SuppressWarnings("resource")
	@Test
	public void toLogin(){
		try{
			// 得到浏览器对象，直接New一个就能得到，现在就好比说你得到了一个浏览器了   
	        WebClient webclient = new WebClient();    
	        webclient.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true  
	        webclient.getOptions().setCssEnabled(false); //禁用css支持  
	        webclient.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常  
	        webclient.getOptions().setTimeout(10000); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待   
	        // 做的第一件事，去拿到这个网页，只需要调用getPage这个方法即可  
	        HtmlPage htmlpage = webclient.getPage("https://login.taobao.com/member/login.jhtml?style=mini&newMini2=true&from=alimama&redirectURL=http%3A%2F%2Flogin.taobao.com%2Fmember%2Ftaobaoke%2Flogin.htm%3Fis_login%3d1&full_redirect=true");   
	        // 根据名字得到一个表单，查看上面这个网页的源代码可以发现表单的名字叫“f”  
	        final HtmlForm form = htmlpage.getForms().get(2);
	        		
	        final HtmlTextInput username = form.getInputByName("TPL_username");   
	        username.setValueAttribute("tb6915945");  
	        final HtmlPasswordInput password = form.getInputByName("TPL_password");   
	        password.setValueAttribute("ds7918092");  
	        
	        // 设置好之后，模拟点击按钮行为。  
	        Page nextPage = htmlpage.executeJavaScript("document.getElementById('J_Form').submit()").getNewPage();
	       
	        
	        nextPage = webclient.getPage(nextPage.getUrl().toString());
	        
	        System.out.println("*************************************************************************************");
//	        System.out.println(nextPage.getWebResponse().getContentAsString());
	        System.out.println("*************************************************************************************");
	        System.out.println("");
	        System.out.println("Cookies : " + webclient.getCookieManager().getCookies().toString());
	        Set<Cookie> cookieSet = webclient.getCookieManager().getCookies();
	        StringBuilder cookies = new StringBuilder();
	        for(Cookie cookie:cookieSet){
	        	String name = cookie.getName();
	        	String value = cookie.getValue();
	        	cookies.append(name + "=" + value + ";");
	        }
	        logger.info(cookies.toString());
	        
	        
	        Page page =webclient.getPage("http://pub.alimama.com/common/getUnionPubContextInfo.json");
	        logger.info(JsonFormatTool.formatJson(page.getWebResponse().getContentAsString()));
			
	        
	        String url="https://pub.alimama.com/common/code/getAuctionCode.json?pvid=10_211.162.8.113_2145_1507970029219&auctionid=558386463934&t=1507970430118&scenes=1&adzoneid=69036167&siteid=19682654&_tb_token_=fed381b34a3e3";
	        String content = HttpClientUtil.get(url, cookies.toString());
			logger.info(JsonFormatTool.formatJson(content));
	       
		}catch(Exception e){
			logger.error("exception",e);
		}
		
	}
	
	
	@Test
	public void getAlimamaCookies() throws InterruptedException{
		
		String cookies = AlimamaCookieUtils.getLoginCookies();
		logger.info(cookies);
	}
	
	
	
	
	
	
	
	
	/**
	 * 测试htmlUnit
	 * @throws FailingHttpStatusCodeException
	 * @throws MalformedURLException
	 * @throws IOException
	 * https://yq.aliyun.com/articles/59521
	 */
	@SuppressWarnings("resource")
	@Test
	public void htmlUnitTest() throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		// 得到浏览器对象，直接New一个就能得到，现在就好比说你得到了一个浏览器了   
        WebClient webclient = new WebClient();    
        // 下面这2句可以写，也可以不写，设置false就是不加载css和js。访问速度更快   
        webclient.getOptions().setCssEnabled(false);   
        webclient.getOptions().setJavaScriptEnabled(false);  
        // 做的第一件事，去拿到这个网页，只需要调用getPage这个方法即可  
        HtmlPage htmlpage = webclient.getPage("https://baidu.com");   
        // 根据名字得到一个表单，查看上面这个网页的源代码可以发现表单的名字叫“f”  
        final HtmlForm form = htmlpage.getFormByName("f");  
        // 同样道理，获取”百度一下“这个按钮  
        final HtmlSubmitInput button = form.getInputByValue("百度一下");  
        // 得到搜索框  
        final HtmlTextInput textField = form.getInputByName("wd");   
        //设置搜索框的value  
        textField.setValueAttribute("战狼2");   
        // 设置好之后，模拟点击按钮行为。  
        final HtmlPage nextPage = button.click();  
        // 把结果转成String   
        String result = nextPage.asXml();  
        //得到的是点击后的网页  
        System.out.println(result);  
	}
	
	

}
