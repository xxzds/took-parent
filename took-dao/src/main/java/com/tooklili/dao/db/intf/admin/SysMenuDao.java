package com.tooklili.dao.db.intf.admin;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.tooklili.dao.db.intf.BaseDao;
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
	
	/**
	 * 查询菜单列表，可进行排序
	 * @author shuai.ding
	 * @param sysMenu
	 * @param orders
	 * @return
	 */
	public List<SysMenu> findMenuAndOrder(@Param("sysMenu") SysMenu sysMenu,@Param("orders")List<Order> orders);
}
