package com.tooklili.service.biz.impl.admin.system;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tooklili.dao.intf.admin.SysMenuDao;
import com.tooklili.dao.intf.admin.SysRoleMenuDao;
import com.tooklili.dao.intf.admin.SysRoleMenuPermissionDao;
import com.tooklili.dao.intf.admin.SysUserRoleDao;
import com.tooklili.model.admin.MenuAndPermissionModel;
import com.tooklili.model.admin.SysMenu;
import com.tooklili.model.admin.SysPermission;
import com.tooklili.model.admin.SysRoleMenuPermission;
import com.tooklili.model.admin.SysUserRole;
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
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleMenuPermissionServiceImpl.class);
	
	@Resource
	private SysRoleMenuPermissionDao sysRoleMenuPermissionDao;
	
	@Resource
	private SysRoleMenuDao sysRoleMenuDao;
	
	@Resource
	private SysMenuDao sysMenuDao;
	
	@Resource
	private SysUserRoleDao sysUserRoleDao;
	
	@Resource
	private RedisTemplate<?,?> redisTemplate;

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
	public ListResult<String> getPermissionsByUserId(Long userId){
		ListResult<String> result = new ListResult<String>();
		if(userId==null){
			throw new BusinessException("通过用户，查询权限，用户信息不能为空");
		}
		
		//通过用户id获取用户角色
		SysUserRole sysUserRole = new SysUserRole();
		sysUserRole.setUserId(userId);
		List<SysUserRole> userRoles = sysUserRoleDao.find(sysUserRole);
		
		if(userRoles!=null && userRoles.size()>=0){
			
			result = this.getPermissionByRoleIds(Lists.transform(userRoles, new Function<SysUserRole, Long>() {
				@Override
				public Long apply(SysUserRole input) {
					
					return input.getRoleId();
				}
			}).toArray(new Long[]{}));
		}
				
		return result;
	}
	
	@Override
	public ListResult<String> getPermissionByRoleIds(Long[] roleIds) {
		ListResult<String> result = new ListResult<String>();
		
		if(roleIds == null || roleIds.length==0){
			throw new BusinessException("通过角色集合，查询权限，角色id集合不能为空");
		}
		
		//此处用set集合，去重
		Set<String> permissionSet = Sets.newHashSet();
		for(Long roleId : roleIds){
			List<String> permissions =  this.getPermissionByRoleId(roleId).getData();			
			permissionSet.addAll(permissions);
		}
		
		//将set转化成list
		List<String> permissionList = Lists.newArrayList();
		permissionList.addAll(permissionSet);
		
		result.setData(permissionList);		
		return result;
	}

	@Override
	public ListResult<String> getPermissionByRoleId(final Long roleId) {
		ListResult<String> result = new ListResult<String>();
		if(roleId == null){
			throw new BusinessException("通过角色，查询权限，角色id不能为空");
		}
		
		//从redis获取 start
		final String prefix = "permission-roleId-";
		final StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		String jsonStr =  redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {				
				byte[] bytes = connection.get(stringRedisSerializer.serialize(prefix+roleId));
				if(bytes==null || bytes.length<=0){
					return null;
				}
				return stringRedisSerializer.deserialize(bytes);
			}
		});
		
		if(jsonStr!=null){
			LOGGER.debug("角色Id：{},从redis中获取权限集合，{}",roleId,jsonStr);
			List<String> permissions =  JSON.parseArray(jsonStr, String.class);
			result.setData(permissions);
			return result;
		}
		//从redis获取 end

		//查询角色查询所拥有的所有叶子节点
		List<MenuAndPermissionModel> leafMenus = sysRoleMenuDao.queryLeafMenuByRoleId(roleId);
		
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
		
		//将角色对应的权限集合存入redis中
		final String jsonStrx = JSON.toJSONString(allPermissions);
		Boolean flag = redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {				
				connection.set(stringRedisSerializer.serialize(prefix+roleId), stringRedisSerializer.serialize(jsonStrx));
				return true;
			}
		});
		if(flag){
			LOGGER.debug("角色id:{},对应的权限集合:{},存入redis成功!",roleId,jsonStrx);
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
