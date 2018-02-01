package com.tooklili.dao.es;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.util.List;

import javax.annotation.Resource;

import org.elasticsearch.script.Script;
import org.elasticsearch.search.sort.ScriptSortBuilder;
import org.elasticsearch.search.sort.ScriptSortBuilder.ScriptSortType;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Repository;

import com.tooklili.model.tooklili.Item;

/**
 * 通过elasticsearchTemplate操作es
 * @author shuai.ding
 *
 * @date 2018年2月1日下午2:15:10
 */
@Repository
public class EsItemRepository {
	
	@Resource
	private ElasticsearchTemplate elasticsearchTemplate;
	
	/**
	 * 查询商品集合
	 * @author shuai.ding
	 * @param cateId      分类id
	 * @param currentPage 当前页
	 * @param pageSize    页面大小
	 * @return
	 */
	public List<Item> queryItemsByCateId(Integer cateId, int currentPage, int pageSize){		
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("cateId", cateId))
				.withPageable(PageRequest.of(currentPage - 1, pageSize)).build();
//		searchQuery.addIndices("item");
		return elasticsearchTemplate.queryForList(searchQuery, Item.class);
	}
	
	/**
	 * 查询商品集合总数
	 * @author shuai.ding
	 * @param cateId        分类id
	 * @param currentPage   当前页
	 * @param pageSize      页面大小
	 * @return
	 */
	public long countItemsByCateId(Integer cateId, int currentPage, int pageSize){
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("cateId", cateId))
				.withPageable(PageRequest.of(currentPage - 1, pageSize)).build();
		return elasticsearchTemplate.count(searchQuery,Item.class);
	}
	
	/**
	 * 通过主键id，查询商品
	 * @author shuai.ding
	 * @param id
	 * @return
	 */
	public Item queryItemById(Long id){
		GetQuery getQuery = new GetQuery();
		getQuery.setId(String.valueOf(id));
		return elasticsearchTemplate.queryForObject(getQuery, Item.class);
	}
	
	/**
	 * 随机获取商品集合
	 * @author shuai.ding
	 * @param cateId     分类id
	 * @param size       返回商品集合大小
	 * @return
	 */
	public List<Item> queryRandomItemByCateId(Integer cateId, Integer size){
		NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
		
		if(cateId != null){
			nativeSearchQueryBuilder.withQuery(termQuery("cateId", cateId));
		}
		if(size != null){
			nativeSearchQueryBuilder.withPageable(PageRequest.of(0, size));
		}
		
		//随机排序
		ScriptSortBuilder sortBuilder = SortBuilders.scriptSort(new Script("Math.random()"), ScriptSortType.STRING);
		nativeSearchQueryBuilder.withSort(sortBuilder);

		return elasticsearchTemplate.queryForList(nativeSearchQueryBuilder.build(),Item.class);
	}
	
	/**
	 * 通过关键字查询字段title，返回集合
	 * @author shuai.ding
	 * @param keyword        关键字
	 * @param currentPage    当前页
	 * @param pageSize       页面大小
	 * @return
	 */
	public List<Item> queryItemsByKeyword(String keyword,int currentPage, int pageSize){
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("title", keyword))
				.withPageable(PageRequest.of(currentPage - 1, pageSize)).build();
		return elasticsearchTemplate.queryForList(searchQuery, Item.class);
	}
	
	/**
	 * 通过关键字查询字段title，返回总个数
	 * @author shuai.ding
	 * @param keyword       关键字
	 * @param currentPage   当前页
	 * @param pageSize      页面大小
	 * @return
	 */
	public long countItemsByKeyword(String keyword,int currentPage, int pageSize){
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery("title", keyword))
				.withPageable(PageRequest.of(currentPage - 1, pageSize)).build();
		return elasticsearchTemplate.count(searchQuery, Item.class);
	}

}
