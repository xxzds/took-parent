package com.tooklili.http.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tooklili.util.result.CommonResultCode;
import com.tooklili.util.result.PlainResult;

/**
 * 上传
 * @author ding.shuai
 * @date 2016年7月20日下午1:38:11
 */
public class HttpUploadUtil {
	
	private static final Logger log = LoggerFactory.getLogger(HttpUploadUtil.class);

	
	/**
	 * 执行上传请求
	 * @param requestUri       请求上传地址
	 * @param files            上传文件集合
	 * @param params           普通参数
	 * @param responseHandler  回调方法，处理响应数据
	 * @return
	 */
	public static PlainResult<String> execute(final String requestUri, List<File> files, Map<String, String> params,
			final ResponseHandler<PlainResult<String>> responseHandler){
		return execute(requestUri, files, null, params, responseHandler);
	}
		

	/**
	 * 执行上传请求
	 * @param requestUri        请求上传地址
	 * @param files				上传文件集合
	 * @param fileName			上传文件对应的名称，如果为空，默认为“file”
	 * @param params			普通参数
	 * @param responseHandler   回调方法，处理响应数据
	 * @return
	 */
	public static PlainResult<String> execute(final String requestUri, List<File> files,String fileName, Map<String, String> params,
			final ResponseHandler<PlainResult<String>> responseHandler) {
		if(StringUtils.isBlank(requestUri)){
			return new PlainResult<String>().setErrorMessage(CommonResultCode.ILLEGAL_PARAM, requestUri);
		}
		
		if(files==null || files.size()==0){
			return new PlainResult<String>().setErrorMessage(-1, "上传的文件对象不能为空");
		}
		
		CloseableHttpClient httpclient = HttpClientFactory.getCloseableHttpClient();
		try {
			 HttpPost httppost = new HttpPost(requestUri);
			 MultipartEntityBuilder multipartEntityBuilder= MultipartEntityBuilder.create();
			 
			 if(StringUtils.isBlank(fileName)){
				 fileName="file";
			 }
			 
			 //文件参数
			 for(File file:files){
				 FileBody fileBody = new FileBody(file);
				 multipartEntityBuilder.addPart(fileName, fileBody);
			 }
			 
			 //普通参数
			 if(params!=null){
				 for (Entry<String, String> param : params.entrySet()) {
					 multipartEntityBuilder.addTextBody(param.getKey(), param.getValue());
		            }
			 }
			 
            HttpEntity reqEntity = multipartEntityBuilder.build();		
			httppost.setEntity(reqEntity);
			
            return httpclient.execute(httppost, responseHandler);
        } catch (Exception e) {
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
	
	
	//测试
    public static void main(String[] args) throws Exception {
    	
    	List<File> files=new ArrayList<File>();
    	files.add(new File("/Users/ding.shuai/Desktop/E8CCF630C68DAE2D024484D618B49509.png"));
    	
        execute("http://192.168.1.106:8080/FileServer/fileUpload/save.do", files, null, new HttpResponseHandler());
    }
}
