package com.tooklili.service.biz.impl.admin.took;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tooklili.dao.db.intf.admin.TookItemSearchKeywordDao;
import com.tooklili.dao.redis.RedisItemCateAndKeywordRepository;
import com.tooklili.model.admin.TookItemSearchKeyword;
import com.tooklili.service.biz.intf.admin.took.ItemSearchKeywordService;
import com.tooklili.service.exception.BusinessException;
import com.tooklili.util.result.BaseResult;
import com.tooklili.util.result.PageResult;

/**
 * @author ding.shuai
 * @date 2018年2月10日上午11:30:05
 */
@Service
public class ItemSearchKeywordServiceImpl implements ItemSearchKeywordService{
	
	@Autowired
	private TookItemSearchKeywordDao tookItemSearchKeywordDao;
	
	@Autowired
	private RedisItemCateAndKeywordRepository redisItemCateAndKeywordRepository;

	@Override
	public PageResult<TookItemSearchKeyword> queryItemSearchKeywords(TookItemSearchKeyword tookItemSearchKeyword,
			Integer currentPage, Integer pageSize) {
		if(currentPage==null){
			currentPage=1;
		}
		if(pageSize==null){
			pageSize=20;
		}
		PageResult<TookItemSearchKeyword> result = new PageResult<TookItemSearchKeyword>(currentPage,pageSize);
		PageBounds pageBounds = new PageBounds(currentPage,pageSize);
		PageList<TookItemSearchKeyword> pageList = tookItemSearchKeywordDao.queryItemSearchKeywordsByPage(tookItemSearchKeyword, pageBounds);
		
		result.setData(pageList);
		result.setTotalCount(pageList.getPaginator().getTotalCount());
		return result;
	}

	@Override
	public BaseResult addItemSearchKeyword(TookItemSearchKeyword tookItemSearchKeyword) {
		BaseResult result = new BaseResult();
		
		if(tookItemSearchKeyword == null){
			throw new BusinessException("新增商品搜索关键字，实体不能为空");
		}
		
		tookItemSearchKeywordDao.insertSelective(tookItemSearchKeyword);	
		return result;
	}

	@Override
	public BaseResult modifyItemSearchKeyword(TookItemSearchKeyword tookItemSearchKeyword) {
		BaseResult result = new BaseResult();
		
		if(tookItemSearchKeyword == null){
			throw new BusinessException("更新商品搜索关键字，实体不能为空");
		}
	    if(tookItemSearchKeyword.getId() == null){
	    	throw new BusinessException("更新商品搜索关键字，主键不能为空");
	    }
	    tookItemSearchKeywordDao.updateByIdSelective(tookItemSearchKeyword);		
		return result;
	}

	@Override
	public BaseResult delItemSearchKeyword(Long itemSearchKeywordId) {
		BaseResult result = new BaseResult();
		
		if(itemSearchKeywordId == null){
			throw new BusinessException("删除商品搜索关键字，主键不能为空");
		}
		tookItemSearchKeywordDao.deleteById(itemSearchKeywordId);
		return result;
	}

	@Override
	public BaseResult synToRedisAboutKewyword() {
		BaseResult result = new BaseResult();
		
		TookItemSearchKeyword tookItemSearchKeyword = new TookItemSearchKeyword();
		tookItemSearchKeyword.setIsAvailable(1);
		List<TookItemSearchKeyword> tookItemSearchKeywords = tookItemSearchKeywordDao.find(tookItemSearchKeyword);
		
		redisItemCateAndKeywordRepository.saveItemCateAndKeyword(tookItemSearchKeywords);
		return result;
	}
}
