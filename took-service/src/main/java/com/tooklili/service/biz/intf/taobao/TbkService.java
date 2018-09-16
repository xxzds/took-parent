package com.tooklili.service.biz.intf.taobao;

import com.taobao.api.ApiException;
import com.taobao.api.request.TbkDgItemCouponGetRequest;
import com.taobao.api.request.TbkDgMaterialOptionalRequest;
import com.taobao.api.response.TbkCouponGetResponse.MapData;
import com.taobao.api.response.TbkDgItemCouponGetResponse.TbkCoupon;
import com.tooklili.model.tooklili.Item;
import com.tooklili.util.result.ListResult;
import com.tooklili.util.result.PageResult;
import com.tooklili.util.result.PlainResult;
import com.tooklili.vo.tbk.TbkItemDetailRespVo;
import com.tooklili.vo.tbk.TbkItemReqVo;
import com.tooklili.vo.tbk.TbkItemRespVo;


/**
 * 淘宝客服务
 * @author shuai.ding
 *
 * @date 2017年8月4日下午5:11:22
 */
public interface TbkService {
	/**
	 * 商品列表
	 * @author shuai.ding
	 * @param tbkItemReqVo
	 * @return
	 * @throws ApiException
	 */
	public PageResult<TbkItemRespVo> getItems(TbkItemReqVo tbkItemReqVo) throws ApiException;
	
	/**
	 * 通过商品id，查询商品信息
	 * @author shuai.ding
	 * @param numIid
	 * @return
	 * @throws ApiException
	 */
	public PlainResult<TbkItemDetailRespVo> getItemDetail(String numIid) throws ApiException;
	
	/**
	 * 获取好券清单列表
	 * @author shuai.ding
	 * @param req
	 * @return
	 * @throws ApiException
	 */
	public PageResult<TbkCoupon> getCouponItems(TbkDgItemCouponGetRequest req) throws ApiException;
	
	/**
	 * 获取好券清单列表
	 * @author shuai.ding
	 * @param req
	 * @return
	 * @throws ApiException
	 */
	public PageResult<Item> getCouponItemsReturnItems(TbkDgItemCouponGetRequest req) throws ApiException;
		
	/**
	 * 获取淘口令
	 * @author shuai.ding
	 * @param text   口令弹框内容
	 * @param url    口令跳转目标页
	 * @param logo   口令弹框logoURL 可选 如https://uland.taobao.com/
	 * @param userFlag   用户标识 1、ds  2、gc
	 * @return
	 * @throws ApiException 
	 */
	public PlainResult<String> createTpwd(String text,String url,String logo,Integer userFlag) throws ApiException;
	
	/**
	 * 阿里妈妈推广券信息查询
	 * @author shuai.ding
	 * @param me   带券ID与商品ID的加密串
	 * @return
	 */
	public PlainResult<MapData> getCouponInfo(String me) throws ApiException;
	
	/**
	 * 通过“淘宝客物料下行-导购”接口，获取商品信息
	 * @param materialId   物料id
	 * @param currentPage  当前页
	 * @param pageSize     页面大小
	 * @return
	 * @throws ApiException 
	 */
	 ListResult<Item> getItems(Long materialId,Integer currentPage,Integer pageSize) throws ApiException;
	 
	 /**
	  * 通过“通用物料搜索API（导购）”接口，获取商品信息
	  * @param req
	  * @return
	  */
	 PageResult<Item> getItems(TbkDgMaterialOptionalRequest req) throws ApiException;
}
