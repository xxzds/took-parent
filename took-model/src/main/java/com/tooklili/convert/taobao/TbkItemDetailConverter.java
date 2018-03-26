package com.tooklili.convert.taobao;

import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;

import com.taobao.api.domain.NTbkItem;
import com.tooklili.vo.tbk.TbkItemDetailRespVo;

/**
 * 
 * @author shuai.ding
 * @date 2017年8月20日上午11:26:43
 */
public class TbkItemDetailConverter {

	public static TbkItemDetailRespVo toTbkItemDetailRespVo(NTbkItem nTbkItem){
		if(nTbkItem==null){
			return null;
		}
		TbkItemDetailRespVo tbkItemDetailRespVo = new TbkItemDetailRespVo();
		tbkItemDetailRespVo.setNumIid(nTbkItem.getNumIid());
		tbkItemDetailRespVo.setItemUrl(nTbkItem.getItemUrl());
		tbkItemDetailRespVo.setPictUrl(nTbkItem.getPictUrl());
		tbkItemDetailRespVo.setSmallImages(nTbkItem.getSmallImages());
		tbkItemDetailRespVo.setTitle(nTbkItem.getTitle());
		tbkItemDetailRespVo.setZkFinalPrice(nTbkItem.getZkFinalPrice());
		tbkItemDetailRespVo.setReservePrice(nTbkItem.getReservePrice());
		
		//销量格式化，大于10000，格式化成xx.x万
		Long volume = nTbkItem.getVolume();
		if(volume != null){
			if(volume>10000){
				tbkItemDetailRespVo.setVolume(new DecimalFormat("#.#").format(volume / 10000.0)+"万");				
			}else{
				tbkItemDetailRespVo.setVolume(String.valueOf(volume));
			}			
		}
		
		//折扣率
		if(StringUtils.isNotEmpty(nTbkItem.getReservePrice()) && StringUtils.isNotEmpty(nTbkItem.getZkFinalPrice())){
			double reservePrice = Double.parseDouble(nTbkItem.getReservePrice());
			double zkFinalPrice = Double.parseDouble(nTbkItem.getZkFinalPrice());		
			//小数最多显示一位
			tbkItemDetailRespVo.setDiscountRate(new DecimalFormat("#.#").format((zkFinalPrice / reservePrice ) * 10));
		}
		return tbkItemDetailRespVo;
	}
}
