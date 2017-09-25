package com.tooklili.service.biz.impl.taobao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.tooklili.http.HttpCallService;
import com.tooklili.model.taobao.ItemImageDetailModel;
import com.tooklili.service.biz.intf.taobao.TaobaoService;
import com.tooklili.util.result.ListResult;
import com.tooklili.util.result.PlainResult;

/**
 * 淘宝服务
 * @author shuai.ding
 * @date 2017年9月25日下午8:16:03
 */
@Service
public class TaobaoServiceImpl implements TaobaoService{
	private static final Logger LOGGER =LoggerFactory.getLogger(TaobaoServiceImpl.class);
	
	@Resource
	private HttpCallService httpCallService;

	
	@Override
	public ListResult<String> getItemImages(String numIid) throws UnsupportedEncodingException {
		ListResult<String> result = new ListResult<String>();
		
		if(StringUtils.isEmpty(numIid)){
			return result.setErrorMessage("numIid不能为空");
		}
		
		String url ="https://hws.m.taobao.com/cache/mtop.wdetail.getItemDescx/4.1/";
		Map<String, String> params = new HashMap<String, String>();
		params.put("data",URLEncoder.encode("{item_num_id:\""+numIid+"\"}", "utf-8"));
		PlainResult<String> responseResult = httpCallService.httpGet(url,params);
		if(!responseResult.isSuccess()){
			LOGGER.info("调用接口[{}]失败，失败原因：{}",url,responseResult.getMessage());
			return result.setErrorMessage("通过[numIid="+numIid+"]调用商品详情图片失败");
		}
		ItemImageDetailModel itemImageDetailModel = JSON.parseObject(responseResult.getData(), ItemImageDetailModel.class);		
		result.setData(itemImageDetailModel.getData().getImages());
		return result;
		
	}

}
