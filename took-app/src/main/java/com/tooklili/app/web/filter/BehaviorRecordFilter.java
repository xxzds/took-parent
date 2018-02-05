package com.tooklili.app.web.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.tooklili.app.web.util.IpUtils;
import com.tooklili.app.web.util.WebUtils;
import com.tooklili.util.DateUtil;

/**
 * 行为记录拦截器
 * @author shuai.ding
 * @date 2018年2月5日下午1:47:51
 */
public class BehaviorRecordFilter implements Filter{	
	private static final Logger LOGGER = LoggerFactory.getLogger(BehaviorRecordFilter.class);

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)servletRequest;	
		
		String currentUrl = WebUtils.getCurrentUrl(request);		
		String method = request.getMethod();		
		String ip = IpUtils.getIpAddr(request);
		
		Map<String, String[]> params = request.getParameterMap();  
		String queryString = "";  
        for (String key : params.keySet()) {  
            String[] values = params.get(key);  
            for (int i = 0; i < values.length; i++) {  
                String value = values[i];  
                queryString += key + "=" + value + "&";  
            }  
        }      
        if(queryString.length()>0){
        	 queryString = queryString.substring(0, queryString.length() - 1); 
        }
       
        BehaviorModel behaviorModel = new BehaviorModel(currentUrl, ip, method, queryString, DateUtil.getTimes());        
        LOGGER.info(JSON.toJSONString(behaviorModel));
        		
        chain.doFilter(servletRequest, servletResponse);		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
			
	}
	
	@Override
	public void destroy() {
		
	}
	
    /**
     * 内部类
     * @author shuai.ding
     * @date 2018年1月19日下午5:26:02
     */
	public class BehaviorModel{
		private String requestUrl;       //请求地址
		private String sourceIp;        //请求的原IP
		private String reqeustMethod;    //请求方法(GET/POST)
		private String params;           //请求参数
		private String requestTime;        //请求时间
		
		public BehaviorModel(){}
		
		public BehaviorModel(String requestUrl, String sourceIp, String reqeustMethod, String params,
				String requestTime) {
			super();
			this.requestUrl = requestUrl;
			this.sourceIp = sourceIp;
			this.reqeustMethod = reqeustMethod;
			this.params = params;
			this.requestTime = requestTime;
		}
		
		public String getRequestUrl() {
			return requestUrl;
		}
		public void setRequestUrl(String requestUrl) {
			this.requestUrl = requestUrl;
		}
		
		public String getSourceIp() {
			return sourceIp;
		}
		public void setSourceIp(String sourceIp) {
			this.sourceIp = sourceIp;
		}

		public String getReqeustMethod() {
			return reqeustMethod;
		}
		public void setReqeustMethod(String reqeustMethod) {
			this.reqeustMethod = reqeustMethod;
		}
		public String getParams() {
			return params;
		}
		public void setParams(String params) {
			this.params = params;
		}
		public String getRequestTime() {
			return requestTime;
		}
		public void setRequestTime(String requestTime) {
			this.requestTime = requestTime;
		}
	}
}
