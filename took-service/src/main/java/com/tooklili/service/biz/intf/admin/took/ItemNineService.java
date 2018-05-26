package com.tooklili.service.biz.intf.admin.took;

import java.util.List;

import com.tooklili.model.taobao.AlimamaItem;
import com.tooklili.model.tooklili.Item;
import com.tooklili.util.result.BaseResult;
import com.tooklili.util.result.PlainResult;

/**
 * 超值9块9商品服务
 * @author ding.shuai
 * @date 2018年5月20日下午3:35:00
 */
public interface ItemNineService {
	
	/**
	 * 插入商品
	 * @param item
	 * @return
	 */
	BaseResult insertItemNine(Item item);
	
	
	/**
	 * 将采集的商品插入或更新到9块9商品表中
	 * @param alimamaItems
	 * @return
	 */
	PlainResult<String> insertOrUpdate(List<AlimamaItem> alimamaItems,Integer itemCateId);
	
	/**
	 * 删除过期商品
	 * @return
	 */
	PlainResult<Integer> delExpiredItems();

}
