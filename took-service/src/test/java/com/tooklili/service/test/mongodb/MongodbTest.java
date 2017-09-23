package com.tooklili.service.test.mongodb;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.alibaba.fastjson.JSON;
import com.tooklili.dao.intf.tooklili.ItemDao;
import com.tooklili.model.tooklili.Item;
import com.tooklili.service.test.BaseTest;
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
			List<Item> list = itemDao.queryItems(null);
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
}
