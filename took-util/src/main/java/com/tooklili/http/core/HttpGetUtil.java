package com.tooklili.http.core;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tooklili.util.result.CommonResultCode;
import com.tooklili.util.result.PlainResult;

/**
 * get请求
 * @author ding.shuai
 * @date 2016年7月20日下午1:34:32
 */
public class HttpGetUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpGetUtil.class);

    /**
     * 执行 HttpGet 请求
     *
     * @param requestUri
     * @param responseHandler
     * @return PlainResult<String>
     */
    public static PlainResult<String> execute(final String requestUri,
                                              final ResponseHandler<PlainResult<String>> responseHandler) {
        if (StringUtils.isBlank(requestUri)) {
            return new PlainResult<String>().setErrorMessage(CommonResultCode.ILLEGAL_PARAM, "requestUri");
        }

        CloseableHttpClient httpclient = HttpClientFactory.getCloseableHttpClient();
        try {
            HttpGet httpget = new HttpGet(requestUri);
            log.info("请求地址：{}",requestUri);
            return httpclient.execute(httpget, responseHandler);
        } catch (Exception e) {
        	log.error("httpGet exception",e);
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

    /**
     * 执行 HttpGet 请求
     *
     * @param requestUri
     * @param params
     * @param responseHandler
     * @return PlainResult<String>
     */
    public static PlainResult<String> execute(final String requestUri, Map<String, String> params,
                                              final ResponseHandler<PlainResult<String>> responseHandler) {
        if (StringUtils.isBlank(requestUri)) {
            return new PlainResult<String>().setErrorMessage(CommonResultCode.ILLEGAL_PARAM, "requestUri");
        }

        return execute(buildRequestUri(requestUri, params), responseHandler);
    }


    private static String buildRequestUri(final String requestUri, final Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return requestUri;
        }

        StringBuilder sb = new StringBuilder(requestUri);
        sb.append('?');

        for (Entry<String, String> param : params.entrySet()) {
            sb.append(param.getKey()).append('=').append(param.getValue()).append('&');
        }

        return sb.substring(0, sb.length() - 1);
    }

}
