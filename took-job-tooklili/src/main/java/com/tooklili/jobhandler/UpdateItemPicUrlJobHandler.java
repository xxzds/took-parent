package com.tooklili.jobhandler;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tooklili.dao.db.intf.tooklili.ItemDao;
import com.tooklili.util.ClearCacheUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;

/**
 * 更新商品的图片url，如果没有增加"http:"的，加上
 * @author shuai.ding
 * @date 2017年10月26日下午12:55:14
 */
@JobHander(value="updateItemPicUrlJobHandler")
@Service
public class UpdateItemPicUrlJobHandler extends IJobHandler{
	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateItemPicUrlJobHandler.class);
	
	@Resource
	private ItemDao ItemDao;

	@Override
	public ReturnT<String> execute(String... params) throws Exception {
		Long count = ItemDao.updateItemPicUrl();
		LOGGER.info("更新商品图片url的个数为:{}",count);
		
		//更新完后，清除缓存
		if(count>0){
			ClearCacheUtil.clearCache();
		}
		return ReturnT.SUCCESS;
	}

}
