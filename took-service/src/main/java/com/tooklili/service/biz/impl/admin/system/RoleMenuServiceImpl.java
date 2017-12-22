package com.tooklili.service.biz.impl.admin.system;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.tooklili.dao.intf.admin.SysRoleMenuDao;
import com.tooklili.dao.intf.admin.SysRoleMenuPermissionDao;
import com.tooklili.model.admin.SysRoleMenu;
import com.tooklili.service.biz.intf.admin.system.RoleMenuService;
import com.tooklili.util.result.BaseResult;

/**
 * 角色-菜单服务
 * @author shuai.ding
 * @date 2017年12月18日上午9:55:51
 */
@Service
public class RoleMenuServiceImpl implements RoleMenuService{
	
	@Resource
	private SysRoleMenuDao sysRoleMenuDao;
	
	@Resource
	private SysRoleMenuPermissionDao sysRoleMenuPermissionDao;

	@Override
	@Transactional
	public BaseResult addRoleMenu(final Long roleId, Long[] menuIds) {
		BaseResult result = new BaseResult();
		
		if(roleId==null){
			return result.setErrorMessage("参数不合法");
		}
		
		if(menuIds==null){
			menuIds = new Long[]{};
		}
		
		//1.通过roleId查询数据库中存入的菜单集合
		SysRoleMenu sysRoleMenu = new SysRoleMenu();
		sysRoleMenu.setRoleId(roleId);
		List<SysRoleMenu> roleMenus =  sysRoleMenuDao.find(sysRoleMenu);
		
		List<Long> oldMenuIds = Lists.transform(roleMenus, new Function<SysRoleMenu,Long>() {
			@Override
			public Long apply(SysRoleMenu input) {				
				return input.getMenuId();
			}		
		});
		
		List<Long> newMenuIds = Arrays.asList(menuIds);
		
		List<Long> addMenuIds = Lists.newArrayList();
		List<Long> delMenuIds = Lists.newArrayList();
		
		//差集
		addMenuIds.clear();
		addMenuIds.addAll(newMenuIds);
		addMenuIds.removeAll(oldMenuIds);
		
		delMenuIds.clear();
		delMenuIds.addAll(oldMenuIds);
		delMenuIds.removeAll(newMenuIds);
		
		//新增的菜单
		if(addMenuIds.size()>0){
			sysRoleMenuDao.batchAddRoleMenu(Lists.transform(addMenuIds, new Function<Long, SysRoleMenu>() {

				@Override
				public SysRoleMenu apply(Long input) {
					SysRoleMenu sysRoleMenu = new SysRoleMenu();
					sysRoleMenu.setMenuId(input);
					sysRoleMenu.setRoleId(roleId);
					return sysRoleMenu;
				}
			}));
		}
		
		//删除的菜单,以及菜单关联的权限
		if(delMenuIds.size()>0){
			List<Long> roleMenuIds = sysRoleMenuDao.queryIdByRoleIdAndMenuIds(delMenuIds, roleId);
			//删除角色-菜单-权限数据
			sysRoleMenuPermissionDao.delRoleMenuPermissionByRoleMenuIds(roleMenuIds);
			//删除角色-菜单数据
			sysRoleMenuDao.delRoleMenuByRoleIdAndMenuIds(delMenuIds, roleId);
		}		
		return result;
	}

}
