package com.tooklili.service.biz.impl.admin.took;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tooklili.dao.db.intf.admin.TookItemCateDao;
import com.tooklili.dao.db.intf.admin.TookItemSearchKeywordDao;
import com.tooklili.enums.admin.IsAvailableEnum;
import com.tooklili.model.admin.TookItemCate;
import com.tooklili.service.biz.intf.admin.took.ItemCateService;
import com.tooklili.service.exception.BusinessException;
import com.tooklili.util.result.BaseResult;
import com.tooklili.util.result.ListResult;
import com.tooklili.util.result.PageResult;

/**
 * 商品分类服务
 * @author ding.shuai
 * @date 2018年2月9日下午4:29:19
 */
@Service
public class ItemCateServiceImpl implements ItemCateService{
	
	@Autowired
	private TookItemCateDao tookItemCateDao;
	
	@Autowired
	private TookItemSearchKeywordDao tookItemSearchKeywordDao;

	@Override
	public PageResult<TookItemCate> getItemCates(TookItemCate itemCate, Integer currentPage, Integer pageSize) {
		if(currentPage==null){
			currentPage=1;
		}
		if(pageSize==null){
			pageSize=20;
		}
		PageResult<TookItemCate> result = new PageResult<TookItemCate>(currentPage,pageSize);
		PageBounds pageBounds = new PageBounds(currentPage,pageSize,Order.formString("item_cate_sort.asc"));
		
		PageList<TookItemCate> pageList = tookItemCateDao.queryItemCatesByPage(itemCate, pageBounds);
		
		result.setData(pageList);
		result.setTotalCount(pageList.getPaginator().getTotalCount());
		return result;
	}
	
	@Override
	public ListResult<TookItemCate> getItemCates(TookItemCate itemCate) {
		ListResult<TookItemCate> result = new ListResult<TookItemCate>();		
		List<TookItemCate> itemCates = tookItemCateDao.find(itemCate);
		result.setData(itemCates);
		return result;
	}

	@Override
	public BaseResult addItemCate(TookItemCate tookItemCate) {
		BaseResult result = new BaseResult();
		if(tookItemCate == null){
			throw new BusinessException("添加商品分类，实体不能为空");
		}
		
		
		long count = tookItemCateDao.insertSelective(tookItemCate);
		if(count<= 0){
			result.setErrorMessage("商品分类"+tookItemCate+"插入数据库失败");
		}
		return result;
	}

	@Override
	@Transactional
	public BaseResult modifyItemCate(TookItemCate tookItemCate) {
		BaseResult result = new BaseResult();
		if(tookItemCate == null){
			throw new BusinessException("修改商品分类，实体不能为空");
		}
	    if(tookItemCate.getId() ==null){
	    	throw new BusinessException("修改商品分类，商品分类id不能为空");
	    }
		tookItemCateDao.updateByIdSelective(tookItemCate);
		
		//如果设置成不可用，同时关联的商品搜索关键字也不可用
		if(tookItemCate.getIsAvailable() == IsAvailableEnum.NO_AVAILIABLE.getCode()){
			tookItemSearchKeywordDao.updateItemSearchKeywordNotAvailableByItemCateId(tookItemCate.getId());
		}		
		return result;
	}

	@Override
	@Transactional
	public BaseResult delItemCate(Long itemCateId) {
		BaseResult result = new BaseResult();
		
		if(itemCateId == null){
			throw new BusinessException("删除商品分类，分类id不能为空");
		}
		tookItemCateDao.deleteById(itemCateId);
		
		//删除关联的商品搜索关键字
		tookItemSearchKeywordDao.delItemSearchKeywordByItemCateId(itemCateId);
		return result;
	}
}
