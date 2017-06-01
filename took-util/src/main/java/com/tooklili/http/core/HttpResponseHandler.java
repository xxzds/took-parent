package com.tooklili.http.core;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tooklili.util.result.CommonResultCode;
import com.tooklili.util.result.PlainResult;

/**
 * http响应回调，统一处理
 * @author ding.shuai
 * @date 2016年7月19日下午2:52:36
 */
public class HttpResponseHandler implements ResponseHandler<PlainResult<String>> {
    private static final Logger  logger   = LoggerFactory.getLogger(HttpResponseHandler.class);

    @Override
    public PlainResult<String> handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
        PlainResult<String> result = new PlainResult<String>();

        final StatusLine statusLine = response.getStatusLine();
        logger.info("http响应的返回码为：{}",statusLine.getStatusCode());
        
        final HttpEntity entity = response.getEntity();
       
        if (statusLine.getStatusCode() < 200 || statusLine.getStatusCode() >= 300) {
            EntityUtils.consume(entity);
            return new PlainResult<String>().setErrorMessage(CommonResultCode.FAIL_HTTP_CALL,
                    statusLine.getReasonPhrase(), statusLine.getStatusCode());
        }
        
        String content=null;
        if(entity!=null){
        	//设定编码，防止中文乱码
        	content= EntityUtils.toString(entity,"utf-8");
        }
        result.setData(content);    
        logger.info("http响应的返回值：{}",content);
        return result;
    }

};
