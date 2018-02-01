package com.tooklili.service.mongodb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.CollectionCallback;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.alibaba.fastjson.JSON;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.tooklili.dao.db.intf.tooklili.ItemDao;
import com.tooklili.model.tooklili.Item;
import com.tooklili.service.BaseTest;
import com.tooklili.service.biz.impl.tooklili.RndScope;
import com.tooklili.service.util.MongoPageable;
import com.tooklili.util.JsonFormatTool;
import com.tooklili.util.result.PageResult;

/**
 * mongodb 服务测试
 * @author shuai.ding
 *
 * @date 2017年9月21日下午3:17:52
 */
public class MongodbTest extends BaseTest{
	
	@Resource
	private MongoTemplate mongoTemplate;
	
	@Resource
	private ItemDao itemDao;
	
	/**
	 * 建立一个集合,及表
	 * @author shuai.ding
	 */
	@Test
	public void createCollection(){
		mongoTemplate.createCollection("test");
	}
	
	/**
	 * 插入测试
	 * @author shuai.ding
	 */
	@Test
	public void insertTest(){
		try{
			List<Item> list = itemDao.queryItemsByCateId(null);
			mongoTemplate.insert(list, "test");			
		}catch(Exception e){
			logger.error("exception",e);
		}
	}
	
	@Test
	public void delTest(){
		try{
			mongoTemplate.remove(new Query(), "test");
		}catch(Exception e){
			logger.error("exception",e);
		}
	}
	
	@Test
	public void findOneTest(){
		try{
			Item item = mongoTemplate.findOne(new Query(Criteria.where("numIid").is("536429307962")), Item.class,"test");
			logger.info(JsonFormatTool.formatJson(JSON.toJSONString(item)));
		}catch(Exception e){
			logger.error("exception",e);
		}
	}
	
	@Test
	public void findTest(){
		try{
			Query query = new Query(Criteria.where("cateId").is(37));
			query.with(new Sort(new Order(Direction.DESC, "addTime")));   //排序
			
			
			//总个数
			long count = mongoTemplate.count(query, Item.class,"test");
			
			Integer page =100;
			Integer size =2;
			query.with(new MongoPageable(page, size)); //分页			
			List<Item> list = mongoTemplate.find(query, Item.class,"test");
			
			
			PageResult<Item> result = new PageResult<Item>(page.longValue(),size.longValue(),count);
			
			result.setData(list);			
			logger.info(JsonFormatTool.formatJson(JSON.toJSONString(result)));
		}catch(Exception e){
			logger.error("exception",e);
		}
	}
	
	/**
	 * 更新
	 * @author shuai.ding
	 */
	@Test
	public void update(){
		try{
			mongoTemplate.updateMulti(new Query(), new Update(), "test");
		}catch(Exception e){
			logger.error("exception",e);
		}
	}
	
	/**
	 * 随机获取文档数据
	 * @author shuai.ding
	 */
	@Test
	public void getRandomDocTest(){
		try{
//			List<Item> items =  mongoTemplate.execute("test", new CollectionCallback<List<Item>>() {
//				
//
//				public List<Item> doInCollection(DBCollection collection) throws MongoException, DataAccessException {
//					int nLimit = 4;
//					List<Item> result = new ArrayList<Item>();
//					
//			    	//通过ObjectId.get()得到二个唯一的变量名。
//				    String sgteArgs="_OpenTmp"+ObjectId.get().toString();
//				    String slteArgs="_OpenTmp"+ObjectId.get().toString();
//				    
//				    System.out.println(sgteArgs+":"+slteArgs);
//				    
//				    DBObject query = new BasicDBObject();
//				    query.put("cateId", 35);
//				    //得到数据的总数
//				    long nLength=collection.count(query);
//				    System.out.println("记录总数:"+nLength);
//				    //根据要获取的记录的条数，对范围值进行修改
//				    RndScope rndScope=new RndScope(nLength,nLimit);
//			    	
//			    	//将创建时间太久的给删除掉
//			        DBCollection dbSystem=collection.getDB().getCollection("system.js");
//			        dbSystem.remove(new BasicDBObjectBuilder()
//			            .add("createDate",
//			                    new BasicDBObjectBuilder()
//			                    .add("$lt",new Date((new Date()).getTime()-24*60*1000)).get())
//			            .add("_id",Pattern.compile("_OpenTmp*"))
//			            .get());
//			        //向system.js集合中添加二个全局的变量
//			        dbSystem.findAndModify(
//			                new BasicDBObjectBuilder().add("_id", sgteArgs).get(),
//			                null,null, false, new BasicDBObjectBuilder()
//			                .add("_id", sgteArgs)
//			                .add("value",rndScope.getGteVal())
//			                .add("createDate",new Date())
//			                .get(), false, true);
//			        dbSystem.findAndModify(new BasicDBObjectBuilder().add("_id",slteArgs).get()
//			                ,null,null,false,new BasicDBObjectBuilder()
//			                                    .add("_id",slteArgs)
//			                                    .add("value",rndScope.getLteVal())
//			                                    .add("createDate",new Date())
//			                                    .get(),false,true);
//			        
//					
//					query.put("$where", "function(){var rnd=Math.random();return (rnd>="
//			                +sgteArgs+" && rnd<="+slteArgs+");}");
//					
//					DBCursor cursor = collection.find(query).limit(nLimit);
//					
//					while(cursor.hasNext()) {
//					     DBObject obj = cursor.next();
//					     Item item = JSON.parseObject(obj.toString(), Item.class);
//					     result.add(item);
//					  }
//					return result;
//				}
//			});			
//			logger.info(JsonFormatTool.formatJson(JSON.toJSONString(items)));
		}catch(Exception e){
			logger.error("exception",e);
		}
	}
}
