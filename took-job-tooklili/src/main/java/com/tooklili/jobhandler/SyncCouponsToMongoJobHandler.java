package com.tooklili.jobhandler;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.tooklili.dao.intf.tooklili.ItemDao;
import com.tooklili.model.tooklili.Item;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;

/**
 * 同步优惠券商品到mongodb中
 * @author ding.shuai
 * @date 2017年10月22日下午11:05:53
 */
@JobHander(value="syncCouponsToMongoJobHandler")
@Service
public class SyncCouponsToMongoJobHandler extends IJobHandler{
	@Resource
	private MongoTemplate mongoTemplate;
	
	@Resource
	private ItemDao itemDao;

	@Override
	public ReturnT<String> execute(String... arg0) throws Exception {
		//删除集合
		mongoTemplate.remove(new Query(), "coupons");
		
		//将数据插入集合
		List<Item> list = itemDao.queryItemsByCateId(null);
		mongoTemplate.insert(list, "coupons");	
		return ReturnT.SUCCESS;
	}

}
