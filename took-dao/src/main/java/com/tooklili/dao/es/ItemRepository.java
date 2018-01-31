package com.tooklili.dao.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import com.tooklili.model.tooklili.Item;

/**
 * 将优惠券商品信息存入es中，并进行crud操作
 * @author shuai.ding
 * @date 2018年1月31日下午4:36:12
 */
public interface ItemRepository extends ElasticsearchCrudRepository<Item, Long>{

}
