package com.tooklili.dao.mongodb;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.tooklili.model.tooklili.Item;

/**
 * @author shuai.ding
 * @date 2018年2月3日下午5:10:55
 */
@Repository
public class MongoItemRepository {
	
	@Resource
	private MongoTemplate mongoTemplate;
	
	/*表名称*/
	private final String collection="coupons";

	/**
	 * 查询商品集合
	 * @author shuai.ding
	 * @param cateId      分类id
	 * @param currentPage 当前页
	 * @param pageSize    页面大小
	 * @return
	 */
	public List<Item> queryItemsByCateId(Integer cateId, int currentPage, int pageSize){
		Query query = new Query(Criteria.where("cateId").is(cateId));					
		query.with(new MongoPageable(currentPage, pageSize,Sort.by(new Order(Direction.DESC, "addTime")))); //分页、排序			
		List<Item> items = mongoTemplate.find(query, Item.class,collection);
		return items;
	}
	
	/**
	 * 查询商品集合总数
	 * @author shuai.ding
	 * @param cateId        分类id
	 * @return
	 */
	public long countItemsByCateId(Integer cateId){
		Query query = new Query(Criteria.where("cateId").is(cateId));
		//总个数
		long count = mongoTemplate.count(query, Item.class,collection);
		return count;
	}
	
	/**
	 * 通过主键id，查询商品
	 * @author shuai.ding
	 * @param id
	 * @return
	 */
	public Item queryItemById(Long id){
		Item item = mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), Item.class,collection);
		return item;
	}
	
	/**
	 * 随机获取商品集合
	 * @author shuai.ding
	 * @param cateId     分类id
	 * @param size       返回商品集合大小
	 * @return
	 */
	public List<Item> queryRandomItemByCateId(Integer cateId, Integer size){
//		final int nLimit = size;
//		List<Item> items =  mongoTemplate.execute(collection, new CollectionCallback<List<Item>>() {
//			
//			public List<Item> doInCollection(DBCollection collection) throws MongoException, DataAccessException {
//				List<Item> result = new ArrayList<Item>();
//				
//		    	//通过ObjectId.get()得到二个唯一的变量名。
//			    String sgteArgs="_OpenTmp"+ObjectId.get().toString();
//			    String slteArgs="_OpenTmp"+ObjectId.get().toString();
//			    
//			    DBObject query = new BasicDBObject();
//			    query.put("cateId",cateId);
//			    //得到数据的总数
//			    long nLength=collection.count(query);
//			    //根据要获取的记录的条数，对范围值进行修改
//			    RndScope rndScope=new RndScope(nLength,nLimit);
//		    	
//		    	//将创建时间太久的给删除掉
//		        DBCollection dbSystem=collection.getDB().getCollection("system.js");
//		        dbSystem.remove(new BasicDBObjectBuilder()
//		            .add("createDate",
//		                    new BasicDBObjectBuilder()
//		                    .add("$lt",new Date((new Date()).getTime()-24*60*1000)).get())
//		            .add("_id",Pattern.compile("_OpenTmp*"))
//		            .get());
//		        //向system.js集合中添加二个全局的变量
//		        dbSystem.findAndModify(
//		                new BasicDBObjectBuilder().add("_id", sgteArgs).get(),
//		                null,null, false, new BasicDBObjectBuilder()
//		                .add("_id", sgteArgs)
//		                .add("value",rndScope.getGteVal())
//		                .add("createDate",new Date())
//		                .get(), false, true);
//		        dbSystem.findAndModify(new BasicDBObjectBuilder().add("_id",slteArgs).get()
//		                ,null,null,false,new BasicDBObjectBuilder()
//		                                    .add("_id",slteArgs)
//		                                    .add("value",rndScope.getLteVal())
//		                                    .add("createDate",new Date())
//		                                    .get(),false,true);
//		        
//				
//				query.put("$where", "function(){var rnd=Math.random();return (rnd>="
//		                +sgteArgs+" && rnd<="+slteArgs+");}");
//				
//				DBCursor cursor = collection.find(query).limit(nLimit);
//				
//				while(cursor.hasNext()) {
//				     DBObject obj = cursor.next();
//				     //此处将_id的键转化成id，最终能将json字符串存入实体中
//				     obj.put("id", obj.get("_id"));
//				     Item item = JSON.parseObject(obj.toString(), Item.class);
//				     result.add(item);
//				  }
//				return result;
//			}
//		});		
		
		return null;
	}
	
	/**
	 * 通过关键字查询字段title，返回集合
	 * @author shuai.ding
	 * @param keyword        关键字
	 * @param currentPage    当前页
	 * @param pageSize       页面大小
	 * @return
	 */
	public List<Item> queryItemsByKeyword(String keyword,int currentPage, int pageSize){
		Query query = new Query(Criteria.where("title").regex(keyword));	
		query.with(new MongoPageable(currentPage, pageSize,Sort.by(new Order(Direction.DESC, "addTime")))); //分页、排序
		
		return mongoTemplate.find(query, Item.class,collection);
	}
	
	/**
	 * 通过关键字查询字段title，返回总个数
	 * @author shuai.ding
	 * @param keyword       关键字
	 * @return
	 */
	public long countItemsByKeyword(String keyword){
		Query query = new Query(Criteria.where("title").regex(keyword));
		
		//总个数
		long count = mongoTemplate.count(query, Item.class,collection);
		return count;
	}
}
