package com.tooklili.service.api.tbk;

import java.util.List;

import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.JuItemsSearchRequest;
import com.taobao.api.request.JuItemsSearchRequest.TopItemQuery;
import com.taobao.api.request.TbkDgItemCouponGetRequest;
import com.taobao.api.request.TbkItemGetRequest;
import com.taobao.api.request.TbkItemInfoGetRequest;
import com.taobao.api.request.TbkItemRecommendGetRequest;
import com.taobao.api.request.TbkJuTqgGetRequest;
import com.taobao.api.request.TbkShopGetRequest;
import com.taobao.api.request.TbkShopRecommendGetRequest;
import com.taobao.api.request.TbkSpreadGetRequest;
import com.taobao.api.request.TbkUatmEventGetRequest;
import com.taobao.api.request.TbkUatmEventItemGetRequest;
import com.taobao.api.request.TbkUatmFavoritesGetRequest;
import com.taobao.api.request.TbkUatmFavoritesItemGetRequest;
import com.taobao.api.request.TbkSpreadGetRequest.TbkSpreadRequest;
import com.taobao.api.response.JuItemsSearchResponse;
import com.taobao.api.response.TbkDgItemCouponGetResponse;
import com.taobao.api.response.TbkItemGetResponse;
import com.taobao.api.response.TbkItemInfoGetResponse;
import com.taobao.api.response.TbkItemRecommendGetResponse;
import com.taobao.api.response.TbkJuTqgGetResponse;
import com.taobao.api.response.TbkShopGetResponse;
import com.taobao.api.response.TbkShopRecommendGetResponse;
import com.taobao.api.response.TbkSpreadGetResponse;
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
@Service
public class TbkApiService {
	
	private String url = PropertiesUtil.getInstance("tbk.properties").getValue("tbk.url");
	private String appkey = PropertiesUtil.getInstance("tbk.properties").getValue("tbk.appkey");
	private String secret = PropertiesUtil.getInstance("tbk.properties").getValue("tbk.secret");

	/**
	 * 淘宝客商品查询
	 * @author shuai.ding
	 * @param req
	 * @return
	 * @throws ApiException
	 */
	public TbkItemGetResponse getItem(TbkItemGetRequest req) throws ApiException{
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		req.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick");
		TbkItemGetResponse rsp = client.execute(req);
		return rsp;
	}
	

	/**
	 * 淘宝客商品关联推荐查询
	 * @author shuai.ding
	 * @param req
	 * @return
	 * @throws ApiException
	 */
	public TbkItemRecommendGetResponse getRecommendItem(TbkItemRecommendGetRequest req) throws ApiException{
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		req.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url");
		TbkItemRecommendGetResponse rsp = client.execute(req);
		return rsp;
	}
	
	/**
	 * 淘宝客商品详情（简版）
	 * @author shuai.ding
	 * @param req
	 * @return
	 * @throws ApiException
	 */
	public TbkItemInfoGetResponse getInfo(TbkItemInfoGetRequest req) throws ApiException{
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		req.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url");
		TbkItemInfoGetResponse rsp = client.execute(req);
		return rsp;
	}
	
	/**
	 * 淘宝客店铺查询
	 * @author shuai.ding
	 * @param req
	 * @return
	 * @throws ApiException
	 */
	public TbkShopGetResponse getShop(TbkShopGetRequest req) throws ApiException{
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		req.setFields("user_id,shop_title,shop_type,seller_nick,pict_url,shop_url");
		TbkShopGetResponse rsp = client.execute(req);
		return rsp;
	}
	
	/**
	 * 淘宝客店铺关联推荐查询
	 * @author shuai.ding
	 * @param req
	 * @return
	 * @throws ApiException
	 */
	public TbkShopRecommendGetResponse getRecommendShop(TbkShopRecommendGetRequest req) throws ApiException{
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		req.setFields("user_id,shop_title,shop_type,seller_nick,pict_url,shop_url");
		TbkShopRecommendGetResponse rsp = client.execute(req);
		return rsp;
	}
	
	/**
	 * 枚举正在进行中的定向招商的活动列表
	 * @author shuai.ding
	 * @param req
	 * @return
	 * @throws ApiException
	 */
	public TbkUatmEventGetResponse getEventUatm(TbkUatmEventGetRequest req) throws ApiException{
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		req.setFields("event_id,event_title,start_time,end_time");
		TbkUatmEventGetResponse rsp = client.execute(req);
		return rsp;
	}
	
	/**
	 * 获取淘宝联盟定向招商的宝贝信息
	 * @author shuai.ding
	 * @param req
	 * @return
	 * @throws ApiException
	 */
	public TbkUatmEventItemGetResponse getItemEventUatm(TbkUatmEventItemGetRequest req) throws ApiException{
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		req.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick,shop_title,zk_final_price_wap,event_start_time,event_end_time,tk_rate,type,status");
		TbkUatmEventItemGetResponse rsp = client.execute(req);
		return rsp;
	}
	
	/**
	 * 获取淘宝联盟选品库的宝贝信息
	 * @author shuai.ding
	 * @param req
	 * @return
	 * @throws ApiException
	 */
	public TbkUatmFavoritesItemGetResponse getItemFavoritesUatm(TbkUatmFavoritesItemGetRequest req) throws ApiException{
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		req.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick,shop_title,zk_final_price_wap,event_start_time,event_end_time,tk_rate,status,type");
		TbkUatmFavoritesItemGetResponse rsp = client.execute(req);
		return rsp;
	}
	
	/**
	 * 获取淘宝联盟选品库列表
	 * @author shuai.ding
	 * @param req
	 * @return
	 * @throws ApiException
	 */
	public TbkUatmFavoritesGetResponse getFavoritesUatm(TbkUatmFavoritesGetRequest req) throws ApiException{
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		req.setFields("favorites_title,favorites_id,type");
		req.setType(1L);
		TbkUatmFavoritesGetResponse rsp = client.execute(req);
		return rsp;
	}
	
	/**
	 * 淘抢购api
	 * 获取淘抢购的数据，淘客商品转淘客链接，非淘客商品输出普通链接
	 * @author shuai.ding
	 * @param req
	 * @return
	 * @throws ApiException
	 */
	public TbkJuTqgGetResponse getTqgJu(TbkJuTqgGetRequest req) throws ApiException{
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		req.setFields("click_url,pic_url,reserve_price,zk_final_price,total_amount,sold_num,title,category_name,start_time,end_time");
		TbkJuTqgGetResponse rsp = client.execute(req);
		return rsp;
	}
	

	/**
	 * 物料传播方式获取
	 * 输入一个原始的链接，转换得到指定的传播方式，如二维码，淘口令，短连接； 现阶段只支持短连接。
	 * @author shuai.ding
	 * @param list
	 * @return
	 * @throws ApiException
	 */
	public TbkSpreadGetResponse getSpread(List<TbkSpreadRequest> list) throws ApiException{
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		TbkSpreadGetRequest req = new TbkSpreadGetRequest();
		req.setRequests(list);
		TbkSpreadGetResponse rsp = client.execute(req);
		return rsp;
	}
	
	/**
	 * 聚划算商品搜索接口
	 * @author shuai.ding
	 * @param topItemQuery
	 * @return
	 * @throws ApiException
	 */
	public JuItemsSearchResponse searchItemsJu(TopItemQuery topItemQuery) throws ApiException{
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		JuItemsSearchRequest req = new JuItemsSearchRequest();
		req.setParamTopItemQuery(topItemQuery);
		JuItemsSearchResponse rsp = client.execute(req);
		return rsp;
	}
	
	/**
	 * 好券清单API【导购】
	 * @author shuai.ding
	 * @param req
	 * @return
	 * @throws ApiException
	 */
	public TbkDgItemCouponGetResponse getCouponItem(TbkDgItemCouponGetRequest req) throws ApiException{
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		TbkDgItemCouponGetResponse rsp = client.execute(req);
		return rsp;
	}
}
