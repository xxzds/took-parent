package com.tooklili.service.tbk;

import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TbkItemGetRequest;
import com.taobao.api.request.TbkItemInfoGetRequest;
import com.taobao.api.response.TbkItemGetResponse;
import com.taobao.api.response.TbkItemInfoGetResponse;
import com.tooklili.util.PropertiesUtil;

/**
 * 淘宝客接口服务
 * @author shuai.ding
 *
 * @date 2017年6月3日下午4:45:45
 */
@Service
public class TbkService {
	
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
}
