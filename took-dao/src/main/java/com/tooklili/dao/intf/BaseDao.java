package com.tooklili.dao.intf;

import java.util.List;

/**
 * Dao通用接口
 * @author shuai.ding
 * @date 2017年8月26日上午11:29:51
 */
public interface BaseDao<T,K> {
	
	/**
     * 获取单个实体
     * @param id
     * @return
     */
    public T findById(K id);
    
    /**
     * 获取实体集合
     * @param entity
     * @return
     */
    public List<T> find(T entity);
    
    /**
     * 通过主键删除实体
     * @param id
     * @return
     */
    public long deleteById(K id);
    
    
    /**
     * 批量删除
     * @param ids
     * @return
     */
    public long batchDeleteByIds(K[] ids);
    
    	
	/**
	 * 全部插入
	 * @param entity
	 * @return  返回插入的个数
	 */
    public long insert(T entity);
    
    /**
	 * 选择性插入
	 * @param entity
	 * @return  返回插入的个数
	 */
    public long insertSelective(T entity);
    
    /**
     * 全部更新
     * @param entity
     * @return
     */
    public long updateById(T entity);
          
    /**
     * 选择性更新
     * @param entity
     * @return
     */
    public long updateByIdSelective(T entity);
}
