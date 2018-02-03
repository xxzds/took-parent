package com.tooklili.dao.redis;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.tooklili.enums.tooklili.ItemCateEnum;
import com.tooklili.model.tooklili.Item;

/** 
 * @author shuai.ding
 * @date 2018年2月3日下午4:45:42
 */
@Repository
public class RedisItemRepository {
	@Resource
	private RedisTemplate<?, ?> redisTemplate;
	
	private  static StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

	/**
	 * 通过商品类别，查询优惠券列表
	 * @author shuai.ding
	 * @param itemCateEnum    商品分类的枚举类
	 * @param currentPage     当前页
	 * @param pageSize        页面大小
	 * @return
	 */
	public List<Item> queryItemsByCateId(ItemCateEnum itemCateEnum, Long currentPage,Long pageSize){
		final long begin =(currentPage-1)*pageSize;
		final long end = begin+pageSize-1;		
		
		return redisTemplate.execute(new RedisCallback<List<Item>>() {

			@Override
			public List<Item> doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] key = stringRedisSerializer.serialize(itemCateEnum.getName());
				List<byte[]> byteList = connection.lRange(key, begin, end);
				
				List<Item> data =  Lists.transform(byteList, new Function<byte[], Item>() {
					@Override
					public Item apply(byte[] input) {						
						String value = stringRedisSerializer.deserialize(input);
						return JSON.parseObject(value, Item.class);
					}					
				});
				return data;
			}
		});
	}
	
	/**
	 * 查询商品集合总数
	 * @author shuai.ding
	 * @param itemCateEnum   商品分类的枚举类
	 * @param currentPage    当前页
	 * @param pageSize
	 * @return
	 */
	public long countItemsByCateId(ItemCateEnum itemCateEnum){
		return redisTemplate.execute(new RedisCallback<Long>() {
			
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] key = stringRedisSerializer.serialize(itemCateEnum.getName());
				Long count = connection.lLen(key);
				return count;
			}
		});
	}
	
	/**
	 * 通过商品分类随机获取size个商品
	 * @author shuai.ding
	 * @param itemCateEnum  商品分类的枚举类
	 * @param size          随机获取商品的个数
	 * @return
	 */
	public List<Item> randomItemByCateId(ItemCateEnum itemCateEnum, Integer size) {
		final int nLimit = size;
		return redisTemplate.execute(new RedisCallback<List<Item>>() {
			@Override
			public List<Item> doInRedis(RedisConnection connection) throws DataAccessException {
				List<Item> items = Lists.newArrayList();
				byte[] key = stringRedisSerializer.serialize(itemCateEnum.getName());

				// 总数
				Long count = connection.lLen(key);

				for (int i = 0; i < nLimit; i++) {
					Long index = (long) (Math.random() * count);
					byte[] valueByte = connection.lIndex(key, index);
					String value = stringRedisSerializer.deserialize(valueByte);
					items.add(JSON.parseObject(value, Item.class));
				}
				return items;
			}
		});
	}
}
