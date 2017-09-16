package com.tooklili.service.biz.api.tbk;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.taobao.api.ApiException;
import com.taobao.api.domain.NTbkItem;
import com.taobao.api.request.TbkDgItemCouponGetRequest;
import com.taobao.api.request.TbkItemInfoGetRequest;
import com.taobao.api.response.TbkDgItemCouponGetResponse;
import com.taobao.api.response.TbkDgItemCouponGetResponse.TbkCoupon;
import com.taobao.api.response.TbkItemGetResponse;
import com.taobao.api.response.TbkItemInfoGetResponse;
import com.tooklili.convert.tbk.TbkItemConverter;
import com.tooklili.convert.tbk.TbkItemDetailConverter;
import com.tooklili.service.biz.api.tbk.TbkApiService;
import com.tooklili.util.PropertiesUtil;
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
public class TbkService {
	
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
		
		result.setTotalCount(tbkItemGetResponse.getTotalResults());
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
		List<NTbkItem> ntbkItems = tbkItemInfoGetResponse.getResults();
		
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
		
		result.setTotalCount(tbkDgItemCouponGetResponse.getTotalResults());
		result.setData(tbkDgItemCouponGetResponse.getResults());
		return result;
	}
}
