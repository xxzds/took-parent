package com.tooklili.dao.es;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import com.tooklili.model.es.Post; 


public interface PostRepository extends ElasticsearchCrudRepository<Post, String>{

}
