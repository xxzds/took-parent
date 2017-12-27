package com.tooklili.service.biz.intf.admin.system;

import com.tooklili.model.admin.SysIcon;
import com.tooklili.util.result.PageResult;

/**
 * 图标服务
 * @author shuai.ding
 * @date 2017年12月26日下午2:08:31
 */
public interface IconService {
	
	/**
	 * 获取图标列表
	 * @author shuai.ding
	 * @param icon         图标实体
	 * @param currentPage  当前页
	 * @param pageSize     页面大小
	 * @return
	 */
	public PageResult<SysIcon> queryIcons(SysIcon icon,Integer currentPage,Integer pageSize);

}
