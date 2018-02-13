package com.tooklili.dao.db.intf.admin;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tooklili.dao.db.intf.BaseDao;
import com.tooklili.model.admin.TookItemCate;

/**
 * 商品分类dao
 * @author ding.shuai
 * @date 2018年2月9日上午12:21:39
 */
public interface TookItemCateDao extends BaseDao<TookItemCate, Long>{
	
	/**
	 * 分页查询
	 * @param tookItemCate   商品分类实体
	 * @param pageBounds     分页、排序实体
	 * @return
	 */
	public PageList<TookItemCate> queryItemCatesByPage(@Param("tookItemCate")TookItemCate tookItemCate,@Param("pageBounds")PageBounds pageBounds);

}
