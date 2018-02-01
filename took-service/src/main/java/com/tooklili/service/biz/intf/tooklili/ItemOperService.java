package com.tooklili.service.biz.intf.tooklili;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import com.taobao.api.ApiException;
import com.taobao.api.response.TbkDgItemCouponGetResponse.TbkCoupon;
import com.tooklili.model.taobao.AlimamaItem;
import com.tooklili.util.result.BaseResult;

/**
 * 操作商品(主要做插入和更新)
 * @author shuai.ding
 * @date 2017年10月25日上午11:30:17
 */
public interface ItemOperService {
	
	/**
	 * 插入或更新商品
	 * @author shuai.ding
	 * @param alimamaItem   超级搜索接口对应的实体
	 * @param itemCateId    商品插入数据库的分类id
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws ParseException 
	 */
	public BaseResult insertOrUpdate(AlimamaItem alimamaItem,Integer itemCateId) throws UnsupportedEncodingException, ParseException;
	
	
	/**
	 * 插入或更新商品
	 * @author shuai.ding
	 * @param tbkCoupon   淘宝客接口对应的实体
	 * @param itemCateId    商品插入数据库的分类id
	 * @return
	 * @throws ApiException 
	 * @throws ParseException 
	 */
	public BaseResult insertOrUpdate(TbkCoupon tbkCoupon,Integer itemCateId) throws ApiException, ParseException;

}
