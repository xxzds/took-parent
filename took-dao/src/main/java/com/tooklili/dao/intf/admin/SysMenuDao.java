package com.tooklili.dao.intf.admin;

import com.tooklili.dao.intf.BaseDao;
import com.tooklili.model.admin.SysMenu;

/**
 * 系统菜单
 * @author shuai.ding
 * @date 2017年8月26日下午4:38:04
 */
public interface SysMenuDao extends BaseDao<SysMenu, Long>{
	
	/**
	 * 通过pid查询子节点的个数
	 * @author shuai.ding
	 * @param pid
	 * @return
	 */
	public int getCountByPid(Long pid);
}
