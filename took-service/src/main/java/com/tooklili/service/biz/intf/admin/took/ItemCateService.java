package com.tooklili.service.biz.intf.admin.took;

import com.tooklili.model.admin.TookItemCate;
import com.tooklili.util.result.BaseResult;
import com.tooklili.util.result.ListResult;
import com.tooklili.util.result.PageResult;

/**
 * 商品分类服务
 * @author ding.shuai
 * @date 2018年2月9日下午4:28:07
 */
public interface ItemCateService {
	
	/**
	 * 获取商品分类集合
	 * @param itemCate     商品服务实体
	 * @param currentPage  当前页
	 * @param pageSize     页面大小
	 * @return
	 */
	public PageResult<TookItemCate> getItemCates(TookItemCate itemCate,Integer currentPage,Integer pageSize);
	
	/**
	 * 商品分类集合
	 * @param itemCate
	 * @return
	 */
	public ListResult<TookItemCate> getItemCates(TookItemCate itemCate);
	
	/**
	 * 增加商品分类
	 * @param tookItemCate
	 * @return
	 */
	public BaseResult addItemCate(TookItemCate tookItemCate);
	
	/**
	 * 修改商品分类
	 * @param tookItemCate
	 * @return
	 */
	public BaseResult modifyItemCate(TookItemCate tookItemCate);
	
	/**
	 * 通过商品分类id，删除商品分类
	 * @param itemCateId
	 * @return
	 */
	public BaseResult delItemCate(Long itemCateId);
}
