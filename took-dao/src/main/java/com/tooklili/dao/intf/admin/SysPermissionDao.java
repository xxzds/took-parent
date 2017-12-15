package com.tooklili.dao.intf.admin;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tooklili.dao.intf.BaseDao;
import com.tooklili.model.admin.SysPermission;

/**
 * 权限管理
 * @author shuai.ding
 *
 * @date 2017年12月15日上午9:26:02
 */
public interface SysPermissionDao extends BaseDao<SysPermission,Long> {
	
	/**
	 * 分页查询
	 * @author shuai.ding
	 * @param sysPermission  权限实体
	 * @param pageBounds     分页，排序实体
	 * @return
	 */
	public PageList<SysPermission> queryPermissionsByPage(@Param("sysPermission") SysPermission sysPermission,@Param("pageBounds")PageBounds pageBounds);

}