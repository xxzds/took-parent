package com.tooklili.service.biz.intf.admin.took;

import com.tooklili.enums.admin.ApiTypeEnum;
import com.tooklili.model.admin.TookKeywordDetail;
import com.tooklili.util.result.ListResult;

/**
 * 关键字缓存服务
 * @author ding.shuai
 * @date 2018年2月11日下午11:12:24
 */
public interface KeywordCacheService {
	
	/**
	 * 关键字信息列表
	 * @param apiTypeEnum 接口类型枚举
	 * @return
	 */
	public ListResult<TookKeywordDetail> queryKeywordDetailFromCache(ApiTypeEnum apiTypeEnum);

}
