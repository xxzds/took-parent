package com.tooklili.dao.intf.tooklili;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tooklili.model.tooklili.Item;

/**
 * 商品持久层
 * @author ding.shuai
 * @date 2017年9月19日下午9:34:21
 */
public interface ItemDao {
	
	public List<Item> queryItems(@Param("cateId") Integer cateId);
	
	public Item queryItemById(Long id);
}