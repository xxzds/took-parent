package com.tooklili.service.biz.intf.tooklili;

import com.tooklili.model.tooklili.Item;
import com.tooklili.util.result.PageResult;
import com.tooklili.util.result.PlainResult;

/**
 * 从redis缓存中取优惠券商品
 * @author ding.shuai
 * @date 2017年9月16日上午10:45:49
 */
public interface ItemService {
	/**
	 * 通过cateId查询优惠券商品列表
	 * @param cateId
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public PageResult<Item> queryCouponItemsByCateId(Integer cateId,Long currentPage,Long pageSize);
	
	/**
	 * 通过主键id查询商品
	 * @param id
	 * @return
	 */
	public PlainResult<Item> queryItemById(Long id);
}
