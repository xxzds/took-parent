package com.tooklili.jobhandler;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tooklili.dao.db.intf.tooklili.ItemDao;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;

/**
 * 清除过期商品，直接操作数据库
 * @author shuai.ding
 * @date 2018年1月9日下午1:46:09
 */
@JobHander(value="clearExpiredItemBySqlJobHandler")
@Service
public class ClearExpiredItemBySqlJobHandler extends IJobHandler{
	private static final Logger LOGGER = LoggerFactory.getLogger(ClearExpiredItemBySqlJobHandler.class);
	
	@Resource
	private ItemDao itemDao;

	@Override
	public ReturnT<String> execute(String... params) throws Exception {
		long count = itemDao.deleteExpiredItem();
		LOGGER.info("删除{}个过期商品",count);
		return ReturnT.SUCCESS;
	}
}
