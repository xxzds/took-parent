package com.tooklili.service.biz.tbk;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.taobao.api.ApiException;
import com.taobao.api.domain.NTbkItem;
import com.taobao.api.response.TbkItemGetResponse;
import com.tooklili.convert.tbk.TbkItemConverter;
import com.tooklili.service.biz.api.tbk.TbkApiService;
import com.tooklili.util.result.PageResult;
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
	
	public PageResult<TbkItemRespVo> getItems(TbkItemReqVo tbkItemReqVo) throws ApiException{
		PageResult<TbkItemRespVo> result = new PageResult<TbkItemRespVo>(tbkItemReqVo.getPageNo(), tbkItemReqVo.getPageSize());
		
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
}
