package com.tooklili.dao.db.intf.admin;

import com.tooklili.dao.db.intf.BaseDao;
import com.tooklili.model.tooklili.Item;

/**
 * 20元封顶商品 持久层
 * @author ding.shuai
 * @date 2018年5月20日下午5:02:12
 */
public interface TookItemTwentyDao extends BaseDao<Item,Long> {
	/**
	 * 通过淘宝商品id查询
	 * @param numIid
	 * @return
	 */
	Item queryItemByNumiid(Long numIid);
	
	
	/**
	 * 删除过期商品
	 * @return
	 */
	int delExpiredItems();
}