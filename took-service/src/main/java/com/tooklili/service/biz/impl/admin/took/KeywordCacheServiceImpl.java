package com.tooklili.service.biz.impl.admin.took;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tooklili.dao.redis.RedisItemCateAndKeywordRepository;
import com.tooklili.enums.admin.ApiTypeEnum;
import com.tooklili.model.admin.TookKeywordDetail;
import com.tooklili.service.biz.intf.admin.took.KeywordCacheService;
import com.tooklili.util.result.ListResult;

/**
 * @author ding.shuai
 * @date 2018年2月11日下午11:14:31
 */
@Service
public class KeywordCacheServiceImpl implements KeywordCacheService{
	
	@Autowired
	private RedisItemCateAndKeywordRepository redisItemCateAndKeywordRepository;

	@Override
	public ListResult<TookKeywordDetail> queryKeywordDetailFromCache(ApiTypeEnum apiTypeEnum) {
		ListResult<TookKeywordDetail> result = new ListResult<TookKeywordDetail>();
		
		List<TookKeywordDetail> keywordDetails = redisItemCateAndKeywordRepository.queryKeywordDetail(apiTypeEnum);
		result.setData(keywordDetails);
		return result;
	}

}
