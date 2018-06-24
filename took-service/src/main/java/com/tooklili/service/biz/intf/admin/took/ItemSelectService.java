package com.tooklili.service.biz.intf.admin.took;

import java.util.List;

import com.tooklili.model.tooklili.TookItemSelect;
import com.tooklili.util.result.PlainResult;

/**
 * 人工选择的商品服务
 * @author ding.shuai
 * @date 2018年6月24日下午4:33:55
 */
public interface ItemSelectService {
	
	/**
	 * 插入或更新商品
	 * @param tookItemSelects
	 * @return
	 */
	PlainResult<String> insertOrUpdate(List<TookItemSelect> tookItemSelects);

}
