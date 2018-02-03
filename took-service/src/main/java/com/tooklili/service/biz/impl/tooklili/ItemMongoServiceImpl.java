package com.tooklili.service.biz.impl.tooklili;

import javax.annotation.Resource;

import com.tooklili.dao.mongodb.MongoItemRepository;
import com.tooklili.enums.tooklili.ItemCateEnum;
import com.tooklili.model.tooklili.Item;
import com.tooklili.service.biz.intf.tooklili.ItemService;
import com.tooklili.util.result.ListResult;
import com.tooklili.util.result.PageResult;
import com.tooklili.util.result.PlainResult;

/**
 * 从mongodb中取优惠券商品
 * @author shuai.ding
 *
 * @date 2017年10月16日下午5:07:42
 */
//@Service
public class ItemMongoServiceImpl implements ItemService{
	
	@Resource
	private MongoItemRepository mongoItemRepository;

	@Override
	public PageResult<Item> queryCouponItemsByCateId(Integer cateId, Long currentPage, Long pageSize) {
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
		
		ItemCateEnum itemCateEnum = ItemCateEnum.valueOf(cateId);
		if(itemCateEnum==null){
			return result.setErrorMessage(10001, "参数cateId不合法");
		}
		
		result.setData(mongoItemRepository.queryItemsByCateId(cateId, currentPage.intValue(), pageSize.intValue()));
		result.setTotalCountLong(mongoItemRepository.countItemsByCateId(cateId));
		return result;
	}

	@Override
	public PlainResult<Item> queryItemById(Long id) {
		PlainResult<Item> result = new PlainResult<Item>();
		if(id==null){
			return result.setErrorMessage("参数id不能为空");
		}
		result.setData(mongoItemRepository.queryItemById(id));
		return result;
	}

	@Override
	public ListResult<Item> getRandomItemByCateId(final Integer cateId, Integer size) {
		ListResult<Item> result = new ListResult<Item>();
		
		if(cateId==null || cateId==0){
			return result.setErrorMessage(10001, "参数cateId不能为空");
		}
		
		ItemCateEnum itemCateEnum = ItemCateEnum.valueOf(cateId);
		if(itemCateEnum==null){
			return result.setErrorMessage(10001, "参数cateId不合法");
		}
		
		//默认值
		if(size==null){
			size=10;
		}
		result.setData(mongoItemRepository.queryRandomItemByCateId(cateId, size));
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
		
		result.setData(mongoItemRepository.queryItemsByKeyword(keyWords, currentPage.intValue(), pageSize.intValue()));
		result.setTotalCountLong(mongoItemRepository.countItemsByKeyword(keyWords));
		return result;
	}

}
