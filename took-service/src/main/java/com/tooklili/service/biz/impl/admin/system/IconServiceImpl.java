package com.tooklili.service.biz.impl.admin.system;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tooklili.dao.db.intf.admin.SysIconDao;
import com.tooklili.model.admin.SysIcon;
import com.tooklili.service.biz.intf.admin.system.IconService;
import com.tooklili.util.result.PageResult;

/**
 * 图标服务
 * @author shuai.ding
 * @date 2017年12月26日下午2:10:52
 */
@Service
public class IconServiceImpl implements IconService{
	
	@Resource
	private SysIconDao sysIconDao;

	@Override
	public PageResult<SysIcon> queryIcons(SysIcon icon, Integer currentPage, Integer pageSize) {
		if(currentPage==null){
			currentPage=1;
		}
		if(pageSize==null){
			pageSize=20;
		}
		PageResult<SysIcon> result = new PageResult<SysIcon>(currentPage,pageSize);
		PageBounds pageBounds = new PageBounds(currentPage,pageSize);
		PageList<SysIcon> pageList = sysIconDao.queryIconsByPage(icon, pageBounds);
		
		result.setData(pageList);
		result.setTotalCount(pageList.getPaginator().getTotalCount());
		
		return result;
	}

}
