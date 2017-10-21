package com.tooklili.service.biz.intf.taobao;

import java.io.UnsupportedEncodingException;

import com.tooklili.util.result.ListResult;
import com.tooklili.util.result.PlainResult;

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
	
	
	/**
	 * 通过商品id获取优惠券链接
	 * @author shuai.ding
	 * @param itemId
	 * @return
	 */
	
	/**
	 * 通过商品id和优惠券id获取优惠券链接
	 * @author shuai.ding
	 * @param activityId  优惠券id 32位
	 * @param itemId  商品id
	 * @return
	 */
	public PlainResult<String> getCouponUrlByItemId(String activityId,String itemId);
	
	/**
	 * 通过商品id，获取商品的子标题
	 * @author shuai.ding
	 * @param itemId
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public PlainResult<String> getItemSubTitleByItemId(String itemId) throws UnsupportedEncodingException;

}
