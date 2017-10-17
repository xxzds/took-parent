package com.tooklili.service.biz.impl.tooklili;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.CollectionCallback;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.tooklili.enums.tooklili.ItemCateEnum;
import com.tooklili.model.tooklili.Item;
import com.tooklili.service.biz.intf.tooklili.ItemService;
import com.tooklili.service.util.MongoPageable;
import com.tooklili.util.result.ListResult;
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
				
		final int nLimit = size;
		List<Item> items =  mongoTemplate.execute(collection, new CollectionCallback<List<Item>>() {
			
			public List<Item> doInCollection(DBCollection collection) throws MongoException, DataAccessException {
				List<Item> result = new ArrayList<Item>();
				
		    	//通过ObjectId.get()得到二个唯一的变量名。
			    String sgteArgs="_OpenTmp"+ObjectId.get().toString();
			    String slteArgs="_OpenTmp"+ObjectId.get().toString();
			    
			    DBObject query = new BasicDBObject();
			    query.put("cateId",cateId);
			    //得到数据的总数
			    long nLength=collection.count(query);
			    //根据要获取的记录的条数，对范围值进行修改
			    RndScope rndScope=new RndScope(nLength,nLimit);
		    	
		    	//将创建时间太久的给删除掉
		        DBCollection dbSystem=collection.getDB().getCollection("system.js");
		        dbSystem.remove(new BasicDBObjectBuilder()
		            .add("createDate",
		                    new BasicDBObjectBuilder()
		                    .add("$lt",new Date((new Date()).getTime()-24*60*1000)).get())
		            .add("_id",Pattern.compile("_OpenTmp*"))
		            .get());
		        //向system.js集合中添加二个全局的变量
		        dbSystem.findAndModify(
		                new BasicDBObjectBuilder().add("_id", sgteArgs).get(),
		                null,null, false, new BasicDBObjectBuilder()
		                .add("_id", sgteArgs)
		                .add("value",rndScope.getGteVal())
		                .add("createDate",new Date())
		                .get(), false, true);
		        dbSystem.findAndModify(new BasicDBObjectBuilder().add("_id",slteArgs).get()
		                ,null,null,false,new BasicDBObjectBuilder()
		                                    .add("_id",slteArgs)
		                                    .add("value",rndScope.getLteVal())
		                                    .add("createDate",new Date())
		                                    .get(),false,true);
		        
				
				query.put("$where", "function(){var rnd=Math.random();return (rnd>="
		                +sgteArgs+" && rnd<="+slteArgs+");}");
				
				DBCursor cursor = collection.find(query).limit(nLimit);
				
				while(cursor.hasNext()) {
				     DBObject obj = cursor.next();
				     //此处将_id的键转化成id，最终能将json字符串存入实体中
				     obj.put("id", obj.get("_id"));
				     Item item = JSON.parseObject(obj.toString(), Item.class);
				     result.add(item);
				  }
				return result;
			}
		});			
		result.setData(items);
		return result;
	}

}
