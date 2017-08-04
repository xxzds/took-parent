package com.tooklili.service.tbk;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.tooklili.service.api.tbk.TbkApiService;
import com.tooklili.util.result.PageResult;
import com.tooklili.vo.tbk.TbkItemReqVo;

/**
 * 淘宝客服务
 * @author shuai.ding
 *
 * @date 2017年8月4日下午5:11:22
 */
@Service
public class TbkService {
	
	@Resource
	private TbkApiService tbkApiService;
	
	public PageResult<TbkItemReqVo> getItems(TbkItemReqVo tbkItemReqVo){
		PageResult<TbkItemReqVo> result = new PageResult<TbkItemReqVo>(tbkItemReqVo.getPageNo(), tbkItemReqVo.getPageSize());
		
		
		return result;
	}
}
