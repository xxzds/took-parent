package com.tooklili.service.biz.impl.taobao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
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
import com.tooklili.util.PropertiesUtil;
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
		
		//获取淘宝宝贝详情图片的接口
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


	@Override
	public PlainResult<String> getCouponUrlByItemId(String activityId,String itemId) {
		PlainResult<String> result = new PlainResult<String>();
		if(StringUtils.isEmpty(itemId)){
			return result.setErrorMessage("商品ID不能为空");
		}
		if(StringUtils.isEmpty(activityId)){
			return result.setErrorMessage("优惠券ID不能为空");
		}
		
		//http://uland.taobao.com/coupon/edetail?activityId=32位券ID&pid=3段式mm号&itemId=商品ID&src=淘客工具接口参数&dx=
		String urlTemplate = "https://uland.taobao.com/coupon/edetail?activityId={0}&pid={1}&itemId={2}&src=cd_cdll";		
		String pid="mm_"+PropertiesUtil.getInstance("tbk.properties").getValue("tbk.pid");
		String couponUrl = MessageFormat.format(urlTemplate,activityId, pid,itemId);		
		result.setData(couponUrl);
		return result;
	}


	@Override
	public PlainResult<String> getItemSubTitleByItemId(String itemId) throws UnsupportedEncodingException {
		PlainResult<String> result = new PlainResult<String>();
		if(StringUtils.isEmpty(itemId)){
			return result.setErrorMessage("商品ID不能为空");
		}
		
		//获取商品详情H5页面接口
		String url="https://acs.m.taobao.com/h5/mtop.taobao.detail.getdetail/6.0/";
		Map<String, String> params = new HashMap<String, String>();
		params.put("qq-pf-to", "pcqq.group");
		params.put("data",URLEncoder.encode("{\"itemNumId\":\""+itemId+"\"}", "utf-8"));		
		PlainResult<String> responseResult =  httpCallService.httpGet(url,params);
		
		//子标题
		String intro = JSON.parseObject(responseResult.getData()).getJSONObject("data").getJSONObject("item").getString("subtitle");
		result.setData(intro);
		return result;
	}

}
