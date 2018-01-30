package com.tooklili.task;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.tooklili.dao.db.intf.tooklili.ItemDao;
import com.tooklili.model.tooklili.Item;
import com.tooklili.util.PropertiesUtil;

/**
 * 同步优惠券商品到mongodb中
 * @author shuai.ding
 * @date 2017年10月16日上午9:59:06
 */
@Service
public class SyncCouponsToMongoJob extends BaseJob{
	
	@Resource
	private MongoTemplate mongoTemplate;
	
	@Resource
	private ItemDao itemDao;
	
	public SyncCouponsToMongoJob() {
		this.setCorn(PropertiesUtil.getInstance("system.properties").getValue("sync_coupons_to_mongo_cron"));
	}

	@Override
	public void execute() {
		//删除集合
		mongoTemplate.remove(new Query(), "coupons");
		
		//将数据插入集合
		List<Item> list = itemDao.queryItemsByCateId(null);
		mongoTemplate.insert(list, "coupons");		
	}

}
