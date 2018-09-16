package com.tooklili.service.biz.impl.taobao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.taobao.api.ApiException;
import com.taobao.api.domain.NTbkItem;
import com.taobao.api.request.TbkCouponGetRequest;
import com.taobao.api.request.TbkDgItemCouponGetRequest;
import com.taobao.api.request.TbkDgMaterialOptionalRequest;
import com.taobao.api.request.TbkDgOptimusMaterialRequest;
import com.taobao.api.request.TbkItemInfoGetRequest;
import com.taobao.api.request.TbkTpwdCreateRequest;
import com.taobao.api.response.TbkCouponGetResponse;
import com.taobao.api.response.TbkCouponGetResponse.MapData;
import com.taobao.api.response.TbkDgItemCouponGetResponse;
import com.taobao.api.response.TbkDgItemCouponGetResponse.TbkCoupon;
import com.taobao.api.response.TbkDgMaterialOptionalResponse;
import com.taobao.api.response.TbkDgOptimusMaterialResponse;
import com.taobao.api.response.TbkItemGetResponse;
import com.taobao.api.response.TbkItemInfoGetResponse;
import com.taobao.api.response.TbkTpwdCreateResponse;
import com.tooklili.convert.taobao.TbkItemConverter;
import com.tooklili.convert.taobao.TbkItemDetailConverter;
import com.tooklili.model.tooklili.Item;
import com.tooklili.service.biz.intf.taobao.TbkApiService;
import com.tooklili.service.biz.intf.taobao.TbkService;
import com.tooklili.util.Arith;
import com.tooklili.util.PropertiesUtil;
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
@Service
public class TbkServiceImpl implements TbkService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TbkServiceImpl.class);
	
	@Resource
	private TbkApiService tbkApiService;
	
	/**
	 * 商品列表
	 * @author shuai.ding
	 * @param tbkItemReqVo
	 * @return
	 * @throws ApiException
	 */
	public PageResult<TbkItemRespVo> getItems(TbkItemReqVo tbkItemReqVo) throws ApiException{
		PageResult<TbkItemRespVo> result = new PageResult<TbkItemRespVo>(tbkItemReqVo.getPageNo(), tbkItemReqVo.getPageSize());
		
		if(StringUtils.isEmpty(tbkItemReqVo.getItemName()) && StringUtils.isEmpty(tbkItemReqVo.getItemCate())){
			return result.setErrorMessage("商品名称和分类不能全部为空");
		}
		
		TbkItemGetResponse  tbkItemGetResponse = tbkApiService.getItem(TbkItemConverter.toTbkItemGetRequest(tbkItemReqVo));
		
		result.setTotalCountLong(tbkItemGetResponse.getTotalResults());
		List<NTbkItem> ntbkItems = tbkItemGetResponse.getResults();
		
		if(ntbkItems!=null && ntbkItems.size()>0){
			result.setData(Lists.transform(ntbkItems, new Function<NTbkItem, TbkItemRespVo>() {
				@Override
				public TbkItemRespVo apply(NTbkItem input) {					
					return TbkItemConverter.toTbkItemRespVo(input);
				}
			}));
		}	
		return result;
	}
	
	/**
	 * 通过商品id，查询商品信息
	 * @author shuai.ding
	 * @param numIid
	 * @return
	 * @throws ApiException
	 */
	public PlainResult<TbkItemDetailRespVo> getItemDetail(String numIid) throws ApiException{
		PlainResult<TbkItemDetailRespVo> result = new PlainResult<TbkItemDetailRespVo>();
		
		TbkItemInfoGetRequest req = new TbkItemInfoGetRequest();
		req.setNumIids(numIid);
		
		TbkItemInfoGetResponse tbkItemInfoGetResponse = tbkApiService.getInfo(req);
		List<com.taobao.api.response.TbkItemInfoGetResponse.NTbkItem> ntbkItems = tbkItemInfoGetResponse.getResults();
		
		if(ntbkItems!=null && ntbkItems.size()>0){
			result.setData(TbkItemDetailConverter.toTbkItemDetailRespVo(ntbkItems.get(0)));
		}			
		return result;
	}
	
	
	/**
	 * 获取好券清单列表
	 * @author shuai.ding
	 * @param req
	 * @return
	 * @throws ApiException
	 */
	public PageResult<TbkCoupon> getCouponItems(TbkDgItemCouponGetRequest req) throws ApiException{		
		if(req.getPageNo()==null){
			req.setPageNo(1L);
		}
		if(req.getPageSize()==null){
			req.setPageSize(20L);
		}
		
		/**
		 * 设置推广者标识
		 */
		if(req.getAdzoneId()==null){
			String tbkPid = PropertiesUtil.getInstance("tbk.properties").getValue("tbk.pid");		
			String[] ids = tbkPid.split("_");
			req.setAdzoneId(Long.parseLong(ids[ids.length-1]));
		}
		PageResult<TbkCoupon> result = new PageResult<TbkCoupon>(req.getPageNo(), req.getPageSize());
		TbkDgItemCouponGetResponse tbkDgItemCouponGetResponse = tbkApiService.getCouponItem(req);
		
		result.setTotalCountLong(tbkDgItemCouponGetResponse.getTotalResults());
		result.setData(tbkDgItemCouponGetResponse.getResults());
		return result;
	}
	
	/**
	 * 获取好券清单列表
	 * @author shuai.ding
	 * @param req
	 * @return
	 * @throws ApiException
	 */
	public PageResult<Item> getCouponItemsReturnItems(TbkDgItemCouponGetRequest req) throws ApiException{		
		if(req.getPageNo()==null){
			req.setPageNo(1L);
		}
		if(req.getPageSize()==null){
			req.setPageSize(20L);
		}
		
		/**
		 * 设置推广者标识
		 */
		if(req.getAdzoneId()==null){
			String tbkPid = PropertiesUtil.getInstance("tbk.properties").getValue("tbk.pid");		
			String[] ids = tbkPid.split("_");
			req.setAdzoneId(Long.parseLong(ids[ids.length-1]));
		}
		PageResult<Item> result = new PageResult<Item>(req.getPageNo(), req.getPageSize());
		TbkDgItemCouponGetResponse tbkDgItemCouponGetResponse = tbkApiService.getCouponItem(req);
		
		result.setTotalCountLong(tbkDgItemCouponGetResponse.getTotalResults());
				
		List<Item> list = Lists.transform(tbkDgItemCouponGetResponse.getResults(), new Function<TbkCoupon, Item>() {
			@Override
			public Item apply(TbkCoupon input) {
				return TbkItemConverter.convertItem(input);
			}
		});
		
		result.setData(list);
		return result;
	}
	
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
	public PlainResult<String> createTpwd(String text,String url,String logo,Integer userFlag) throws ApiException{
		PlainResult<String> result = new PlainResult<String>();
		
		if(StringUtils.isEmpty(text)){
			return result.setErrorMessage("口令弹框内容不能为空");
		}
		if(text.length()<5){
			return result.setErrorMessage("口令弹框内容不能少于5个字符 ");
		}
		if(StringUtils.isEmpty(url)){
			return result.setErrorMessage("口令跳转目标页不能为空");
		}
				
		TbkTpwdCreateRequest req = new TbkTpwdCreateRequest();
		req.setText(text);
		req.setUrl(url);
		if(StringUtils.isNotEmpty(logo)){
			req.setLogo(logo);
		}
		TbkTpwdCreateResponse rsp = tbkApiService.createTpwd(req,userFlag);
		
		if(StringUtils.isNotEmpty(rsp.getErrorCode())){
			LOGGER.info("调用获取淘口令接口失败，失败原因：{}",rsp.getSubMsg());
			return result.setErrorMessage(rsp.getSubMsg());
		}		
		result.setData(rsp.getData().getModel());
		return result;
		
	}
	
	/**
	 * 阿里妈妈推广券信息查询
	 * @author shuai.ding
	 * @param me   带券ID与商品ID的加密串
	 * @return
	 */
	public PlainResult<MapData> getCouponInfo(String me) throws ApiException{
		PlainResult<MapData> result = new PlainResult<MapData>();
		if(StringUtils.isEmpty(me)){
			return result.setErrorMessage("带券ID与商品ID的加密串的参数不能为空");
		}
		LOGGER.info("带券ID与商品ID的加密串:{}",me);
		
		TbkCouponGetRequest req = new TbkCouponGetRequest();
		req.setMe(me);
		TbkCouponGetResponse tbkCouponGetResponse = tbkApiService.getCoupon(req);
		LOGGER.info("响应内容：{}",JSON.toJSONString(tbkCouponGetResponse));
		result.setData(tbkCouponGetResponse.getData());
		return result;
	}

	@Override
	public ListResult<Item> getItems(Long materialId, Integer currentPage, Integer pageSize) throws ApiException {
		ListResult<Item> result = new ListResult<Item>();
		
		if(materialId == null) {
			return result.setErrorMessage("物料id不能为空");
		}
		
		String pid = PropertiesUtil.getInstance("tbk.properties").getValue("tbk.pid");
		String[] temp = pid.split("_");
		String adzoneid = temp[3];
		
		TbkDgOptimusMaterialRequest req = new TbkDgOptimusMaterialRequest();
		req.setAdzoneId(Long.parseLong(adzoneid));
		req.setPageNo((long)currentPage);
		req.setPageSize((long)pageSize);
		req.setMaterialId(materialId);
		
		TbkDgOptimusMaterialResponse resp = tbkApiService.getDgOptimusMaterial(req);
		LOGGER.info("响应内容：{}",JSON.toJSONString(resp));
		
		if(StringUtils.isNotEmpty(resp.getErrorCode())) {
			if("15".equals(resp.getErrorCode())) {  //无结果
				return result;
			}else {
				return result.setErrorMessage(resp.getSubMsg());
			}
		}
		
		//有结果
		List<com.taobao.api.response.TbkDgOptimusMaterialResponse.MapData> mapDatas = resp.getResultList();	
		List<Item> items = Lists.transform(mapDatas, new Function<com.taobao.api.response.TbkDgOptimusMaterialResponse.MapData, Item>() {

			@Override
			public Item apply(com.taobao.api.response.TbkDgOptimusMaterialResponse.MapData input) {				
				return TbkItemConverter.convertItem(input);
			}
		});
		result.setData(items);		
		return result;
	}

	@Override
	public PageResult<Item> getItems(TbkDgMaterialOptionalRequest req) throws ApiException {
	    PageResult<Item> result = new PageResult<>();
	    
	    if(req == null || (StringUtils.isEmpty(req.getQ()) && StringUtils.isEmpty(req.getCat()))){
	    		return result.setErrorMessage("参数q与cat不能都为空");
	    }
	    
	    if(req.getPageNo() == null) {
	    		req.setPageNo(1L);
	    }
	    if(req.getPageSize() == null){
	    	    req.setPageSize(20L);
	    }
	    
	    String pid = PropertiesUtil.getInstance("tbk.properties").getValue("tbk.pid");
		String[] temp = pid.split("_");
		String adzoneid = temp[3];
		req.setAdzoneId(Long.parseLong(adzoneid));
	    
		TbkDgMaterialOptionalResponse rsp = tbkApiService.getDgMaterialOptional(req);
		
		result.setCurrentPage(req.getPageNo().intValue());
		result.setPageSize(req.getPageSize().intValue());
		result.setTotalCountLong(rsp.getTotalResults() == null ? 0L:rsp.getTotalResults());
		
		
	    List<Item> items = Lists.transform(rsp.getResultList(), new Function<com.taobao.api.response.TbkDgMaterialOptionalResponse.MapData, Item>() {

			@Override
			public Item apply(com.taobao.api.response.TbkDgMaterialOptionalResponse.MapData input) {
				Item item = TbkItemConverter.convertItem(input);
				
				//查询优惠券信息
				String couponAmount = "0";
				if(StringUtils.isNotEmpty(item.getCouponId())) {
					TbkCouponGetRequest req = new TbkCouponGetRequest();
					req.setItemId(item.getNumIid());
					req.setActivityId(item.getCouponId());
					try {
						TbkCouponGetResponse tbkCouponGetResponse = tbkApiService.getCoupon(req);
						couponAmount = tbkCouponGetResponse.getData().getCouponAmount();
					} catch (ApiException e) {
						e.printStackTrace();
					}					
				}
				
				//折扣价
				double couponPrice = Arith.sub(Double.valueOf(item.getPrice()), Double.valueOf(couponAmount));
				item.setCouponPrice(String.valueOf(couponPrice));
				//优惠券
				item.setQuan(couponAmount);
				return item;
			}
		});
		result.setData(items);	
		return result;
	}
}
