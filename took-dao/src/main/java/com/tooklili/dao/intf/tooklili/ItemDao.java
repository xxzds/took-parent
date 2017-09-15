package com.tooklili.dao.intf.tooklili;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tooklili.model.tooklili.Item;

public interface ItemDao {

	public List<Item> queryItems(@Param("cateId") Integer cateId);
}
