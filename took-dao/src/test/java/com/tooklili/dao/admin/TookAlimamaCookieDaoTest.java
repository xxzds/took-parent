package com.tooklili.dao.admin;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tooklili.dao.BaseTest;
import com.tooklili.dao.db.intf.admin.TookAlimamaCookieDao;
import com.tooklili.model.admin.TookAlimamaCookie;
import com.tooklili.util.JsonFormatTool;

public class TookAlimamaCookieDaoTest extends BaseTest{
	
	@Autowired
	private TookAlimamaCookieDao tookAlimamaCookieDao;
	
	@Test
	public void findAllTest(){
		try{
			List<TookAlimamaCookie> lists = tookAlimamaCookieDao.find(null);
			logger.info(JsonFormatTool.formatJson(JSON.toJSONString(lists)));
		}catch(Exception e){
			logger.error("exception",e);
		}		
	}
	
	@Test
	public void queryAlimamaCookiesByPageTest(){
		try{
			PageBounds pageBounds = new PageBounds(1,10);
			PageList<TookAlimamaCookie> lists = tookAlimamaCookieDao.queryAlimamaCookiesByPage(null, pageBounds);
			logger.info(JsonFormatTool.formatJson(JSON.toJSONString(lists)));
		}catch(Exception e){
			logger.error("exception",e);
		}
	}

}
