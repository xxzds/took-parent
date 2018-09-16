package com.tooklili.service.jobhandler;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tooklili.dao.db.intf.admin.TookAlimamaCookieDao;
import com.tooklili.enums.admin.IsAvailableEnum;
import com.tooklili.model.admin.TookAlimamaCookie;
import com.tooklili.util.HttpClientUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;

/**
 * 保活alimama登录的cookie
 * @author ding.shuai
 * @date 2017年10月22日下午10:57:19
 */
@JobHander(value="persistenceAlimamaCookieJobHandler")
@Service
public class PersistenceAlimamaCookieJobHandler extends IJobHandler{
	
	@Autowired
	private TookAlimamaCookieDao tookAlimamaCookieDao;

	@Override
	public ReturnT<String> execute(String... arg0) throws Exception {
		//从数据库中查询alimama cookie列表
		TookAlimamaCookie tookAlimamaCookie = new TookAlimamaCookie();
		tookAlimamaCookie.setIsAvailable(IsAvailableEnum.YES_AVAILIABLE.getCode());
		List<TookAlimamaCookie> alimamaCookies = tookAlimamaCookieDao.find(tookAlimamaCookie);
		
		if (alimamaCookies != null && alimamaCookies.size() > 0) {
			for (TookAlimamaCookie alimamaCookie : alimamaCookies) {
				requestAlimama(alimamaCookie);
				Thread.sleep(2000); // 线程暂停2s
			}
		}		
		return ReturnT.SUCCESS;
	}
	
	/**
	 * 刷新一次session，保活cookie
	 * @param alimamaCookie
	 */
	private void requestAlimama(TookAlimamaCookie alimamaCookie){
//		String url ="http://wx.tooklili.com/common/getUnionPubContextInfo.json";
		
		String url= "https://www.alimama.com/index.htm";
		HttpClientUtil.get(url, alimamaCookie.getAlimamaCookie());
		String info =MessageFormat.format("用户‘{0}’请求[{1}]地址成功", alimamaCookie.getName(),url);
		XxlJobLogger.log(info);
//		String url2 = "https://www.alimama.com/getLogInfo.htm?callback=__jp0";
//		String content2 = HttpClientUtil.get(url2, alimamaCookie.getAlimamaCookie());
//		String info2 =MessageFormat.format("用户‘{0}’请求[{1}]地址成功,请求内容：{2}", alimamaCookie.getName(),url2,content2);
//
//		XxlJobLogger.log(info2);
	}

}