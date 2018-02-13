package com.tooklili.service.biz.intf.admin.took;

import com.tooklili.model.admin.TookItemSearchKeyword;
import com.tooklili.util.result.BaseResult;
import com.tooklili.util.result.PageResult;

/**
 * 商品搜索关键字服务
 * @author ding.shuai
 * @date 2018年2月10日上午11:28:56
 */
public interface ItemSearchKeywordService {
	
	/**
	 * 分页查询
	 * @param tookItemSearchKeyword   实体
	 * @param currentPage             当前页
	 * @param pageSize                页面大小
	 * @return
	 */
	public PageResult<TookItemSearchKeyword> queryItemSearchKeywords(TookItemSearchKeyword tookItemSearchKeyword,Integer currentPage,Integer pageSize);
	
	/**
	 * 新增商品搜索关键字
	 * @param tookItemSearchKeyword
	 * @return
	 */
	public BaseResult addItemSearchKeyword(TookItemSearchKeyword tookItemSearchKeyword);
	
	/**
	 * 修改商品搜索关键字
	 * @param tookItemSearchKeyword
	 * @return
	 */
	public BaseResult modifyItemSearchKeyword(TookItemSearchKeyword tookItemSearchKeyword);
	
	/**
	 * 删除商品搜索关键字
	 * @param itemSearchKeywordId
	 * @return
	 */
	public BaseResult delItemSearchKeyword(Long itemSearchKeywordId);
	
	/**
	 * 关键字同步到redis中
	 * @return
	 */
	public BaseResult synToRedisAboutKewyword();

}
