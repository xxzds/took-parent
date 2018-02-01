package com.tooklili.dao.es.intf;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import com.tooklili.model.es.Post; 


/**
 * 测试
 * @author shuai.ding
 * @date 2018年1月31日下午4:32:42
 */
public interface PostRepository extends ElasticsearchCrudRepository<Post, String>{

}
