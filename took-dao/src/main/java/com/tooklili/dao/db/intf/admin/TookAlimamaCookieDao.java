package com.tooklili.dao.db.intf.admin;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tooklili.dao.db.intf.BaseDao;
import com.tooklili.model.admin.TookAlimamaCookie;

/**
 * alimama cookie 持久层
 * @author ding.shuai
 * @date 2018年2月18日上午11:38:24
 */
public interface TookAlimamaCookieDao extends BaseDao<TookAlimamaCookie,Long> {
	
	/**
	 * 分页查询
	 * @param tookAlimamaCookie  实体
	 * @param pageBounds         分页，排序实体
	 * @return
	 */
	public PageList<TookAlimamaCookie> queryAlimamaCookiesByPage(@Param("tookAlimamaCookie")TookAlimamaCookie tookAlimamaCookie,@Param("pageBounds")PageBounds pageBounds);

}