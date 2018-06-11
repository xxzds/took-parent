package com.tooklili.dao.redis;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

/**
 * redis公共存储操作
 * @author shuai.ding
 * @date: 2018年6月11日 下午7:39:53
 */
@Repository
public class RedisCommonRepository {
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisCommonRepository.class);
	private  static StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
	
	@Resource
	private RedisTemplate<?, ?> redisTemplate;
	
	
	/**
	 * 将value存入redis中，生命周期为seconds秒
	 * @param key      键
	 * @param value    值
	 * @param seconds  过期时间
	 * @param dbIndex  数据库索引
	 * @return
	 */
	public boolean setString(String key,String value,long seconds,int dbIndex) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				connection.select(dbIndex);
				connection.setEx(stringRedisSerializer.serialize(key), seconds, stringRedisSerializer.serialize(value));
				LOGGER.info("redis存储string类型，key:{},value:{},seconds:{}，dbIndex:{}",key,value,seconds,dbIndex);
				return true;
			}
		});
	}
	
	
	/**
	 * 通过key获取string类型的value
	 * @param key 键
	 * @param dbIndex  数据库索引
	 * @return
	 */
	public  String getString(String key,int dbIndex) {
		return redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				connection.select(dbIndex);
				byte[] bytes = connection.get(stringRedisSerializer.serialize(key));
				if(bytes==null || bytes.length<=0){
					return null;
				}
				return stringRedisSerializer.deserialize(bytes);
			}
		});
	}

}
