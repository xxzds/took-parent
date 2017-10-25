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
import com.tooklili.service.util.RedisUtils;

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
	
	
	@Test
	public void setAlimamaCookie(){
		try{
			String cookie="t=f7de8508771a63b914836dcf4d318bed; account-path-guide-s1=true; 120259453_yxjh-filter-1=true; undefined_yxjh-filter-1=true; pub-message-center=1; cookie2=1d4e1c75238bdbe96a5205a68c44b808; _tb_token_=fb563333b8588; v=0; alimamapwag=TW96aWxsYS81LjAgKFdpbmRvd3MgTlQgNi4xOyBXT1c2NCkgQXBwbGVXZWJLaXQvNTM3LjM2IChLSFRNTCwgbGlrZSBHZWNrbykgQ2hyb21lLzYxLjAuMzE2My4xMDAgU2FmYXJpLzUzNy4zNg%3D%3D; cookie32=188ac1e5db32c5916d7e368d0254cbbf; alimamapw=FQZUX1AAWA0Ma1MNB1YABAAOUQZXUVgHAl8JAVAPUFFUVAAIB1BTAgRX; cookie31=MTIwMjU5NDUzLHRiNjkxNTk0NSwxMTE0MzMyOTA1QHFxLmNvbSxUQg%3D%3D; login=UtASsssmOIJ0bQ%3D%3D; cna=kh9nEqPMlWgCAdyyGRZG7pPS; isg=AnZ2nT7zMzEBJcfHzSrP0VvUx6y4P4j4JwUCieBfQ9n0Ixa9SCcK4dzRTcm0";
			RedisUtils.setString("alimama_cookie", cookie);
		}catch(Exception e){
			logger.info("exception",e);
		}
		
	}
	
	/**
	 * 模拟消息队列(list)
	 * @author shuai.ding
	 */
	@Test
	public void simulateMessageQueue(){
		try{
			final byte[] key = stringRedisSerializer.serialize("list");
			
			redisTemplate.execute(new RedisCallback<String>(){

				@Override
				public String doInRedis(final RedisConnection connection) throws DataAccessException {
					//切换数据库到1中
					connection.select(1);
										
					connection.del(key);
					for(int i=0;i<30;i++){
						connection.lPush(key, stringRedisSerializer.serialize(String.valueOf(i)));
					}					
					return null;
				}				
			});	
			
			
			/**
			 * 开启10个线程
			 */
			for(int i=0;i<10;i++){
				new Thread( new Runnable() {
					public void run() {
						while(true){
							byte[] valueByte = redisTemplate.execute(new RedisCallback<byte[]>() {

								@Override
								public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
									//切换数据库到1中
									connection.select(1);
									
									byte[] valueByte = connection.lPop(key);
									return valueByte;
								}
							});
							
							if(valueByte==null){
								break;
							}
							logger.info("当前线程：{}，从队列中取出的数据为：{}",Thread.currentThread().getName(),stringRedisSerializer.deserialize(valueByte));						
						}
						
					}
				}).start();
			}

			 //主线程休眠  
	        Thread.sleep(Long.MAX_VALUE);
		}catch(Exception e){
			logger.info("exception",e);
		}
		
	}
}
