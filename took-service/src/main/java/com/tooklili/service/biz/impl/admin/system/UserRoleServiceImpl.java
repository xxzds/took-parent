package com.tooklili.service.biz.impl.admin.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tooklili.dao.db.intf.admin.SysUserRoleDao;
import com.tooklili.model.admin.SysUserRole;
import com.tooklili.service.biz.intf.admin.system.UserRoleService;
import com.tooklili.service.exception.BusinessException;
import com.tooklili.util.result.BaseResult;
import com.tooklili.util.result.ListResult;

/**
 *  用户角色服务
 * @author shuai.ding
 * @date 2017年12月15日下午1:53:40
 */
@Service
public class UserRoleServiceImpl  implements UserRoleService{
	
	@Resource
	private SysUserRoleDao sysUserRoleDao;

	@Override
	public BaseResult addUserRole(Long userId, Long roleId) {
		BaseResult result = new BaseResult();
		
		SysUserRole sysUserRole = new SysUserRole();
		sysUserRole.setUserId(userId);
		sysUserRole.setRoleId(roleId);
		
		long count = sysUserRoleDao.insert(sysUserRole);
		if(count<=0){
			throw new BusinessException("用户角色关联关系插入失败");
		}		
		return result;
	}

	@Override
	public BaseResult delUserRole(Long userId) {
		BaseResult result = new BaseResult();
		
		if(userId==null){
			throw new BusinessException("删除用户-角色关联关系，用户ID不能为空");
		}
		
		sysUserRoleDao.delUserRoleByUserId(userId);		
		return result;
	}

	@Override
	public ListResult<SysUserRole> queryUserRole(SysUserRole sysUserRole) {
		ListResult<SysUserRole> result = new ListResult<SysUserRole>();
		List<SysUserRole> list = sysUserRoleDao.queryUserRole(sysUserRole);
		result.setData(list);
		return result;
	}

}
