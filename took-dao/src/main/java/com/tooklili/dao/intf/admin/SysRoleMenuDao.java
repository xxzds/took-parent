package com.tooklili.dao.intf.admin;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tooklili.dao.intf.BaseDao;
import com.tooklili.model.admin.MenuAndPermissionModel;
import com.tooklili.model.admin.SysMenu;
import com.tooklili.model.admin.SysRoleMenu;

/**
 * @author shuai.ding
 * @date 2017年12月18日上午9:51:14
 */
public interface SysRoleMenuDao extends BaseDao<SysRoleMenu,Long> {
	
	/**
	 * 批量新增角色-菜单信息
	 * @author shuai.ding
	 * @param roleMenus
	 * @return
	 */
	public long batchAddRoleMenu(List<SysRoleMenu> roleMenus);
	
	/**
	 * 通过角色id和菜单id，删除关联
	 * @author shuai.ding
	 * @param menuIds
	 * @param roleId
	 * @return
	 */
	public long delRoleMenuByRoleIdAndMenuIds(@Param("menuIds")List<Long> menuIds,@Param("roleId")Long roleId);
	
	/**
	 * 通过角色id，删除关联
	 * @author shuai.ding
	 * @param roleId
	 * @return
	 */
	public long delRoleMenuByRoleId(Long roleId);
	
	/**
	 * 通过角色Id和菜单Ids,查询主键集合
	 * @author shuai.ding
	 * @param menuIds
	 * @param roleId
	 * @return
	 */
	public List<Long> queryIdByRoleIdAndMenuIds(@Param("menuIds")List<Long> menuIds,@Param("roleId")Long roleId);
	
	/**
	 * 通过用户id，查询用户所拥有的菜单集合
	 * @author shuai.ding
	 * @param userId
	 * @return
	 */
	public List<SysMenu> queryMenuByUserId(Long userId);
	
	
	/**
	 * 通过角色id,查询用户所拥有的菜单集合
	 * @author shuai.ding
	 * @param roleId
	 * @return
	 */
	public List<SysMenu> queryMenuByRoleId(Long roleId);
	
	/**
	 * 通过用户id，查询用户所拥有的叶子节点集合
	 * @author shuai.ding
	 * @param userId
	 * @return
	 */
	public List<MenuAndPermissionModel> queryLeafMenuByUserId(Long userId);
	
	/**
	 * 通过角色id,查询角色所拥有的的叶子节点集合
	 * @author shuai.ding
	 * @param roleId
	 * @return
	 */
	public List<MenuAndPermissionModel> queryLeafMenuByRoleId(Long roleId);

}