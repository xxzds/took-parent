package com.tooklili.service.jobhandler;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

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
	@Resource
	private RedisTemplate<?, ?> redisTemplate;
	
	private  static StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
	
	private static final  String ALIMAMACOOKIEKEY ="alimama_cookie";

	@Override
	public ReturnT<String> execute(String... arg0) throws Exception {
		String cookie = redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] bytes = connection.get(stringRedisSerializer.serialize(ALIMAMACOOKIEKEY));
				if(bytes==null || bytes.length<=0){
					return null;
				}
				return stringRedisSerializer.deserialize(bytes);
			}
		});
		LOGGER.info("redis中key：{}对应的value：{}",ALIMAMACOOKIEKEY,cookie);
		
		//刷新一次服务器cookie所对应的session
		if(StringUtils.isNotEmpty(cookie)){
			String url ="https://www.alimama.com/index.htm";
			HttpClientUtil.get(url, cookie);
			LOGGER.info("请求地址[{}]成功",url);
		}		
		return ReturnT.SUCCESS;
	}

}