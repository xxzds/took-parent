package com.tooklili.dao.admin;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.tooklili.dao.BaseTest;
import com.tooklili.dao.db.intf.admin.TookItemCateDao;
import com.tooklili.model.admin.TookItemCate;
import com.tooklili.util.JsonFormatTool;

public class TookItemCateDaoTest extends BaseTest{
	
	@Autowired
	private TookItemCateDao tookItemCateDao;
	
	@Test
	public void query(){
		try{
			List<TookItemCate> list = tookItemCateDao.find(null);
			logger.info(JsonFormatTool.formatJson(JSON.toJSONString(list)));
		}catch(Exception e){
			logger.error("exception",e);
		}		
	}

}
