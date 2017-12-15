package com.tooklili.service.biz.impl.admin.system;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tooklili.dao.intf.admin.SysPermissionDao;
import com.tooklili.model.admin.SysPermission;
import com.tooklili.service.biz.intf.admin.system.PermissionService;
import com.tooklili.service.exception.BusinessException;
import com.tooklili.util.result.BaseResult;
import com.tooklili.util.result.PageResult;

/**
 * 权限
 * @author shuai.ding
 * @date 2017年12月15日上午9:42:59
 */
@Service
public class PermissionServiceImpl implements PermissionService{
	
	@Resource
	private SysPermissionDao sysPermissionDao;

	@Override
	public PageResult<SysPermission> findPermissions(SysPermission sysPermission, Integer currentPage, Integer pageSize) {
		if(currentPage==null){
			currentPage=1;
		}
		if(pageSize==null){
			pageSize=20;
		}
		PageResult<SysPermission> result = new PageResult<SysPermission>(currentPage,pageSize);
		PageBounds pageBounds = new PageBounds(currentPage,pageSize);		
		PageList<SysPermission> pageList = sysPermissionDao.queryPermissionsByPage(sysPermission, pageBounds);
		
		result.setData(pageList);
		result.setTotalCount(pageList.getPaginator().getTotalCount());
		
		return result;
	}

	@Override
	public BaseResult addPermission(SysPermission sysPermission) {
		BaseResult result = new BaseResult();
		
		if(sysPermission==null){
			throw new BusinessException("添加的权限实体不能为空");
		}
		
	    long count = sysPermissionDao.insert(sysPermission);
	    if(count<=0){
	    	throw new BusinessException("权限插入失败");
	    }
		
		return result;
	}

	@Override
	public BaseResult modifyPermission(SysPermission sysPermission) {
		BaseResult result = new BaseResult();
		
		if(sysPermission==null){
			throw new BusinessException("修改的权限实体不能为空");
		}
		if(sysPermission.getId()==null){
			throw new BusinessException("修改权限实体的主键不能为空");
		}
		
		long count = sysPermissionDao.updateByIdSelective(sysPermission);
		 if(count<=0){
	    	throw new BusinessException("权限修改失败");
	    }
		
		return result;
	}

	@Override
	public BaseResult delPermission(Long id) {
		BaseResult result = new BaseResult();
		
		if(id==null){
			throw new BusinessException("删除的权限主键不能为空");
		}
		
		long count = sysPermissionDao.deleteById(id);
		if(count<=0){
	    	throw new BusinessException("权限删除失败");
	    }
		
		return result;
	}

}
