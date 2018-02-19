package com.tooklili.admin.web.controller.took;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tooklili.admin.web.controller.BaseController;
import com.tooklili.admin.web.interceptor.annotation.RequiresPermissions;
import com.tooklili.enums.admin.IsAvailableEnum;
import com.tooklili.model.admin.TookAlimamaCookie;
import com.tooklili.service.biz.intf.admin.took.CookieAlimamaService;
import com.tooklili.util.result.BaseResult;
import com.tooklili.util.result.PageResult;

/**
 * cookie控制器
 * @author ding.shuai
 * @date 2018年2月18日下午12:31:27
 */
@Controller
@RequestMapping("took/cookie")
public class AlimamaCookieController extends BaseController{
	
	@Autowired
	private CookieAlimamaService cookieAlimamaService;
	
	/**
	 * 设置通用数据
	 */
	@Override
	protected void setCommonData(Model model) {
		//是否可用
		model.addAttribute("isAvailables", IsAvailableEnum.values());
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String mainn(Model model){
		this.setCommonData(model);
		return "took/alimama_cookie";
	}
	
	/**
	 * alimama cookie列表
	 * @param tookAlimamaCookie   实体
	 * @param page           当前页
	 * @param rows           页面大小
	 * @return
	 */
	@RequestMapping("/getCookies")
	@ResponseBody
	@RequiresPermissions("item:cookie:view")
	public PageResult<TookAlimamaCookie> getCookies(TookAlimamaCookie tookAlimamaCookie,Integer page,Integer rows){
		return cookieAlimamaService.getAlimamaCookies(tookAlimamaCookie, page, rows);
	} 
	
	/**
	 * 增加 alimama cookie
	 * @param tookAlimamaCookie
	 * @return
	 */
	@RequestMapping("/addAlimamaCookie")
	@ResponseBody
	@RequiresPermissions("item:cookie:add")
	public BaseResult addAlimamaCookie(TookAlimamaCookie tookAlimamaCookie){
		return cookieAlimamaService.addAlimamaCookie(tookAlimamaCookie);
	}
	
	/**
	 * 修改 alimama cookie
	 * @param tookAlimamaCookie
	 * @return
	 */
	@RequestMapping("/modifyAlimamaCookie")
	@ResponseBody
	@RequiresPermissions("item:cookie:modify")
	public BaseResult modifyAlimamaCookie(TookAlimamaCookie tookAlimamaCookie){
		return cookieAlimamaService.modifyAlimamaCookie(tookAlimamaCookie);
	}
	
	/**
	 * 删除 alimama cookie
	 * @param alimamaCookieId
	 * @return
	 */
	@RequestMapping("/delAlimamaCookie/{alimamaCookieId}")
	@ResponseBody
	@RequiresPermissions("item:cookie:delete")
	public BaseResult delAlimamaCookie(@PathVariable Long alimamaCookieId){
		return cookieAlimamaService.delAlimamaCookie(alimamaCookieId);
	}
	
	
	/**
	 * 名称是否不重复
	 * @param id    修改时的主键id
	 * @param name  名称
	 * @return
	 */
	@RequestMapping("/nameIfNotRepeat")
	@ResponseBody
	public boolean nameIfNotRepeat(Long id,String name){
		if(StringUtils.isEmpty(name)){
			return true;
		}
		TookAlimamaCookie tookAlimamaCookie = new TookAlimamaCookie();
		tookAlimamaCookie.setName(name);
		List<TookAlimamaCookie> list = cookieAlimamaService.getAlimamaCookies(tookAlimamaCookie).getData();
		if(list != null && list.size() >0){
			if(id == null) return false;
			if(id != null && list.get(0).getId() != id) return false;
		}
		return true;
	}
}
