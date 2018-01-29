package com.tooklili.service.biz.impl.tooklili;

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
import com.tooklili.dao.intf.tooklili.ItemDao;
import com.tooklili.enums.tooklili.ItemCateEnum;
import com.tooklili.model.tooklili.Item;
import com.tooklili.service.biz.intf.tooklili.ItemService;
import com.tooklili.util.result.ListResult;
import com.tooklili.util.result.PageResult;
import com.tooklili.util.result.PlainResult;

/**
 * 从redis缓存中取优惠券商品
 * @author ding.shuai
 * @date 2017年9月16日上午10:45:49
 */
@Service
public class ItemRedisServiceImpl implements ItemService{
	
	@Resource
	private RedisTemplate<?, ?> redisTemplate;
	
	@Resource
	private ItemDao itemDao;
	
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
				result.setTotalCountLong(count);
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
	
	/**
	 * 通过主键id查询商品
	 * @param id
	 * @return
	 */
	public PlainResult<Item> queryItemById(Long id){
		PlainResult<Item> result = new PlainResult<Item>();
		if(id==null){
			return result.setErrorMessage("参数id不能为空");
		}		
		Item item = itemDao.queryItemById(id);
		result.setData(item);		
		return result;
	}

	@Override
	public ListResult<Item> getRandomItemByCateId(Integer cateId, Integer size) {
		ListResult<Item> result = new ListResult<Item>();
		
		if(cateId==null || cateId==0){
			return result.setErrorMessage(10001, "参数cateId不能为空");
		}
		
		final ItemCateEnum itemCateEnum = ItemCateEnum.valueOf(cateId);
		if(itemCateEnum==null){
			return result.setErrorMessage(10001, "参数cateId不合法");
		}
		
		if(size==null){
			size=10;
		}
		
		final int nLimit = size;
		List<Item> items = redisTemplate.execute(new RedisCallback<List<Item>>(){
			@Override
			public List<Item> doInRedis(RedisConnection connection) throws DataAccessException {
				List<Item> items = Lists.newArrayList();
				byte[] key = stringRedisSerializer.serialize(itemCateEnum.getName());
				
				//总数
				Long count = connection.lLen(key);
				
				for(int i=0;i<nLimit;i++){
					Long index =  (long) (Math.random() * count);
					byte[] valueByte = connection.lIndex(key, index);
					String value = stringRedisSerializer.deserialize(valueByte);
					items.add(JSON.parseObject(value, Item.class));
				}
				return items;
			}
			
		});
		result.setData(items);
		return result;
	}

	@Override
	public PageResult<Item> queryCouponItemsByKeyWords(String keyWords,Long currentPage,Long pageSize) {
		if(currentPage==null || currentPage==0){
			currentPage=1L;
		}
		if(pageSize==null || pageSize==0){
			pageSize=20L;
		}
		PageResult<Item> result = new PageResult<Item>(currentPage,pageSize);
		result.setErrorMessage("无关键字查询功能");
		return result;
	}
}
