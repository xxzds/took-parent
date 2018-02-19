package com.tooklili.service.biz.impl.admin.took;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tooklili.dao.db.intf.admin.TookAlimamaCookieDao;
import com.tooklili.model.admin.TookAlimamaCookie;
import com.tooklili.service.biz.intf.admin.took.CookieAlimamaService;
import com.tooklili.service.exception.BusinessException;
import com.tooklili.util.result.BaseResult;
import com.tooklili.util.result.ListResult;
import com.tooklili.util.result.PageResult;

/**
 * alimama cookie 维护服务
 * @author ding.shuai
 * @date 2018年2月18日下午12:13:03
 */
@Service
public class CookieAlimamaServiceImpl implements CookieAlimamaService{
	
	@Resource
	private TookAlimamaCookieDao tookAlimamaCookieDao;

	@Override
	public PageResult<TookAlimamaCookie> getAlimamaCookies(TookAlimamaCookie tookAlimamaCookie, Integer currentPage,
			Integer pageSize) {
		if(currentPage==null){
			currentPage=1;
		}
		if(pageSize==null){
			pageSize=20;
		}
		PageResult<TookAlimamaCookie> result = new PageResult<TookAlimamaCookie>(currentPage,pageSize);
		PageBounds pageBounds = new PageBounds(currentPage,pageSize);
		PageList<TookAlimamaCookie> pageList = tookAlimamaCookieDao.queryAlimamaCookiesByPage(tookAlimamaCookie, pageBounds);
		
		result.setData(pageList);
		result.setTotalCount(pageList.getPaginator().getTotalCount());
		return result;
	}
	
	@Override
	public ListResult<TookAlimamaCookie> getAlimamaCookies(TookAlimamaCookie tookAlimamaCookie){
		ListResult<TookAlimamaCookie> result = new ListResult<TookAlimamaCookie>();
		List<TookAlimamaCookie> alimamaCookies = tookAlimamaCookieDao.find(tookAlimamaCookie);		
		result.setData(alimamaCookies);
		return result;
	}

	@Override
	public BaseResult addAlimamaCookie(TookAlimamaCookie tookAlimamaCookie) {
		BaseResult result = new BaseResult();
		if(tookAlimamaCookie == null){
			throw new BusinessException("添加cookie，实体不能为空");
		}
		
		
		long count = tookAlimamaCookieDao.insertSelective(tookAlimamaCookie);
		if(count<= 0){
			result.setErrorMessage("alimama cookie"+tookAlimamaCookie+"插入数据库失败");
		}
		return result;
	}

	@Override
	public BaseResult modifyAlimamaCookie(TookAlimamaCookie tookAlimamaCookie) {
		BaseResult result = new BaseResult();
		if(tookAlimamaCookie == null){
			throw new BusinessException("实体不能为空");
		}
	    if(tookAlimamaCookie.getId() ==null){
	    	throw new BusinessException("实体id不能为空");
	    }
	    tookAlimamaCookieDao.updateByIdSelective(tookAlimamaCookie);		
		return result;
	}

	@Override
	public BaseResult delAlimamaCookie(Long alimamaCookieId) {
		BaseResult result = new BaseResult();
		
		if(alimamaCookieId == null){
			throw new BusinessException("实体id不能为空");
		}
		tookAlimamaCookieDao.deleteById(alimamaCookieId);
		return result;
	}
}
