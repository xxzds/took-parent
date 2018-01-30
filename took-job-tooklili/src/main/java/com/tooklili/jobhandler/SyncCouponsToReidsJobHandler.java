package com.tooklili.jobhandler;

import java.util.Collections;
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
import com.google.common.collect.Lists;
import com.tooklili.dao.db.intf.tooklili.ItemDao;
import com.tooklili.enums.tooklili.ItemCateEnum;
import com.tooklili.model.tooklili.Item;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;

/**
 * 同步优惠券商品到redis缓存中
 * @author ding.shuai
 * @date 2017年10月22日下午11:08:13
 */
@JobHander(value="syncCouponsToReidsJobHandler")
@Service
public class SyncCouponsToReidsJobHandler extends IJobHandler{
private static final Logger LOGGER = LoggerFactory.getLogger(SyncCouponsToReidsJobHandler.class);
	
	@Resource
	private ItemDao itemDao;
	
	@Resource
	private RedisTemplate<?, ?> redisTemplate;
	
	private  static StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

	@Override
	public ReturnT<String> execute(String... arg0) throws Exception {
		try{
			for(ItemCateEnum itemCateEnum:ItemCateEnum.values()){
			 Long count = saveToRedis(itemCateEnum);
			 LOGGER.info("类别为【{}】的优惠券商品存入redis缓存中成功，存入个数为：{}",itemCateEnum.getName(),count);
			}	
			return ReturnT.SUCCESS;
		}catch(Exception e){
			LOGGER.error("exception:",e);
			return ReturnT.FAIL;
		}		
	}
	
	/**
	 * 从数据库中查询商品集合，存入redis中，key为枚举名称
	 * @author shuai.ding
	 * @param itemCateEnum   枚举类
	 */
	private Long saveToRedis(final ItemCateEnum itemCateEnum){
		//从数据库中查询指定商品
		final List<Item> items = itemDao.queryItemsByCateId(itemCateEnum.getCode());
		
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				List<byte[]> list = Lists.newArrayList(); 
				for(Item item:items){
					byte[] bytes =stringRedisSerializer.serialize(JSON.toJSONString(item));
					list.add(bytes);
				}
				byte[] key = stringRedisSerializer.serialize(itemCateEnum.getName());
				//清除缓存数据
				connection.del(key);
				
				//此处说明一下，lpush插入的数据，会直接将数据放到集合的头部，故此处需要将集合的顺序倒置一下				
				//逆序排列
				Collections.reverse(list);
				return connection.lPush(key, list.toArray(new byte[list.size()][]));
			}
		});
	}
}
