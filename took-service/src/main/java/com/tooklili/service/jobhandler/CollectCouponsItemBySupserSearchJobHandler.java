package com.tooklili.service.jobhandler;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

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
import com.tooklili.model.taobao.AlimamaItem;
import com.tooklili.model.taobao.AlimamaReqItemModel;
import com.tooklili.model.taobao.KeyWordAndCateModel;
import com.tooklili.service.biz.intf.taobao.AlimamaService;
import com.tooklili.service.biz.intf.tooklili.ItemOperService;
import com.tooklili.util.JsonFormatTool;
import com.tooklili.util.result.PageResult;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;

/**
 * 通过超级搜索接口，采集优惠券商品
 * @author shuai.ding
 *
 * @date 2017年10月21日上午11:16:50
 */
@JobHander(value="collectCouponsItemBySupserSearchJobHandler")
@Service
public class CollectCouponsItemBySupserSearchJobHandler extends IJobHandler{
	private static final Logger LOGGER = LoggerFactory.getLogger(CollectCouponsItemBySupserSearchJobHandler.class);
	
	@Resource
	private AlimamaService alimamaService;
	
	@Resource(name = "itemEsOperServiceImpl")
	private ItemOperService itemOperService;
	
	@Resource
	private RedisTemplate<?,?> redisTemplate; 
	
	private final Integer PAGEMAX=100;
	
	private  static StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
	
	private final String key = "super_search_keyword";

	@Override
	public ReturnT<String> execute(String... params) throws Exception {
		
		
		//每次调用采集5次
		for(int i=0;i<5;i++){
			this.collectCouponItemBySuperSearch();
			Thread.sleep(20000);
		}
		
		
		return ReturnT.SUCCESS;
	}
	
	private void collectCouponItemBySuperSearch() throws UnsupportedEncodingException, ParseException{
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
		
		
		//调用超级接口
		AlimamaReqItemModel alimamaReqItemModel = new AlimamaReqItemModel();
		
		alimamaReqItemModel.setQ(keyWordAndCateModel.getKeyWord());
		alimamaReqItemModel.setYxjh(1);
		alimamaReqItemModel.setToPage(keyWordAndCateModel.getCurrentPage());
		alimamaReqItemModel.setPerPageSize(1);
		//包含店铺优惠券
		alimamaReqItemModel.setDpyhq(1);
		//天猫
		alimamaReqItemModel.setUserType(1);
		//销量从高到低
		alimamaReqItemModel.setSortType(9);
		PageResult<AlimamaItem> result = alimamaService.superSearchItems(alimamaReqItemModel);
		LOGGER.info(JsonFormatTool.formatJson(JSON.toJSONString(result)));
		
		if(result.getData().size()<=0){
			LOGGER.info("通过关键词[{}]查询第{}页的商品没有查到",keyWordAndCateModel.getKeyWord(),keyWordAndCateModel.getCurrentPage());
			return;
		}		
		AlimamaItem alimamaItem = result.getData().get(0);
		
		itemOperService.insertOrUpdate(alimamaItem, keyWordAndCateModel.getCate());
	}
}
