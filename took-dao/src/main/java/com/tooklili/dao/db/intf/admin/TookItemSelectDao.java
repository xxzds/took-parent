package com.tooklili.dao.db.intf.admin;

import com.tooklili.dao.db.intf.BaseDao;
import com.tooklili.model.tooklili.TookItemSelect;

/**
 * 人工选择的商品 持久层
 * @author ding.shuai
 * @date 2018年6月24日下午4:30:08
 */
public interface TookItemSelectDao extends BaseDao<TookItemSelect,Long> {
	
	/**
	 * 通过淘宝商品id查询
	 * @param numIid
	 * @return
	 */
	TookItemSelect queryItemByNumiid(Long numIid);
	
	/**
	 * 删除过期商品
	 * @return
	 */
	int delExpiredItems();

}