package com.tooklili.http.core;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tooklili.util.result.CommonResultCode;
import com.tooklili.util.result.PlainResult;

/**
 * post请求
 * @author ding.shuai
 * @date 2016年7月19日下午3:24:52
 */
public class HttpPostUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpPostUtil.class);

    /**
     * @param requestUri
     * @param params
     * @param responseHandler
     * @return PlainResult<String>
     */
    public static PlainResult<String> execute(final String requestUri, final Map<String, String> params,
                                              final ResponseHandler<PlainResult<String>> responseHandler) {
        if (StringUtils.isBlank(requestUri)) {
        	return new PlainResult<String>().setErrorMessage(CommonResultCode.ILLEGAL_PARAM, "requestUri");
        }

        CloseableHttpClient httpclient = HttpClientFactory.getCloseableHttpClient();
        try {
            HttpUriRequest request = buildUriPostRequest(requestUri, params);
            
            return httpclient.execute(request, responseHandler);
        } catch (Exception e) {
        	log.error("post request exception", e);
            String msg = e.getCause() == null ? e.toString() : e.getCause().getMessage();
            return new PlainResult<String>().setErrorMessage(CommonResultCode.EXCEPITON_HTTP_CALL, msg);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                log.error("error message", e);
            }
        }

    }


    private static HttpUriRequest buildUriPostRequest(final String requestUri, final Map<String, String> params)
            throws URISyntaxException {
        RequestBuilder builder = RequestBuilder.post().setUri(new URI(requestUri));
        
        List<BasicNameValuePair> listParams = new ArrayList<BasicNameValuePair>();
        if (params != null) {
            for (Entry<String, String> param : params.entrySet()) {
                listParams.add(new BasicNameValuePair(param.getKey(), param.getValue()));
            }
        }
        //设置编码格式，防止中文乱码
        builder.setEntity(new UrlEncodedFormEntity(listParams, Consts.UTF_8)); 
        return builder.build();
    }
}
