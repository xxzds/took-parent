package com.tooklili.dao.admin;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tooklili.dao.BaseTest;
import com.tooklili.dao.db.intf.admin.TookItemSearchKeywordDao;
import com.tooklili.model.admin.TookItemSearchKeyword;
import com.tooklili.util.JsonFormatTool;

public class TookItemSearchKeywordDaoTest extends BaseTest{
	
	@Autowired
	private TookItemSearchKeywordDao tookItemSearchKeywordDao;
	
	@Test
	public void query(){
		try{
			PageList<TookItemSearchKeyword> result = tookItemSearchKeywordDao.queryItemSearchKeywordsByPage(null, new PageBounds(1, 10));
			logger.info(JsonFormatTool.formatJson(JSON.toJSONString(result)));
		}catch(Exception e){
			logger.error("exception",e);
		}
	}

}
