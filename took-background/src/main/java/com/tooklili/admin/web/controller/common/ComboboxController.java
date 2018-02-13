package com.tooklili.admin.web.controller.common;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tooklili.model.admin.SysRole;
import com.tooklili.model.admin.TookItemCate;
import com.tooklili.service.biz.intf.admin.system.RoleService;
import com.tooklili.service.biz.intf.admin.took.ItemCateService;

/**
 * 下拉框控制器
 * @author shuai.ding
 * @date 2017年12月15日上午11:51:01
 */
@Controller
@RequestMapping("/common")
public class ComboboxController {
	
	@Resource
	private RoleService roleService;
	
	@Resource
	private ItemCateService itemCateService;
	
	/**
	 * 角色列表
	 * @return
	 */
	@RequestMapping("/getRoles")
	@ResponseBody
	public List<SysRole> getRoles(){
		return roleService.findRoles(null).getData();
	}
	
	/**
	 * 商品分类列表
	 * @param isIncludeAll  是否包含全部
	 * @return
	 */
	@RequestMapping("/getItemCates")
	@ResponseBody
	public List<TookItemCate> getItemCates(boolean isIncludeAll){
		List<TookItemCate> tookItemCates = itemCateService.getItemCates(null).getData();
		
		if(isIncludeAll){
			//新增一个"全部"
			TookItemCate itemCate = new TookItemCate();
			itemCate.setId(-1L);
			itemCate.setItemCateName("全部");
			tookItemCates.add(0, itemCate);
		}		
		return tookItemCates;
	}

}
