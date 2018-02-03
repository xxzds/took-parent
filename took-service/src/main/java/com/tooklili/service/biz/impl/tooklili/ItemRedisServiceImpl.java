package com.tooklili.service.biz.impl.tooklili;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tooklili.dao.db.intf.tooklili.ItemDao;
import com.tooklili.dao.redis.RedisItemRepository;
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
	private RedisItemRepository redisItemRepository;
	
	@Resource
	private ItemDao itemDao;

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
		PageResult<Item> result = new PageResult<Item>(currentPage,pageSize);		
		if(cateId==null || cateId==0){
			return result.setErrorMessage(10001, "参数cateId不能为空");
		}
		
		final ItemCateEnum itemCateEnum = ItemCateEnum.valueOf(cateId);
		if(itemCateEnum==null){
			return result.setErrorMessage(10001, "参数cateId不合法");
		}
		
		List<Item> items = redisItemRepository.queryItemsByCateId(itemCateEnum, currentPage, pageSize);
		Long count =redisItemRepository.countItemsByCateId(itemCateEnum);
		
		result.setData(items);
		result.setTotalCountLong(count);
		
		return result;
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
		result.setData(redisItemRepository.randomItemByCateId(itemCateEnum, size));
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
