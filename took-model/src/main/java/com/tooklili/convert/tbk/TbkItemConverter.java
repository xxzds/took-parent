package com.tooklili.convert.tbk;

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
		tbkItemGetRequest.setPageNo(tbkItemReqVo.getPageNo());
		tbkItemGetRequest.setPageSize(tbkItemReqVo.getPageSize());		
		return tbkItemGetRequest;
	}
	
	public static TbkItemRespVo toTbkItemRespVo(NTbkItem nTbkItem){
		if(nTbkItem==null){
			return null;
		}
		TbkItemRespVo tbkItemRespVo = new TbkItemRespVo();
		tbkItemRespVo.setNumIid(nTbkItem.getNumIid());
		tbkItemRespVo.setItemUrl(nTbkItem.getItemUrl());
		tbkItemRespVo.setClickUrl(nTbkItem.getClickUrl());
		
		return tbkItemRespVo;
	}
}
