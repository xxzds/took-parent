package com.tooklili.dao.db.intf.admin;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tooklili.dao.db.intf.BaseDao;
import com.tooklili.model.admin.SysIcon;

/**
 * 图标持久层
 * @author shuai.ding
 * @date 2017年12月26日下午2:06:34
 */
public interface SysIconDao extends BaseDao<SysIcon,Long> {
	
	/**
	 * 分页查询图标信息
	 * @author shuai.ding
	 * @param sysIcon
	 * @param pageBounds
	 * @return
	 */
	public PageList<SysIcon> queryIconsByPage(@Param("sysIcon")SysIcon sysIcon,@Param("pageBounds")PageBounds pageBounds);

}