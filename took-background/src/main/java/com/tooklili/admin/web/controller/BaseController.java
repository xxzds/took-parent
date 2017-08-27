package com.tooklili.admin.web.controller;

import org.apache.commons.lang.StringUtils;

import com.tooklili.admin.web.permission.PermissionList;

/**
 * 基础控制器
 * @author shuai.ding
 * @date 2017年8月27日下午6:07:07
 */
public class BaseController {
	protected PermissionList permissionList = null;
	
	/**
	 * 权限前缀：如sys:user 则生成的新增权限为 sys:user:create
	 */
	public void setResourceIdentity(String resourceIdentity) {
		if (!StringUtils.isEmpty(resourceIdentity)) {
			permissionList = PermissionList.newPermissionList(resourceIdentity);
		}
	}
}
