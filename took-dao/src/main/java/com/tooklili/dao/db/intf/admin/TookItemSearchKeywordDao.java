package com.tooklili.dao.db.intf.admin;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tooklili.dao.db.intf.BaseDao;
import com.tooklili.model.admin.TookItemSearchKeyword;

/**
 * 商品搜索关键字dao
 * @author ding.shuai
 * @date 2018年2月9日下午11:22:56
 */
public interface TookItemSearchKeywordDao extends BaseDao<TookItemSearchKeyword, Long>{
	
	/**
	 * 分页查询
	 * @param tookItemCate   商品搜索关键字实体
	 * @param pageBounds     分页、排序实体
	 * @return
	 */
	public PageList<TookItemSearchKeyword> queryItemSearchKeywordsByPage(@Param("tookItemSearchKeyword")TookItemSearchKeyword tookItemSearchKeyword,@Param("pageBounds")PageBounds pageBounds);
	
	/**
	 * 通过商品分类id删除商品搜索关键字
	 * @param itemCateId
	 * @return
	 */
	public long delItemSearchKeywordByItemCateId(Long itemCateId);
	
	/**
	 * 通过商品分类id更新商品搜索关键字不可用
	 * @param itemCateId
	 * @return
	 */
	public long updateItemSearchKeywordNotAvailableByItemCateId(Long itemCateId);

}
