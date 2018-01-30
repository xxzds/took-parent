package com.tooklili.dao.db.intf.admin;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tooklili.dao.db.intf.BaseDao;
import com.tooklili.model.admin.SysRole;

/**
 * 角色
 * @author shuai.ding
 *
 * @date 2017年12月13日下午4:40:24
 */
public interface SysRoleDao extends BaseDao<SysRole,Long> {
	
	public PageList<SysRole> queryRolesByPage(@Param("sysRole")SysRole sysRole,@Param("pageBounds")PageBounds pageBounds);

}