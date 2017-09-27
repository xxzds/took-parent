package com.tooklili.service.redis;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.tooklili.dao.intf.tooklili.ItemDao;
import com.tooklili.enums.tooklili.ItemCateEnum;
import com.tooklili.model.tooklili.Item;
import com.tooklili.service.BaseTest;

/**
 * redis 操作测试
 * @author shuai.ding
 * @date 2017年9月12日上午10:14:28
 */
public class RedisTest extends BaseTest{
	
	@Resource
	private RedisTemplate<?,?> redisTemplate;
	
	@Resource
	private ItemDao itemDao;
	
	private  static StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
	
	@Test
	public void setItemsToRedis(){
		try{
			
			final ItemCateEnum constume = ItemCateEnum.CONSTUME;
			final List<Item> items = itemDao.queryItemsByCateId(constume.getCode());
			
			redisTemplate.execute(new RedisCallback<Long>() {
				@Override
				public Long doInRedis(RedisConnection connection) throws DataAccessException {
					List<byte[]> list = Lists.newArrayList(); 
					for(Item item:items){
						byte[] bytes =stringRedisSerializer.serialize(JSON.toJSONString(item));
						list.add(bytes);
					}
					return connection.lPush(stringRedisSerializer.serialize(constume.getName()), list.toArray(new byte[list.size()][]));
				}
			});
		}catch(Exception e){
			logger.error("exception:",e);
		}	
	}
	
	/**
	 * 发现一个现象 Lists.transform转化是个懒执行的过程，只有转换的结果被使用，才会执行转化代码
	 * @author shuai.ding
	 */
	@Test
	public void getItemsToRids(){
		try{
			redisTemplate.execute(new RedisCallback<List<Item>>(){
				@Override
				public List<Item> doInRedis(RedisConnection connection) throws DataAccessException {
					byte[] key = stringRedisSerializer.serialize(ItemCateEnum.CONSTUME.getName());
					List<byte[]> byteLists =  connection.lRange(key, 1, 10);
//					List<byte[]> byteLists =  connection.lRange(key, 0, connection.lLen(key));
					
					List<Item> items = Lists.transform(byteLists, new Function<byte[], Item>() {

						@Override
						public Item apply(byte[] input) {
							String value = stringRedisSerializer.deserialize(input);
							logger.info(value);
							return JSON.parseObject(value, Item.class);
						}
					});
					logger.info(JSON.toJSONString(items));
					return items;
				}
				
			});
		}catch(Exception e){
			logger.info("exception",e);
		}
	}
}
