package com.tooklili.task;

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
import com.tooklili.dao.intf.tooklili.ItemDao;
import com.tooklili.model.tooklili.Item;
import com.tooklili.util.PropertiesUtil;

/**
 * 同步优惠券商品到redis缓存中
 * @author shuai.ding
 * @date 2017年9月15日下午1:47:29
 */
@Service
public class SyncCouponsToReidsJob extends BaseJob{
	private static final Logger LOGGER = LoggerFactory.getLogger(SyncCouponsToReidsJob.class);
	
	@Resource
	private ItemDao itemDao;
	
	@Resource
	private RedisTemplate<?, ?> redisTemplate;
	
	private  static StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
	
	public SyncCouponsToReidsJob() {
		this.setCorn(PropertiesUtil.getInstance("system.properties").getValue("sync_coupons_to_redis_cron"));
	}

	@Override
	public void execute(){
		try{
			for(ItemCateEnum itemCateEnum:ItemCateEnum.values()){
			 Long count = saveToRedis(itemCateEnum);
			 LOGGER.info("类别为【{}】的优惠券商品存入redis缓存中成功，存入个数为：{}",itemCateEnum.getName(),count);
			}			
		}catch(Exception e){
			LOGGER.error("exception:",e);
		}	
	}
	
	

	/**
	 * 从数据库中查询商品集合，存入redis中，key为枚举名称
	 * @author shuai.ding
	 * @param itemCateEnum   枚举类
	 */
	private Long saveToRedis(final ItemCateEnum itemCateEnum){
		//从数据库中查询指定商品
		final List<Item> items = itemDao.queryItems(itemCateEnum.getCode());
		
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				List<byte[]> list = Lists.newArrayList(); 
				for(Item item:items){
					byte[] bytes =stringRedisSerializer.serialize(JSON.toJSONString(item));
					list.add(bytes);
				}
				return connection.lPush(stringRedisSerializer.serialize(itemCateEnum.getName()), list.toArray(new byte[list.size()][]));
			}
		});
	}
	
	/**
	 * 商品分类枚举类
	 * @author shuai.ding
	 * @date 2017年9月15日下午2:10:09
	 */
	private enum ItemCateEnum{
		CONSTUME(35,"服装"),
		MONTHER_BOBY(36,"母婴"),
		COSMETICS(37,"化妆品"),
		OCCUPY_HOME(38,"居家"),
		SHOE_BAG_ACCESSORIES(39,"鞋包配饰"),
		GASTRONOMY(40,"美食"),
		PRODUCT_STYLE_CAR(41,"文体车品"),
		DIGITAL_HOME_APPLIANCES(42,"数码家电");
		
		private Integer code;
		private String name;
		
		private ItemCateEnum(Integer code,String name) {
			this.code = code;
			this.name = name;
		}

		public Integer getCode() {
			return code;
		}

		public String getName() {
			return name;
		}
	}
}
