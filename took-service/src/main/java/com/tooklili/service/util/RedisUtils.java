package com.tooklili.service.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * reids 工具类
 * @author ding.shuai
 * @date 2017年3月29日下午8:48:37
 */
public class RedisUtils {

	private static RedisTemplate<?, ?> redisTemplate = SpringUtils.getBean("redisTemplate");	
	private  static StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
	private static JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
	
	/**
	 * 删除redis中keys对应的值
	 * @param keys
	 * @return
	 */
	public static Long delByKey(final String... keys){
		if(keys==null || keys.length<=0){
			return 0L;
		}
		
		return redisTemplate.execute(new RedisCallback<Long>() {			
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				List<byte[]> list = Lists.newArrayList(); 
				for(String key:keys){
					byte[] bytes =stringRedisSerializer.serialize(key);
					list.add(bytes);
				}	
				return connection.del(list.toArray(new byte[list.size()][]));
			}
		});
	}
	
	/**
	 * 将value存入redis中，生命周期为seconds秒
	 * @param key
	 * @param value
	 * @param seconds
	 * @return
	 */
	public static boolean setString(final String key,final String value,final long seconds){
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {				
				connection.setEx(stringRedisSerializer.serialize(key), seconds, stringRedisSerializer.serialize(value));
				return true;
			}
		});
	}
	
	/**
	 * 通过key获取string类型的value
	 * @param key
	 * @return
	 */
	public static String getString(final String key){
		return redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] bytes = connection.get(stringRedisSerializer.serialize(key));
				if(bytes==null || bytes.length<=0){
					return null;
				}
				return stringRedisSerializer.deserialize(bytes);
			}
		});
	}
	
	/**
	 * 将field,value存入hash
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public static  boolean setHash(final String key, final String field,final Object value){
    	return redisTemplate.execute(new RedisCallback<Boolean>(){
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {								
				return connection.hSet(stringRedisSerializer.serialize(key), stringRedisSerializer.serialize(field), jdkSerializationRedisSerializer.serialize(value));
			}
    	});
    }
	
	
	/**
	 * 获取hash集合
	 * @param key
	 * @return
	 */
	public static  Map<String, Object> getHash(final String key){
    	return redisTemplate.execute(new RedisCallback<Map<String, Object>>(){
			@Override
			public Map<String, Object> doInRedis(RedisConnection connection) throws DataAccessException {	
				Map<byte[], byte[]> map = connection.hGetAll(stringRedisSerializer.serialize(key));
				Map<String, Object> result =Maps.newHashMap();
				if(map!=null && map.size()>0){
					for(byte[] b:map.keySet()){
						result.put(stringRedisSerializer.deserialize(b), jdkSerializationRedisSerializer.deserialize(map.get(b)));
					}
				}
				return result;
			}
    	});
    }
	
	/**
	 * 将存入hash中key为fields的值删除
	 * @param key
	 * @param fields
	 * @return
	 */
	public static  Long delHashValueByField(final String key, final String... fields){
		if(fields==null || fields.length<=0){
			return 0L;
		}
		
    	return redisTemplate.execute(new RedisCallback<Long>(){
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				List<byte[]> list = Lists.newArrayList(); 
				for(String field:fields){
					byte[] bytes =stringRedisSerializer.serialize(field);
					list.add(bytes);
				}	
				return connection.hDel(stringRedisSerializer.serialize(key), list.toArray(new byte[list.size()][]));
			}
    	});
    }
	
	/**
	 * 将values存入list中
	 * @param key
	 * @param values
	 * @return
	 */
	public static Long addList(final String key,final Object... values){
		if(values==null || values.length<=0){
			return 0L;
		}	
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				List<byte[]> list = Lists.newArrayList(); 
				for(Object value:values){
					byte[] bytes =jdkSerializationRedisSerializer.serialize(value);
					list.add(bytes);
				}
				return connection.lPush(stringRedisSerializer.serialize(key), list.toArray(new byte[list.size()][]));
			}
		});
	}
	
	/**
	 * 通过key获取list集合
	 * @param key
	 * @return
	 */
	public static List<Object> getList(final String key){
		return redisTemplate.execute(new RedisCallback<List<Object>>() {

			@Override
			public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = stringRedisSerializer.serialize(key);
				Long length = connection.lLen(keyByte);
				List<byte[]> byteList = connection.lRange(keyByte, 0, length);
				if(byteList==null || byteList.size()==0){
					return new ArrayList<Object>();
				}
				return Lists.transform(byteList, new Function<byte[], Object>() {
					@Override
					public Object apply(byte[] input) {						
						return jdkSerializationRedisSerializer.deserialize(input);
					}					
				});
			}
		});
	}
	
	/**
	 * 将values存入set中
	 * @param key
	 * @param value
	 * @return  插入set中的个数
	 */
	public static Long addSet(final String key,final Object... values){
		if(values==null || values.length<=0){
			return 0L;
		}		
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				List<byte[]> list = Lists.newArrayList(); 
				for(Object value:values){
					byte[] bytes =jdkSerializationRedisSerializer.serialize(value);
					list.add(bytes);
				}
				return connection.sAdd(stringRedisSerializer.serialize(key), list.toArray(new byte[list.size()][]));
			}			
		});
	}
	
	/**
	 * 通过key获取set集合
	 * @param key
	 * @return
	 */
	public static Set<Object> getSet(final String key){
		return redisTemplate.execute(new RedisCallback<Set<Object>>() {
			@Override
			public Set<Object> doInRedis(RedisConnection connection) throws DataAccessException {
				Set<byte[]> set = connection.sMembers(stringRedisSerializer.serialize(key));
				Set<Object> result = Sets.newHashSet();
				if(set!=null && set.size()>0){
					for(byte[] b:set){
						result.add(jdkSerializationRedisSerializer.deserialize(b));
					}
				}				
				return result;
			}
		});
	}
	
	/**
	 * 删除set集合中values数据
	 * @param key
	 * @param values
	 * @return
	 */
	public static Long delSetValue(final String key,final String... values){
		if(values==null || values.length<=0){
			return 0L;
		}
		
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				List<byte[]> list = Lists.newArrayList(); 
				for(String value:values){
					byte[] bytes =jdkSerializationRedisSerializer.serialize(value);
					list.add(bytes);
				}				
				return connection.sRem(stringRedisSerializer.serialize(key), list.toArray(new byte[list.size()][]));
			}
		});
	}
	
	/**
	 * 将value插入ZSet中，序号为score
	 * @param key
	 * @param score
	 * @param value
	 * @return
	 */
	public static Boolean addZSet(final String key,final double score,final Object value){
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {				
				return connection.zAdd(stringRedisSerializer.serialize(key), score, jdkSerializationRedisSerializer.serialize(value));
			}
		});
	}
	
	
	/**
	 * 通过key获取ZSet集合
	 * @param key
	 * @return
	 */
	public static Set<Object> getZSet(final String key){
		 return redisTemplate.execute(new RedisCallback<Set<Object>>() {
			@Override
			public Set<Object> doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte=stringRedisSerializer.serialize(key);
				Long length = connection.zCount(keyByte, 0, Long.MAX_VALUE);
				Set<byte[]> set =connection.zRange(keyByte, 0, length);
				Set<Object> result = Sets.newHashSet();
				if(set!=null && set.size()>0){
					for(byte[] b:set){
						result.add(jdkSerializationRedisSerializer.deserialize(b));
					}
				}
				return result;
			}
		});
	}
}
