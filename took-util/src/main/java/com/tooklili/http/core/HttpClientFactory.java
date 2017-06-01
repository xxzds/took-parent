package com.tooklili.http.core;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * 缓存 HttpClientConnection 的工厂
 * @author ding.shuai
 * @date 2016年7月19日下午2:51:59
 */
public class HttpClientFactory {

    private static final int connectTimeout           = 90000;
    private static final int socketTimeout            = 90000;
    private static final int connectionRequestTimeout = 90000;

    private static final int maxTotalConns            = 2000;
    private static final int defaultMaxConnsPerRoute  = 100;


	public static CloseableHttpClient getCloseableHttpClient() {

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout).setConnectionRequestTimeout(connectionRequestTimeout)
                .setStaleConnectionCheckEnabled(true).build();

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(maxTotalConns);
        cm.setDefaultMaxPerRoute(defaultMaxConnsPerRoute);

        // 后续：
        // 1. 参数=>minas
        // 2. 扩展考虑根据具体应用，配置连接数
        return HttpClients.custom().setDefaultRequestConfig(requestConfig).setConnectionManager(cm).build();

    }

}
