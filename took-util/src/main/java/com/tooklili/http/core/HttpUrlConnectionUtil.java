package com.tooklili.http.core;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * jdk自带，http请求封装
 * @author ding.shuai
 * @date 2016年7月20日下午1:36:48
 */
public class HttpUrlConnectionUtil {
	
	private static final  Logger logger = LoggerFactory.getLogger(HttpUrlConnectionUtil.class);

    public static final String Y = "1";
    public static final String N = "0";

    public static final String TRUE = "1";
    public static final String FALSE = "0";

    public static final String PATH_SIGN = "/";
    public static final String PAIR_SIGN = ";";
    public static final String KEY_VALUE_SIGN = ":";
    public static final String VALUE_NOOP = "xx";
    public static final String UTF8 = "UTF-8";
    public static final String LINE_SEPARATOR = "\r\n";

    public static class HTTP_CONTENT_TYPE {
        public static final String HEAD_KEY     = "Content-type";
        public static final String JSON         = "application/json";
    }

    public static class HTTP_METHOD {
        public static final String POST         = "POST";
        public static final String GET          = "GET";
    }

    /**
     * post 请求（传输json数据）
     * @param urlStr      
     * @param param    json字符串
     * @param timeout  连接超时时间
     * @return
     * @throws SocketTimeoutException
     * @throws MalformedURLException
     * @throws IOException
     */
	public static String doPost(String urlStr, String param, int timeout) throws SocketTimeoutException, MalformedURLException, IOException {
		HttpURLConnection conn = null;
		BufferedReader in = null;
		OutputStream out = null;

		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(timeout);
			conn.setReadTimeout(timeout);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod(HTTP_METHOD.POST);
			conn.setRequestProperty(HTTP_CONTENT_TYPE.HEAD_KEY, HTTP_CONTENT_TYPE.JSON);
			logger.info("请求地址：{}",urlStr);
			out = conn.getOutputStream();
			//参数
			if(param!=null && !"".equals(param)){
				out.write(param.getBytes(UTF8));
			}
			out.flush();
			StringBuilder sb = new StringBuilder();
			String line;

			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), UTF8));

			while ((line = in.readLine()) != null) {
				sb.append(line).append(LINE_SEPARATOR);
			}
						
			logger.info("http响应的返回值：{}",sb.toString());
			return sb.toString();
		} catch (SocketTimeoutException e) {
			logger.error("[SocketTimeoutException]remoteCallByJson:url[" + urlStr + "] timout:[" + timeout + "]  msg=" + e.getMessage(),e);
			throw e;
		} catch (MalformedURLException e) {
			logger.error("[MalformedURLException]remoteCallByJson:url[" + urlStr + "] msg=" + e.getMessage(),e);
			throw e;
		} catch (IOException e) {
			logger.error("[IOException]remoteCallByJson:url[" + urlStr + "] msg=" + e.getMessage(),e);
			throw e;
		} finally {
			if (out != null) {
				out.flush();
			}

			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);

			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	public static String doGet(String urlStr, Map<String, String> params,
			int timeout) throws SocketTimeoutException, MalformedURLException,
			IOException {

		HttpURLConnection conn = null;
		BufferedReader in = null;

		try {
			StringBuilder urlParam = new StringBuilder();
			if (params != null) {
				for (String key : params.keySet()) {
					urlParam.append(urlParam.length() == 0 ? "?" : "&");
					urlParam.append(key).append("=").append(params.get(key));
				}
			}

			URL url = new URL(urlStr + urlParam.toString());
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(timeout);
			conn.setReadTimeout(timeout);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod(HTTP_METHOD.GET);

			StringBuilder sb = new StringBuilder();
			String line;

			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), UTF8));

			while ((line = in.readLine()) != null) {
				sb.append(line).append(LINE_SEPARATOR);
			}
		    logger.info("http响应的返回值：{}",sb.toString());
			return sb.toString();
		} catch (SocketTimeoutException e) {
			logger.error("[SocketTimeoutException]doGet: url[" + urlStr + "] timout:[" + timeout + "]  msg=" + e.getMessage(),e);
			throw e;
		} catch (MalformedURLException e) {
			logger.error("[MalformedURLException]doGet: url[" + urlStr + "] msg=" + e.getMessage(),e);
			throw e;
		} catch (IOException e) {
			logger.error("[IOException]doGet: url[" + urlStr + "] msg=" + e.getMessage(),e);
			throw e;
		} finally {
			IOUtils.closeQuietly(in);

			if (conn != null) {
				conn.disconnect();
			}
		}
	}
	
	
	/**
	 * 通过http get请求，获取二进制数据
	 * @author shuai.ding
	 * @param urlStr
	 * @param params
	 * @param timeout
	 * @return
	 * @throws SocketTimeoutException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static byte[] doGetReturnByte(String urlStr, Map<String, String> params,
			int timeout) throws SocketTimeoutException, MalformedURLException,
			IOException {

		HttpURLConnection conn = null;
		InputStream in = null;
		ByteArrayOutputStream out = null;

		try {
			StringBuilder urlParam = new StringBuilder();
			if (params != null) {
				for (String key : params.keySet()) {
					urlParam.append(urlParam.length() == 0 ? "?" : "&");
					urlParam.append(key).append("=").append(params.get(key));
				}
			}

			URL url = new URL(urlStr + urlParam.toString());
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(timeout);
			conn.setReadTimeout(timeout);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod(HTTP_METHOD.GET);
			// 输入流  
			in = conn.getInputStream();  
			
			
			byte[] buf = new byte[1024];  
			int length = 0;  
		    out = new ByteArrayOutputStream();
			while ((length = in.read(buf)) != -1) {  
			    out.write(buf, 0, length);  
			}  
		    		    
			return out.toByteArray();
		} catch (SocketTimeoutException e) {
			logger.error("[SocketTimeoutException]doGet: url[" + urlStr + "] timout:[" + timeout + "]  msg=" + e.getMessage());
			throw e;
		} catch (MalformedURLException e) {
			logger.error("[MalformedURLException]doGet: url[" + urlStr + "] msg=" + e.getMessage());
			throw e;
		} catch (IOException e) {
			logger.error("[IOException]doGet: url[" + urlStr + "] msg=" + e.getMessage());
			throw e;
		}catch(Exception e){
			logger.error("[Exception]doGet: url[" + urlStr + "] msg=" + e.getMessage());
			throw e;
		}finally {
			if (out != null) {
				out.flush();
			}

			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);

			if (conn != null) {
				conn.disconnect();
			}
		}
	}

}
