package com.tooklili.task;

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
import com.tooklili.util.PropertiesUtil;

/**
 * 保活alimama登录的cookie
 * @author shuai.ding
 * @date 2017年10月18日下午2:57:30
 */
@Service
public class PersistenceAlimamaCookieJob extends BaseJob{
	
	private static final Logger LOGGER =LoggerFactory.getLogger(PersistenceAlimamaCookieJob.class);
		
	@Resource
	private RedisTemplate<?, ?> redisTemplate;
	
	private  static StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
	
	private static final  String ALIMAMACOOKIEKEY ="alimama_cookie";
	
	
	public PersistenceAlimamaCookieJob() {
		this.setCorn( PropertiesUtil.getInstance("system.properties").getValue("persist_alimama_cookie_cron"));
	}

	@Override
	public void execute() {
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
			HttpClientUtil.get("https://www.alimama.com/index.htm", cookie);
			LOGGER.info("请求地址[{}]成功",url);
		}		
	}

}
