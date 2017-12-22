package com.tooklili.service.biz.impl.admin.system;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.tooklili.dao.intf.admin.SysMenuDao;
import com.tooklili.dao.intf.admin.SysRoleMenuDao;
import com.tooklili.dao.intf.admin.SysRoleMenuPermissionDao;
import com.tooklili.model.admin.MenuAndPermissionModel;
import com.tooklili.model.admin.SysMenu;
import com.tooklili.model.admin.SysPermission;
import com.tooklili.model.admin.SysRoleMenuPermission;
import com.tooklili.model.admin.SysUser;
import com.tooklili.service.biz.intf.admin.system.RoleMenuPermissionService;
import com.tooklili.service.exception.BusinessException;
import com.tooklili.util.result.BaseResult;
import com.tooklili.util.result.ListResult;

/**
 * 角色-菜单，权限服务
 * @author shuai.ding
 * @date 2017年12月18日上午9:56:13
 */
@Service
public class RoleMenuPermissionServiceImpl implements RoleMenuPermissionService{
	
	@Resource
	private SysRoleMenuPermissionDao sysRoleMenuPermissionDao;
	
	@Resource
	private SysRoleMenuDao sysRoleMenuDao;
	
	@Resource
	private SysMenuDao sysMenuDao;

	@Override
	public ListResult<SysRoleMenuPermission> findRoleMenuPermissions(SysRoleMenuPermission sysRoleMenuPermission) {
		ListResult<SysRoleMenuPermission> result = new ListResult<SysRoleMenuPermission>();
		List<SysRoleMenuPermission> roleMenuPermissions = sysRoleMenuPermissionDao.find(sysRoleMenuPermission);
		result.setData(roleMenuPermissions);
		return result;
	}

	@Override
	@Transactional
	public BaseResult addRoleMenuPermissions(final Long roleMenuId, Long[] permissionIds) {
		BaseResult result = new BaseResult();
		
		if(roleMenuId==null){
			throw new BusinessException("角色-菜单ID不能为空");
		}
		
		//1.删除roleMenuId下的所有权限
		sysRoleMenuPermissionDao.delRoleMenuPermissionByRoleMenuId(roleMenuId);
		
		//2.增加权限
		if(permissionIds!=null && permissionIds.length>0){
			sysRoleMenuPermissionDao.batchAddRoleMenuPermission(Lists.transform(Arrays.asList(permissionIds), new Function<Long,SysRoleMenuPermission>() {

				@Override
				public SysRoleMenuPermission apply(Long input) {
					SysRoleMenuPermission roleMenuPermission = new SysRoleMenuPermission();
					roleMenuPermission.setRoleMenuId(roleMenuId);
					roleMenuPermission.setPermissionId(input);
					return roleMenuPermission;
				}				
			}));
		}		
		return result;
	}

	@Override
	public ListResult<String> getPermissionsByUser(SysUser user) {
		ListResult<String> result = new ListResult<String>();
		if(user==null){
			throw new BusinessException("通过用户，查询权限，用户信息不能为空");
		}
		
		//查询用户所拥有的所有叶子节点
		List<MenuAndPermissionModel> leafMenus = sysRoleMenuDao.queryLeftMenuByUserId(user.getId());
		
		List<String> allPermissions = Lists.newArrayList();
		for(MenuAndPermissionModel menuAndPermissionModel:leafMenus){
			Long roleMenuId = menuAndPermissionModel.getRoleMenuId();
			if(roleMenuId!=null){
				
				//查询角色所拥有的某个菜单中，所有权限
				List<SysPermission> permissions = sysRoleMenuPermissionDao.queryPermissionByRoleMenuId(roleMenuId);
				
				List<String> permissinsWithMenu = getMenuPermission(Lists.transform(permissions, new Function<SysPermission,String>() {
					@Override
					public String apply(SysPermission input) {
						return input.getPermissionIdentify();
					}				
				}), menuAndPermissionModel);
				
				//将菜单的权限添加到集合中
				allPermissions.addAll(permissinsWithMenu);
			}			
		}
		result.setData(allPermissions);
		return result;
	}
	
	/**
	 * 获取某菜单所有权限集合，最终权限的的形式（如 system：user:view）
	 * @author shuai.ding
	 * @param permissions
	 * @param sysMenu
	 * @return
	 */
	private List<String> getMenuPermission(List<String> permissions,SysMenu sysMenu){
		final String menuIdentify =sysMenu.getMenuIdentify();
		permissions = Lists.transform(permissions, new Function<String, String>() {

			@Override
			public String apply(String input) {				
				return menuIdentify +":"+input;
			}
		});
		
		Long parentId = sysMenu.getMenuParentId();		
		//如果是一级节点，直接返回
		if(parentId ==0L){
			return  permissions;
		}
		
		SysMenu parentMenu = sysMenuDao.findById(parentId);
		permissions = getMenuPermission(permissions, parentMenu);
		return  permissions;
	}

}
