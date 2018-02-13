package com.tooklili.service.jobhandler;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.taobao.api.ApiException;
import com.taobao.api.request.TbkDgItemCouponGetRequest;
import com.taobao.api.response.TbkDgItemCouponGetResponse.TbkCoupon;
import com.tooklili.dao.redis.RedisItemCateAndKeywordRepository;
import com.tooklili.enums.admin.ApiTypeEnum;
import com.tooklili.model.taobao.TookKeywordInfo;
import com.tooklili.service.biz.intf.taobao.TbkService;
import com.tooklili.service.biz.intf.tooklili.ItemOperService;
import com.tooklili.util.JsonFormatTool;
import com.tooklili.util.result.PageResult;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;

/**
 * 通过淘宝客采券接口，采集优惠券商品
 * @author ding.shuai
 * @date 2017年10月21日下午7:55:01
 */
@JobHander(value="collectCouponsItemByTbkApiJobHandller")
@Service
public class CollectCouponsItemByTbkApiJobHandller extends IJobHandler{
	private static final Logger LOGGER = LoggerFactory.getLogger(CollectCouponsItemByTbkApiJobHandller.class);

	@Resource
	private TbkService tbkService;
	
	@Resource(name = "itemEsOperServiceImpl")
	private ItemOperService itemOperService;
	
	@Resource
	private RedisItemCateAndKeywordRepository redisItemCateAndKeywordRepository;
	
	@Override
	public ReturnT<String> execute(String... arg0) throws Exception {		
		//每次调用采集10次
		for(int i=0;i<10;i++){
			this.collectCouponItemByTbkApi();
		}
		return ReturnT.SUCCESS;
	}
	
	/**
	 * 调用采券接口，将采集的券存入数据库
	 * @throws ApiException
	 * @throws ParseException
	 */
	private void collectCouponItemByTbkApi() throws ApiException, ParseException{
		//从redis中随机选取关键字
		TookKeywordInfo tookKeywordInfo = redisItemCateAndKeywordRepository.getRandomKeywordInfo(ApiTypeEnum.TBK_API);	
		
		//调用淘宝客采券接口
		TbkDgItemCouponGetRequest req = new TbkDgItemCouponGetRequest();
		req.setPageNo((long)tookKeywordInfo.getCurrentPage());
		req.setPageSize(1L);
		req.setQ(tookKeywordInfo.getKeyword());
		LOGGER.info("选择的关键词：{}，采集第{}页",tookKeywordInfo.getKeyword(),tookKeywordInfo.getCurrentPage());
		PageResult<TbkCoupon> result = tbkService.getCouponItems(req);
		LOGGER.info("调用tbk采券接口,返回的内容：{}",JsonFormatTool.formatJson(JSON.toJSONString(result)));
		
		List<TbkCoupon> tbkCoupons = result.getData();
		if(tbkCoupons==null || tbkCoupons.size()==0){
			LOGGER.info("通过关键词[{}]查询第{}页的商品没有查到",tookKeywordInfo.getKeyword(),tookKeywordInfo.getCurrentPage());
			return;
		}		
		TbkCoupon tbkCoupon = tbkCoupons.get(0);
		
		itemOperService.insertOrUpdate(tbkCoupon, tookKeywordInfo.getCateId());
	}
	
}
