package com.tooklili.dao.admin;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.tooklili.dao.BaseTest;
import com.tooklili.dao.db.intf.admin.SysUserRoleDao;
import com.tooklili.model.admin.SysUserRole;
import com.tooklili.util.JsonFormatTool;

public class SysUserRoleDaoTest extends BaseTest{
	
	@Resource
	private SysUserRoleDao sysUserRoleDao;

	@Test
	public void queryUserRoleTest(){
		try{
			SysUserRole sysUserRole = new SysUserRole();
			sysUserRole.setUserId(51L);
			
			List<SysUserRole> list = sysUserRoleDao.queryUserRole(sysUserRole);
			logger.info(JsonFormatTool.formatJson(JSON.toJSONString(list)));
		}catch(Exception e){
			logger.error("exception",e);
		}		
	}
}
