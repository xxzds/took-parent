package com.tooklili.service.jobhandler;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.taobao.api.ApiException;
import com.taobao.api.request.TbkDgItemCouponGetRequest;
import com.taobao.api.response.TbkDgItemCouponGetResponse.TbkCoupon;
import com.tooklili.model.taobao.KeyWordAndCateModel;
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
	private RedisTemplate<?,?> redisTemplate; 
	
	private final Integer PAGEMAX=100;
	private  static StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
	private final String key = "tbk_api_keyword";
	
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
		KeyWordAndCateModel keyWordAndCateModel = redisTemplate.execute(new RedisCallback<KeyWordAndCateModel>() {
			@Override
			public KeyWordAndCateModel doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = stringRedisSerializer.serialize(key);
				//移除并返回集合中的一个随机元素
				byte[] valueByte = connection.sPop(keyByte);		 
				 KeyWordAndCateModel keyWordAndCateModel = JSON.parseObject(stringRedisSerializer.deserialize(valueByte), KeyWordAndCateModel.class);
				 LOGGER.info(JSON.toJSONString(keyWordAndCateModel));
				 
				 //深拷贝
				 KeyWordAndCateModel keyWordAndCateModelNew = keyWordAndCateModel.clone();
				 
				 //添加新的数据
				 Integer currentPage = keyWordAndCateModelNew.getCurrentPage() < PAGEMAX ? (keyWordAndCateModelNew.getCurrentPage()+1) : 1;
				 keyWordAndCateModelNew.setCurrentPage(currentPage);
				 connection.sAdd(keyByte, stringRedisSerializer.serialize(JSON.toJSONString(keyWordAndCateModelNew)));
				 
				 return keyWordAndCateModel;
			}
		});
		
		//调用淘宝客采券接口
		TbkDgItemCouponGetRequest req = new TbkDgItemCouponGetRequest();
		req.setPageNo((long)keyWordAndCateModel.getCurrentPage());
		req.setPageSize(1L);
		req.setQ(keyWordAndCateModel.getKeyWord());
		LOGGER.info("选择的关键词：{}，采集第{}页",keyWordAndCateModel.getKeyWord(),keyWordAndCateModel.getCurrentPage());
		PageResult<TbkCoupon> result = tbkService.getCouponItems(req);
		LOGGER.info("调用tbk采券接口,返回的内容：{}",JsonFormatTool.formatJson(JSON.toJSONString(result)));
		
		List<TbkCoupon> tbkCoupons = result.getData();
		if(tbkCoupons==null || tbkCoupons.size()==0){
			LOGGER.info("通过关键词[{}]查询第{}页的商品没有查到",keyWordAndCateModel.getKeyWord(),keyWordAndCateModel.getCurrentPage());
			return;
		}		
		TbkCoupon tbkCoupon = tbkCoupons.get(0);
		
		itemOperService.insertOrUpdate(tbkCoupon, keyWordAndCateModel.getCate());
	}
	
}
