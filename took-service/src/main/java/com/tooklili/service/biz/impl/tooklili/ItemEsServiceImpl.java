package com.tooklili.service.biz.impl.tooklili;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tooklili.dao.es.EsItemRepository;
import com.tooklili.model.tooklili.Item;
import com.tooklili.service.biz.intf.tooklili.ItemService;
import com.tooklili.util.result.ListResult;
import com.tooklili.util.result.PageResult;
import com.tooklili.util.result.PlainResult;

@Service
public class ItemEsServiceImpl implements ItemService{
	
	@Autowired
	private EsItemRepository esItemRepository;

	@Override
	public PageResult<Item> queryCouponItemsByCateId(Integer cateId, Long currentPage, Long pageSize) {
		if(currentPage==null || currentPage==0){
			currentPage=1L;
		}
		if(pageSize==null || pageSize==0){
			pageSize=20L;
		}
		PageResult<Item> result = new PageResult<Item>(currentPage,pageSize);	
		result.setData(esItemRepository.queryItems(cateId, currentPage.intValue(), pageSize.intValue()));
		result.setTotalCountLong(esItemRepository.countItems(cateId));
		
		return result;
	}

	@Override
	public PlainResult<Item> queryItemById(Long id) {
		PlainResult<Item> result = new PlainResult<Item>();
		if(id==null){
			return result.setErrorMessage("参数id不能为空");
		}
		Item item = esItemRepository.queryItemById(id);
		result.setData(item);
		return result;
	}

	@Override
	public ListResult<Item> getRandomItemByCateId(Integer cateId, Integer size) {
		ListResult<Item> result = new ListResult<Item>();
		result.setData(esItemRepository.queryRandomItemByCateId(cateId, size));		
		return result;
	}

	@Override
	public PageResult<Item> queryCouponItemsByKeyWords(String keyWords, Long currentPage, Long pageSize) {
		if(currentPage==null || currentPage==0){
			currentPage=1L;
		}
		if(pageSize==null || pageSize==0){
			pageSize=20L;
		}
		PageResult<Item> result = new PageResult<Item>(currentPage,pageSize);
		result.setData(esItemRepository.queryItemsByKeyword(keyWords, currentPage.intValue(), pageSize.intValue()));
		result.setTotalCountLong(esItemRepository.countItemsByKeyword(keyWords));		
		return result;
	}

}
