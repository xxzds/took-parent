package com.tooklili.service.biz.intf.taobao;

import java.io.UnsupportedEncodingException;

import com.tooklili.util.result.ListResult;

/**
 * 淘宝服务
 * @author shuai.ding
 * @date 2017年9月25日下午8:15:46
 */
public interface TaobaoService{
	
	/**
	 * https://hws.m.taobao.com/cache/mtop.wdetail.getItemDescx/4.1/?data=%7Bitem_num_id%3A%2240545413689%22%7D
	 * 此接口支持jsonp，type=jsonp&dataType=jsonp
	 * 通过商品id，查询商品详情页的图片列表
	 * @author shuai.ding
	 * @param numIid
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public ListResult<String> getItemImages(String numIid) throws UnsupportedEncodingException;

}
