package com.tooklili.service.biz.api.tooklili;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.tooklili.enums.tooklili.ItemCateEnum;
import com.tooklili.model.tooklili.Item;
import com.tooklili.util.result.PageResult;

/**
 * 从redis缓存中取优惠券商品
 * @author ding.shuai
 * @date 2017年9月16日上午10:45:49
 */
@Service
public class TookliliService {
	
	@Resource
	private RedisTemplate<?, ?> redisTemplate;
	
	private  static StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

	/**
	 * 通过cateId查询优惠券商品列表
	 * @param cateId
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public PageResult<Item> queryCouponItemsByCateId(Integer cateId,Long currentPage,Long pageSize){
		if(currentPage==null || currentPage==0){
			currentPage=1L;
		}
		if(pageSize==null || pageSize==0){
			pageSize=20L;
		}
		final PageResult<Item> result = new PageResult<Item>(currentPage,pageSize);		
		if(cateId==null || cateId==0){
			return result.setErrorMessage(10001, "参数cateId不能为空");
		}
		
		final ItemCateEnum itemCateEnum = ItemCateEnum.valueOf(cateId);
		if(itemCateEnum==null){
			return result.setErrorMessage(10001, "参数cateId不合法");
		}
		
		
		
		final long begin =(currentPage-1)*pageSize;
		final long end = begin+pageSize-1;		
		return redisTemplate.execute(new RedisCallback<PageResult<Item>>(){
			@Override
			public PageResult<Item> doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] key = stringRedisSerializer.serialize(itemCateEnum.getName());
				List<byte[]> byteList = connection.lRange(key, begin, end);
				//总页数
				Long count = connection.lLen(key);
				result.setTotalCount(count);
				if(byteList==null || byteList.size()==0){
					result.setData(new ArrayList<Item>());
					return result;
				}
				
				List<Item> data =  Lists.transform(byteList, new Function<byte[], Item>() {
					@Override
					public Item apply(byte[] input) {						
						String value = stringRedisSerializer.deserialize(input);
						return JSON.parseObject(value, Item.class);
					}					
				});
				
				result.setData(data);				
				return result;
			}
			
		});
	}
}
