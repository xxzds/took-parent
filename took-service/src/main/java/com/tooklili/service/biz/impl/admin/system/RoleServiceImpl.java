package com.tooklili.service.biz.impl.admin.system;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.tooklili.dao.db.intf.admin.SysRoleDao;
import com.tooklili.dao.db.intf.admin.SysRoleMenuDao;
import com.tooklili.dao.db.intf.admin.SysRoleMenuPermissionDao;
import com.tooklili.dao.db.intf.admin.SysUserRoleDao;
import com.tooklili.model.admin.SysRole;
import com.tooklili.model.admin.SysRoleMenu;
import com.tooklili.service.biz.intf.admin.system.RoleService;
import com.tooklili.service.exception.BusinessException;
import com.tooklili.util.result.BaseResult;
import com.tooklili.util.result.ListResult;
import com.tooklili.util.result.PageResult;

/**
 * 角色服务
 * @author shuai.ding
 * @date 2017年12月13日下午4:41:59
 */
@Service
public class RoleServiceImpl implements RoleService{
	
	@Resource
	private SysRoleDao sysRoleDao;
	
	@Resource
	private SysUserRoleDao sysUserRoleDao;
	
	@Resource
	private SysRoleMenuDao sysRoleMenuDao;
	
	@Resource
	private SysRoleMenuPermissionDao sysRoleMenuPermissionDao;

	@Override
	public PageResult<SysRole> findRoles(SysRole sysRole, Integer currentPage, Integer pageSize) {
		if(currentPage==null){
			currentPage=1;
		}
		if(pageSize==null){
			pageSize=20;
		}
		PageResult<SysRole> result = new PageResult<SysRole>(currentPage,pageSize);
		PageBounds pageBounds = new PageBounds(currentPage,pageSize);		
		PageList<SysRole> pageList = sysRoleDao.queryRolesByPage(sysRole, pageBounds);
		
		result.setData(pageList);
		result.setTotalCount(pageList.getPaginator().getTotalCount());
		return result;
	}

	@Override
	public ListResult<SysRole> findRoles(SysRole sysRole) {
		ListResult<SysRole> result = new ListResult<SysRole>();
		List<SysRole> sysRoles = sysRoleDao.find(sysRole);
		result.setData(sysRoles);
		return result;
	}

	@Override
	public BaseResult addRole(SysRole role) {
		BaseResult result = new BaseResult();
		
		if(role == null){
			throw new BusinessException("添加角色，实体不能为空");
		}
		
		//添加创建时间
		if (role.getCreateTime()==null) {
			role.setCreateTime(new Date());
		}
		
		long count = sysRoleDao.insert(role);
		if(count<=0){
			result.setErrorMessage("角色"+role+"插入数据库失败");
		}		
		return result;
	}

	@Override
	public BaseResult modifyRole(SysRole role) {
		BaseResult result = new BaseResult();
		
		if(role == null){
			throw new BusinessException("修改角色，实体不能为空");
		}
		
		if(role.getId()==null){
			throw new BusinessException("修改角色，角色id不能为空");
		}
		
		//添加修改时间
		if(role.getModifyTime() == null){
			role.setModifyTime(new Date());
		}
		
		long count = sysRoleDao.updateByIdSelective(role);
		if(count<=0){
			result.setErrorMessage("角色"+role+"修改失败");
		}
		return result;
	}

	@Override
	@Transactional
	public BaseResult delRole(Long roleId) {
		BaseResult result = new BaseResult();
		
		if(roleId == null){
			throw new BusinessException("删除角色，角色id不能为空");
		}
		
		//1.删除用户与角色的关联信息
		sysUserRoleDao.delUserRoleByRoleId(roleId);
		
		//2.删除角色与菜单权限的关联信息
		SysRoleMenu sysRoleMenu = new SysRoleMenu();
		sysRoleMenu.setRoleId(roleId);
		List<SysRoleMenu> roleMenus = sysRoleMenuDao.find(sysRoleMenu);
		
		if(roleMenus!=null && roleMenus.size()>0){
			sysRoleMenuPermissionDao.delRoleMenuPermissionByRoleMenuIds(Lists.transform(roleMenus, new Function<SysRoleMenu, Long>() {
				@Override
				public Long apply(SysRoleMenu input) {
					return input.getId();
				}
			}));
			sysRoleMenuDao.delRoleMenuByRoleId(roleId);
		}
				
		//3.删除角色信息 
		sysRoleDao.deleteById(roleId);
				
		return result;
	}

}
