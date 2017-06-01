package com.tooklili.http;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ResponseHandler;

import com.tooklili.http.core.HttpGetUtil;
import com.tooklili.http.core.HttpPostUtil;
import com.tooklili.http.core.HttpResponseHandler;
import com.tooklili.http.core.HttpUploadUtil;
import com.tooklili.http.core.HttpUrlConnectionUtil;
import com.tooklili.util.result.PlainResult;

/**
 * http同步请求的实现
 * @author ding.shuai
 * @date 2016年7月20日下午2:33:05
 */
public class HttpCallServiceImpl implements HttpCallService {

    private static final ResponseHandler<PlainResult<String>> defaultResponseHandler = new HttpResponseHandler();

    @Override
    public PlainResult<String> httpGet(String requestUri) {
        return HttpGetUtil.execute(requestUri, defaultResponseHandler);
    }

    @Override
    public PlainResult<String> httpGet(String requestUri, Map<String, String> params) {
        return HttpGetUtil.execute(requestUri, params, defaultResponseHandler);
    }

    @Override
    public PlainResult<String> httpPost(String requestUri) {
        return HttpPostUtil.execute(requestUri, null, defaultResponseHandler);
    }

    @Override
    public PlainResult<String> httpPost(String requestUri, Map<String, String> params) {
        return HttpPostUtil.execute(requestUri, params, defaultResponseHandler);
    }

    @Override
    public PlainResult<String> urlConnectionPost(String url, String params) {
        PlainResult<String> result = new PlainResult<String>();
        try {
            String rt = HttpUrlConnectionUtil.doPost(url, params, 5000);
            result.setData(rt);
            return result;
        } catch (Exception e) {
            return result.setErrorMessage(e.getMessage());
        }
    }

    @Override
    public PlainResult<String> urlConnectionGet(String url, Map<String, String> params) {
        PlainResult<String> result = new PlainResult<String>();
        try {
            String rt = HttpUrlConnectionUtil.doGet(url, params, 5000);
            result.setData(rt);
            return result;
        } catch (Exception e) {
        	return result.setErrorMessage(e.getMessage());
        }
    }

	@Override
	public PlainResult<String> httpUpload(String requestUri, File file) {
		List<File> files=new ArrayList<File>();
		files.add(file);
		return HttpUploadUtil.execute(requestUri, files, null, defaultResponseHandler);
	}
	
	@Override
	public PlainResult<String> httpUpload(String requestUri,File file,String fileName){
		List<File> files=new ArrayList<File>();
		files.add(file);
		return HttpUploadUtil.execute(requestUri, files, fileName,null, defaultResponseHandler);
	}

	@Override
	public PlainResult<String> httpUpload(String requestUri, File file, Map<String, String> params) {
		List<File> files=new ArrayList<File>();
		files.add(file);
		return HttpUploadUtil.execute(requestUri, files, params, defaultResponseHandler);
	}
	
	public PlainResult<String> httpUpload(String requestUri,File file,String fileName,Map<String, String> params){
		List<File> files=new ArrayList<File>();
		files.add(file);
		return HttpUploadUtil.execute(requestUri, files, fileName, params, defaultResponseHandler);
	}

	@Override
	public PlainResult<String> httpUpload(String requestUri, List<File> files) {
		return HttpUploadUtil.execute(requestUri, files, null, defaultResponseHandler);
	}
	
	@Override
	public PlainResult<String> httpUpload(String requestUri,List<File> files,String fileName){
		return HttpUploadUtil.execute(requestUri, files,fileName, null, defaultResponseHandler);
	}

	@Override
	public PlainResult<String> httpUpload(String requestUri, List<File> files, Map<String, String> params) {
		return HttpUploadUtil.execute(requestUri, files, params, defaultResponseHandler);
	}
	
	@Override
	public PlainResult<String> httpUpload(String requestUri,List<File> files,String fileName,Map<String, String> params){
		return HttpUploadUtil.execute(requestUri, files,fileName, params, defaultResponseHandler);
	}

}
