package com.tooklili.service.jobhandler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tooklili.dao.db.intf.admin.TookAlimamaCookieDao;
import com.tooklili.enums.admin.IsAvailableEnum;
import com.tooklili.model.admin.TookAlimamaCookie;
import com.tooklili.util.HttpClientUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;

/**
 * 保活alimama登录的cookie
 * @author ding.shuai
 * @date 2017年10月22日下午10:57:19
 */
@JobHander(value="persistenceAlimamaCookieJobHandler")
@Service
public class PersistenceAlimamaCookieJobHandler extends IJobHandler{
	private static final Logger LOGGER =LoggerFactory.getLogger(PersistenceAlimamaCookieJobHandler.class);
	
	@Autowired
	private TookAlimamaCookieDao tookAlimamaCookieDao;

	@Override
	public ReturnT<String> execute(String... arg0) throws Exception {
		//从数据库中查询alimama cookie列表
		TookAlimamaCookie tookAlimamaCookie = new TookAlimamaCookie();
		tookAlimamaCookie.setIsAvailable(IsAvailableEnum.YES_AVAILIABLE.getCode());
		List<TookAlimamaCookie> alimamaCookies = tookAlimamaCookieDao.find(tookAlimamaCookie);
		
	    if(alimamaCookies != null && alimamaCookies.size() > 0){
	    	for(TookAlimamaCookie alimamaCookie : alimamaCookies){
	    		requestAlimama(alimamaCookie);
	    		Thread.sleep(2000);  //线程暂停2s
	    	}
	    }		
		return ReturnT.SUCCESS;
	}
	
	/**
	 * 刷新一次session，包活cookie
	 * @param alimamaCookie
	 */
	private void requestAlimama(TookAlimamaCookie alimamaCookie){
		String url ="https://www.alimama.com/index.htm";
		HttpClientUtil.get(url, alimamaCookie.getAlimamaCookie());
		LOGGER.info("用户‘{}’请求[{}]地址成功",alimamaCookie.getName(),url);
	}

}