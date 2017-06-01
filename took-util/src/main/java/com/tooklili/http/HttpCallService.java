package com.tooklili.http;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.tooklili.util.result.PlainResult;


/**
 * http同步请求服务
 * @author ding.shuai
 * @date 2016年7月19日下午2:42:39
 */
public interface HttpCallService {

    PlainResult<String> httpGet(String requestUri);

    PlainResult<String> httpGet(String requestUri, Map<String, String> params);

    PlainResult<String> httpPost(String requestUri);

    PlainResult<String> httpPost(String requestUri, Map<String, String> params);

    PlainResult<String> urlConnectionPost(String url, String params);

    PlainResult<String> urlConnectionGet(String url, Map<String, String> params);
    
    PlainResult<String> httpUpload(String requestUri,File file);
    
    PlainResult<String> httpUpload(String requestUri,File file,String fileName);
    
    PlainResult<String> httpUpload(String requestUri,File file,Map<String, String> params);
    
    PlainResult<String> httpUpload(String requestUri,File file,String fileName,Map<String, String> params);
    
    PlainResult<String> httpUpload(String requestUri,List<File> files);
    
    PlainResult<String> httpUpload(String requestUri,List<File> files,String fileName);
    
    PlainResult<String> httpUpload(String requestUri,List<File> files,Map<String, String> params);
    
    PlainResult<String> httpUpload(String requestUri,List<File> files,String fileName,Map<String, String> params);

}
