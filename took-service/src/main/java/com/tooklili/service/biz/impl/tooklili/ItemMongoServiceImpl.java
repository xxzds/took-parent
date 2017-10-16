package com.tooklili.service.biz.impl.tooklili;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.tooklili.enums.tooklili.ItemCateEnum;
import com.tooklili.model.tooklili.Item;
import com.tooklili.service.biz.intf.tooklili.ItemService;
import com.tooklili.service.util.MongoPageable;
import com.tooklili.util.result.PageResult;
import com.tooklili.util.result.PlainResult;

/**
 * 从mongodb中取优惠券商品
 * @author shuai.ding
 *
 * @date 2017年10月16日下午5:07:42
 */
@Service
public class ItemMongoServiceImpl implements ItemService{
	
	@Resource
	private MongoTemplate mongoTemplate;
	
	/*表名称*/
	private final String collection="coupons";

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
		
		Query query = new Query(Criteria.where("cateId").is(cateId));
		query.with(new Sort(new Order(Direction.DESC, "addTime")));   //排序
		
		
		//总个数
		long count = mongoTemplate.count(query, Item.class,collection);
		result.setTotalCount(count);
		
		
		query.with(new MongoPageable(currentPage.intValue(), pageSize.intValue())); //分页			
		List<Item> list = mongoTemplate.find(query, Item.class,collection);
		result.setData(list);
		return result;
	}

	@Override
	public PlainResult<Item> queryItemById(Long id) {
		PlainResult<Item> result = new PlainResult<Item>();
		if(id==null){
			return result.setErrorMessage("参数id不能为空");
		}
		Item item = mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), Item.class,collection);
		result.setData(item);
		return result;
	}

}
