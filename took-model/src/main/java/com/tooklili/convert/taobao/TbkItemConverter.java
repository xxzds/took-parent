package com.tooklili.convert.taobao;

import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;

import com.taobao.api.domain.NTbkItem;
import com.taobao.api.request.TbkItemGetRequest;
import com.tooklili.vo.tbk.TbkItemReqVo;
import com.tooklili.vo.tbk.TbkItemRespVo;

/**
 * 
 * @author ding.shuai
 * @date 2017年8月5日下午6:21:50
 */
public class TbkItemConverter {
	
	public static TbkItemGetRequest toTbkItemGetRequest(TbkItemReqVo tbkItemReqVo){
		if(tbkItemReqVo==null){
			return null;
		}
		TbkItemGetRequest tbkItemGetRequest = new TbkItemGetRequest();
		tbkItemGetRequest.setQ(tbkItemReqVo.getItemName());
		tbkItemGetRequest.setCat(tbkItemReqVo.getItemCate());
		tbkItemGetRequest.setPageNo(Long.valueOf(tbkItemReqVo.getPageNo()));
		tbkItemGetRequest.setPageSize(Long.valueOf(tbkItemReqVo.getPageSize()));
		//按销量排序
		tbkItemGetRequest.setSort("total_sales");
		return tbkItemGetRequest;
	}
	
	public static TbkItemRespVo toTbkItemRespVo(NTbkItem nTbkItem){
		if(nTbkItem==null){
			return null;
		}
		TbkItemRespVo tbkItemRespVo = new TbkItemRespVo();
		tbkItemRespVo.setNumIid(nTbkItem.getNumIid());
		tbkItemRespVo.setItemUrl(nTbkItem.getItemUrl());
		tbkItemRespVo.setPictUrl(nTbkItem.getPictUrl());
		tbkItemRespVo.setSmallImages(nTbkItem.getSmallImages());
		tbkItemRespVo.setTitle(nTbkItem.getTitle());
		tbkItemRespVo.setZkFinalPrice(nTbkItem.getZkFinalPrice());
		tbkItemRespVo.setReservePrice(nTbkItem.getReservePrice());
		
		//销量格式化，大于10000，格式化成xx.x万
		Long volume = nTbkItem.getVolume();
		if(volume != null){
			if(volume>10000){
				tbkItemRespVo.setVolume(new DecimalFormat("#.#").format(volume / 10000.0)+"万");				
			}else{
				tbkItemRespVo.setVolume(String.valueOf(volume));
			}			
		}
		
		//折扣率
		if(StringUtils.isNotEmpty(nTbkItem.getReservePrice()) && StringUtils.isNotEmpty(nTbkItem.getZkFinalPrice())){
			double reservePrice = Double.parseDouble(nTbkItem.getReservePrice());
			double zkFinalPrice = Double.parseDouble(nTbkItem.getZkFinalPrice());		
			//小数最多显示一位
			tbkItemRespVo.setDiscountRate(new DecimalFormat("#.#").format((zkFinalPrice / reservePrice ) * 10));
		}
		return tbkItemRespVo;
	}
}
