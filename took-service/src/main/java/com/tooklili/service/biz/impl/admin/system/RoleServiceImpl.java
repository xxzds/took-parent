package com.tooklili.service.biz.impl.admin.system;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tooklili.dao.intf.admin.SysRoleDao;
import com.tooklili.model.admin.SysRole;
import com.tooklili.service.biz.intf.admin.system.RoleService;
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

}
