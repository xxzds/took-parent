package com.tooklili.service.biz.impl.common;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tooklili.dao.redis.RedisCommonRepository;
import com.tooklili.service.biz.intf.common.ShortLinkService;
import com.tooklili.util.RandomUtils;
import com.tooklili.util.result.PlainResult;

/**
 * 自定义短连接服务
 * @author shuai.ding
 * @date: 2018年6月11日 下午8:12:48
 */
@Service
public class CustomShortLinkServiceImpl implements ShortLinkService{
	
	@Autowired
	private RedisCommonRepository redisCommonRepository;

	/**
	 * 返回的是key，短连接需要在controller层拼接
	 */
	@Override
	public PlainResult<String> getShortLinkUrl(String url) {
		PlainResult<String> result = new PlainResult<String>();
		String key = RandomUtils.randomChar(5);
		
		//尝试三次，防止出现key重复
		for(int i=0;i<3;i++) {
			String value = redisCommonRepository.getString(key,2);
			if(StringUtils.isEmpty(value)) break;
			key = RandomUtils.randomChar(5);
		}
		
		//存储15天
		redisCommonRepository.setString(key, url, 15*24*60*60, 2);		
		result.setData(key);
		return result;
	}

}
