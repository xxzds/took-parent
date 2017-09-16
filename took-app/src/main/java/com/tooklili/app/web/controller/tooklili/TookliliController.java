package com.tooklili.app.web.controller.tooklili;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tooklili.model.tooklili.Item;
import com.tooklili.service.biz.api.tooklili.TookliliService;
import com.tooklili.util.result.PageResult;

/**
 * tooklili控制器
 * @author ding.shuai
 * @date 2017年9月16日上午11:36:21
 */
@Controller
public class TookliliController {
	
	@Resource
	private TookliliService tookliliService;
	
	@RequestMapping("/couponItems")
	@ResponseBody
	public PageResult<Item> queryCouponItemsByCateId(Integer cateId,Long currentPage,Long pageSize){
		return tookliliService.queryCouponItemsByCateId(cateId, currentPage, pageSize);
	}

}
