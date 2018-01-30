package com.tooklili.dao.db.intf.admin;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tooklili.dao.db.intf.BaseDao;
import com.tooklili.model.admin.SysUser;

/**
 * 系统用户
 * @author shuai.ding
 * @date 2017年8月26日上午11:53:10
 */
public interface SysUserDao extends BaseDao<SysUser, Long>{
	
	public PageList<SysUser> queryUsersByPage(@Param("sysUser")SysUser sysUser, @Param("pageBounds")PageBounds pageBounds);
	
}
