package com.tooklili.service.biz.intf.taobao;

import com.taobao.api.ApiException;
import com.taobao.api.request.TbkDgItemCouponGetRequest;
import com.taobao.api.response.TbkCouponGetResponse.MapData;
import com.taobao.api.response.TbkDgItemCouponGetResponse.TbkCoupon;
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
	 * 获取淘口令
	 * @author shuai.ding
	 * @param text   口令弹框内容
	 * @param url    口令跳转目标页
	 * @param logo   口令弹框logoURL 可选 如https://uland.taobao.com/
	 * @return
	 * @throws ApiException 
	 */
	public PlainResult<String> createTpwd(String text,String url,String logo) throws ApiException;
	
	/**
	 * 阿里妈妈推广券信息查询
	 * @author shuai.ding
	 * @param me   带券ID与商品ID的加密串
	 * @return
	 */
	public PlainResult<MapData> getCouponInfo(String me) throws ApiException;
}
