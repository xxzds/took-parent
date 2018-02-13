package com.tooklili.dao.redis;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.tooklili.enums.admin.ApiTypeEnum;
import com.tooklili.model.admin.TookItemSearchKeyword;
import com.tooklili.model.admin.TookKeywordDetail;
import com.tooklili.model.taobao.TookKeywordInfo;

/**
 * 商品分类和搜索关键字存储
 * @author ding.shuai
 * @date 2018年2月10日下午10:39:05
 */
@Repository
public class RedisItemCateAndKeywordRepository {
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisItemCateAndKeywordRepository.class);
	
	@Resource
	private RedisTemplate<String, String> redisTemplate;
	
	@Resource(name = "updateKeywordSearchCurrentPage")
	private RedisScript<TookKeywordInfo> updateKeywordSearchCurrentPage;
	
	private  static StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
	
	//商品分类+搜索关键字
	private static final String ITEM_CATE_KEYWORD  = "item_cate_keyword";
	private byte[] itemCateKewordKey = stringRedisSerializer.serialize(ITEM_CATE_KEYWORD);
	
	//淘宝客 关键字 分页信息
	private static final String TBK_ITEM_CATE_KEYWORD_DETAIL = "tbk_item_cate_keyword_detail";
	private byte[] tbkItemCateKeywordDetailKey = stringRedisSerializer.serialize(TBK_ITEM_CATE_KEYWORD_DETAIL);
	
	//超级搜 关键字 分页信息
	private static final String SUPER_ITEM_CATE_KEYWORD_DETAIL = "super_item_cate_keyword_detail";
	private byte[] superItemKeywordDetailKey = stringRedisSerializer.serialize(SUPER_ITEM_CATE_KEYWORD_DETAIL);
		
	/**
	 * 商品分类+搜索关键字+最大页数集合存入redis的set中
	 * key 商品分类+搜索关键字+最大页数 value 当前页 存入redis的hash中
	 * @param tookItemSearchKeywords   数据库中存入的实体
	 * @return
	 */
	public void saveItemCateAndKeyword(List<TookItemSearchKeyword> tookItemSearchKeywords){
		 redisTemplate.execute(new RedisCallback<Long>(){
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {	
				
				List<byte[]> cateKeywordBytesFromRedis = Lists.newArrayList();
				cateKeywordBytesFromRedis.addAll(connection.sMembers(itemCateKewordKey));
								
				List<byte[]> cateKeywordBytesFromDb = Lists.newArrayList();				
				for(TookItemSearchKeyword tookItemSearchKeyword:tookItemSearchKeywords){	
					//商品分类id+搜索关键字+最大页
					String key = tookItemSearchKeyword.getItemCateId()+"_"+tookItemSearchKeyword.getSearchKeyword()+"_"+tookItemSearchKeyword.getMaxPage();
					byte[] keyByte =stringRedisSerializer.serialize(key);
					cateKeywordBytesFromDb.add(keyByte);
										
					//当前页,默认为1
				    byte[] valueByte =stringRedisSerializer.serialize("1");
					
					//hash，如果key存在，则不插入
					connection.hSetNX(tbkItemCateKeywordDetailKey, keyByte, valueByte);
					connection.hSetNX(superItemKeywordDetailKey, keyByte, valueByte);
				}
				
				List<String> cateKeywordFromDb = Lists.transform(cateKeywordBytesFromDb, new Function<byte[],String>() {
					@Override
					public String apply(byte[] input) {						
						return stringRedisSerializer.deserialize(input);
					}					
				});
				
				List<String> cateKeywordFromRedis = Lists.transform(cateKeywordBytesFromRedis, new Function<byte[],String>() {
					@Override
					public String apply(byte[] input) {
						return stringRedisSerializer.deserialize(input);
					}
				});
				
				//添加(补集操作)
				@SuppressWarnings("unchecked")
				List<String> addCateKeyword = (List<String>) CollectionUtils.subtract(cateKeywordFromDb, cateKeywordFromRedis);	
				if(addCateKeyword != null && addCateKeyword.size() > 0){
					Long insert = connection.sAdd(itemCateKewordKey, Lists.transform(addCateKeyword, new Function<String,byte[]>() {
						@Override
						public byte[] apply(String input) {
							return stringRedisSerializer.serialize(input);
						}
						
					}).toArray(new byte[addCateKeyword.size()][]));
					LOGGER.info("向redis中插入{}个商品分类，分别为:{}",insert,JSON.toJSON(addCateKeyword));
				}				
								
				//删除
				@SuppressWarnings("unchecked")
				List<String> delCateKeyword = (List<String>) CollectionUtils.subtract(cateKeywordFromRedis,cateKeywordFromDb);
				if(delCateKeyword != null && delCateKeyword.size() > 0){
					List<byte[]> delCateKeywordBytes = Lists.transform(delCateKeyword, new Function<String,byte[]>() {
						@Override
						public byte[] apply(String input) {
							return stringRedisSerializer.serialize(input);
						}
						
					});
					
					for(byte[] value : delCateKeywordBytes){
						connection.sRem(itemCateKewordKey,value);
						connection.hDel(tbkItemCateKeywordDetailKey, value);
						connection.hDel(superItemKeywordDetailKey, value);
					}
					LOGGER.info("向redis中删除{}个商品分类，分别为:{}",delCateKeyword.size(),JSON.toJSON(delCateKeyword));
				}
				return null;
			}			
		});
	}
	
	/**
	 * 从hash中查询关键字信息（分类id，关键字，最大页，当前页）
	 * @param apiTypeEnum
	 * @return
	 */
	public List<TookKeywordDetail> queryKeywordDetail(ApiTypeEnum apiTypeEnum){
		return redisTemplate.execute(new RedisCallback<List<TookKeywordDetail>>() {

			@Override
			public List<TookKeywordDetail> doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyByte = null;
				if(apiTypeEnum == ApiTypeEnum.TBK_API){
					keyByte = tbkItemCateKeywordDetailKey;
				}else if(apiTypeEnum == ApiTypeEnum.SUPER_SEARCH_API){
					keyByte = superItemKeywordDetailKey;
				}else{
					return null;
				}
				
				Map<byte[], byte[]> map = connection.hGetAll(keyByte);
				
				List<TookKeywordDetail> result = Lists.newArrayList();
				for(byte[] key : map.keySet()){
					byte[] value = map.get(key);
					
					TookKeywordDetail tookKeywordDetail = new TookKeywordDetail();
					tookKeywordDetail.setKeyword(stringRedisSerializer.deserialize(key));
					tookKeywordDetail.setCurrentPage(Integer.parseInt(stringRedisSerializer.deserialize(value)));
					result.add(tookKeywordDetail);
				}			
				return result;
			}
		});
	}
	
	/**
	 * 随机获取关键字信息
	 * @param apiTypeEnum
	 * @return
	 */
	public TookKeywordInfo getRandomKeywordInfo(ApiTypeEnum apiTypeEnum){
		String key = null;
		if(apiTypeEnum == ApiTypeEnum.TBK_API){
			key = TBK_ITEM_CATE_KEYWORD_DETAIL;
		}else if(apiTypeEnum == ApiTypeEnum.SUPER_SEARCH_API){
			key = SUPER_ITEM_CATE_KEYWORD_DETAIL;
		}else{
			throw new RuntimeException("枚举类不存在");
		}
				
		//获取随机关键字
		String keyword = redisTemplate.opsForSet().randomMember(ITEM_CATE_KEYWORD);
		if(StringUtils.isEmpty(keyword)){
			LOGGER.info("没有随机获取到关键字");
			return null;
		}
		
		//获取当前页信息，并进行加1操作
	    TookKeywordInfo tookKeywordInfo = redisTemplate.execute(updateKeywordSearchCurrentPage,new StringRedisSerializer(),
	    		new Jackson2JsonRedisSerializer<>(TookKeywordInfo.class),
	    		Collections.singletonList(key),keyword);
	    return tookKeywordInfo;
	}
}
