package com.tooklili.service.biz.intf.taobao;

import java.util.List;

import com.taobao.api.ApiException;
import com.taobao.api.request.JuItemsSearchRequest.TopItemQuery;
import com.taobao.api.request.TbkCouponGetRequest;
import com.taobao.api.request.TbkDgItemCouponGetRequest;
import com.taobao.api.request.TbkDgMaterialOptionalRequest;
import com.taobao.api.request.TbkDgOptimusMaterialRequest;
import com.taobao.api.request.TbkItemGetRequest;
import com.taobao.api.request.TbkItemInfoGetRequest;
import com.taobao.api.request.TbkItemRecommendGetRequest;
import com.taobao.api.request.TbkJuTqgGetRequest;
import com.taobao.api.request.TbkShopGetRequest;
import com.taobao.api.request.TbkShopRecommendGetRequest;
import com.taobao.api.request.TbkSpreadGetRequest.TbkSpreadRequest;
import com.taobao.api.request.TbkTpwdCreateRequest;
import com.taobao.api.request.TbkUatmEventGetRequest;
import com.taobao.api.request.TbkUatmEventItemGetRequest;
import com.taobao.api.request.TbkUatmFavoritesGetRequest;
import com.taobao.api.request.TbkUatmFavoritesItemGetRequest;
import com.taobao.api.response.JuItemsSearchResponse;
import com.taobao.api.response.TbkCouponGetResponse;
import com.taobao.api.response.TbkDgItemCouponGetResponse;
import com.taobao.api.response.TbkDgMaterialOptionalResponse;
import com.taobao.api.response.TbkDgOptimusMaterialResponse;
import com.taobao.api.response.TbkItemGetResponse;
import com.taobao.api.response.TbkItemInfoGetResponse;
import com.taobao.api.response.TbkItemRecommendGetResponse;
import com.taobao.api.response.TbkJuTqgGetResponse;
import com.taobao.api.response.TbkShopGetResponse;
import com.taobao.api.response.TbkShopRecommendGetResponse;
import com.taobao.api.response.TbkSpreadGetResponse;
import com.taobao.api.response.TbkTpwdCreateResponse;
import com.taobao.api.response.TbkUatmEventGetResponse;
import com.taobao.api.response.TbkUatmEventItemGetResponse;
import com.taobao.api.response.TbkUatmFavoritesGetResponse;
import com.taobao.api.response.TbkUatmFavoritesItemGetResponse;
import com.tooklili.util.PropertiesUtil;

/**
 * 淘宝客接口服务
 * @author shuai.ding
 *
 * @date 2017年6月3日下午4:45:45
 */
public interface TbkApiService {
	
	public final String url = PropertiesUtil.getInstance("tbk.properties").getValue("tbk.url");
	public final String appkey = PropertiesUtil.getInstance("tbk.properties").getValue("tbk.appkey");
	public final String secret = PropertiesUtil.getInstance("tbk.properties").getValue("tbk.secret");
	
	public final String gcAppkey = PropertiesUtil.getInstance("tbk.properties").getValue("gc.tbk.appkey");
	public final String gcSecret = PropertiesUtil.getInstance("tbk.properties").getValue("gc.tbk.secret");

	/**
	 * 淘宝客商品查询
	 * @author shuai.ding
	 * @param req
	 * @return
	 * @throws ApiException
	 */
	public TbkItemGetResponse getItem(TbkItemGetRequest req) throws ApiException;
	

	/**
	 * 淘宝客商品关联推荐查询
	 * @author shuai.ding
	 * @param req
	 * @return
	 * @throws ApiException
	 */
	public TbkItemRecommendGetResponse getRecommendItem(TbkItemRecommendGetRequest req) throws ApiException;
	
	/**
	 * 淘宝客商品详情（简版）
	 * @author shuai.ding
	 * @param req
	 * @return
	 * @throws ApiException
	 */
	public TbkItemInfoGetResponse getInfo(TbkItemInfoGetRequest req) throws ApiException;
	
	/**
	 * 淘宝客店铺查询
	 * @author shuai.ding
	 * @param req
	 * @return
	 * @throws ApiException
	 */
	public TbkShopGetResponse getShop(TbkShopGetRequest req) throws ApiException;
	
	/**
	 * 淘宝客店铺关联推荐查询
	 * @author shuai.ding
	 * @param req
	 * @return
	 * @throws ApiException
	 */
	public TbkShopRecommendGetResponse getRecommendShop(TbkShopRecommendGetRequest req) throws ApiException;
	
	/**
	 * 枚举正在进行中的定向招商的活动列表
	 * @author shuai.ding
	 * @param req
	 * @return
	 * @throws ApiException
	 */
	public TbkUatmEventGetResponse getEventUatm(TbkUatmEventGetRequest req) throws ApiException;
	
	/**
	 * 获取淘宝联盟定向招商的宝贝信息
	 * @author shuai.ding
	 * @param req
	 * @return
	 * @throws ApiException
	 */
	public TbkUatmEventItemGetResponse getItemEventUatm(TbkUatmEventItemGetRequest req) throws ApiException;
	
	/**
	 * 获取淘宝联盟选品库的宝贝信息
	 * @author shuai.ding
	 * @param req
	 * @return
	 * @throws ApiException
	 */
	public TbkUatmFavoritesItemGetResponse getItemFavoritesUatm(TbkUatmFavoritesItemGetRequest req) throws ApiException;
	
	/**
	 * 获取淘宝联盟选品库列表
	 * @author shuai.ding
	 * @param req
	 * @return
	 * @throws ApiException
	 */
	public TbkUatmFavoritesGetResponse getFavoritesUatm(TbkUatmFavoritesGetRequest req) throws ApiException;
	
	/**
	 * 淘抢购api
	 * 获取淘抢购的数据，淘客商品转淘客链接，非淘客商品输出普通链接
	 * @author shuai.ding
	 * @param req
	 * @return
	 * @throws ApiException
	 */
	public TbkJuTqgGetResponse getTqgJu(TbkJuTqgGetRequest req) throws ApiException;

	/**
	 * 物料传播方式获取
	 * 输入一个原始的链接，转换得到指定的传播方式，如二维码，淘口令，短连接； 现阶段只支持短连接。
	 * @author shuai.ding
	 * @param list
	 * @return
	 * @throws ApiException
	 */
	public TbkSpreadGetResponse getSpread(List<TbkSpreadRequest> list) throws ApiException;
	
	/**
	 * 聚划算商品搜索接口
	 * @author shuai.ding
	 * @param topItemQuery
	 * @return
	 * @throws ApiException
	 */
	public JuItemsSearchResponse searchItemsJu(TopItemQuery topItemQuery) throws ApiException;
	
	/**
	 * 好券清单API【导购】
	 * @author shuai.ding
	 * @param req
	 * @return
	 * @throws ApiException
	 */
	public TbkDgItemCouponGetResponse getCouponItem(TbkDgItemCouponGetRequest req) throws ApiException;
	
	/**
	 * 阿里妈妈推广券信息查询
	 * @author shuai.ding
	 * @param req
	 * @return
	 * @throws ApiException
	 */
	public TbkCouponGetResponse getCoupon(TbkCouponGetRequest req) throws ApiException;
	
	/**
	 * 淘宝客淘口令
	 * 提供淘客生成淘口令接口，淘客提交口令内容、logo、url等参数，生成淘口令关键key如：￥SADadW￥，后续进行文案包装组装用于传播
	 * @author shuai.ding
	 * @param req
	 * @param userFlag  用户标识 1、ds  2、gc
	 * @return
	 * @throws ApiException
	 */
	public TbkTpwdCreateResponse createTpwd(TbkTpwdCreateRequest req,Integer userFlag) throws ApiException;
	
	/**
	 *淘宝客物料下行-导购
	 *通用物料推荐，传入官方公布的物料id，可获取指定物料
	 * @param req
	 * @return
	 * @throws ApiException 
	 */
	TbkDgOptimusMaterialResponse getDgOptimusMaterial(TbkDgOptimusMaterialRequest req) throws ApiException;
	
	/**
	 * 通用物料搜索API（导购）
	 * @param req
	 * @return
	 * @throws ApiException
	 */
	TbkDgMaterialOptionalResponse getDgMaterialOptional(TbkDgMaterialOptionalRequest req) throws ApiException;
}
