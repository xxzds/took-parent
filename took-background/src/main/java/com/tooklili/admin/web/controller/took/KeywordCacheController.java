package com.tooklili.admin.web.controller.took;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tooklili.admin.web.controller.BaseController;
import com.tooklili.admin.web.interceptor.annotation.RequiresPermissions;
import com.tooklili.enums.admin.ApiTypeEnum;
import com.tooklili.model.admin.TookKeywordDetail;
import com.tooklili.service.biz.intf.admin.took.KeywordCacheService;
import com.tooklili.util.result.ListResult;

/**
 * 关键字缓存管理
 * @author ding.shuai
 * @date 2018年2月11日下午10:31:46
 */
@Controller
@RequestMapping("took/cache")
@RequiresPermissions("item:keywordcache:view")
public class KeywordCacheController extends BaseController{
	
	@Autowired
	private KeywordCacheService keywordCacheService;
	
	@Override
	protected void setCommonData(Model model) {
		model.addAttribute("apiTypes", ApiTypeEnum.values());
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String main(Model model){
		setCommonData(model);
		return "took/keyword_cache";
	}
	
	@RequestMapping("/queryKeywordDetails")
	@ResponseBody
	public ListResult<TookKeywordDetail> queryKeywordDetails(Integer type){
		ListResult<TookKeywordDetail> result = new ListResult<TookKeywordDetail>();
		if(type ==null || ApiTypeEnum.valueOf(type) == null){
			return result.setErrorMessage("类型错误");
		}
		
		result = keywordCacheService.queryKeywordDetailFromCache(ApiTypeEnum.valueOf(type));
		return result;
	}
 
}
