package com.tooklili.service.biz.intf.admin.took;

import com.tooklili.model.admin.TookAlimamaCookie;
import com.tooklili.util.result.BaseResult;
import com.tooklili.util.result.ListResult;
import com.tooklili.util.result.PageResult;

/**
 * alimama cookie 维护服务
 * @author ding.shuai
 * @date 2018年2月18日下午12:13:03
 */
public interface CookieAlimamaService {
	
	/**
	 * alimama cookie 集合
	 * @param tookAlimamaCookie  alimama cookie 实体
	 * @param currentPage        当前页
	 * @param pageSize           页面大小
	 * @return
	 */
	public PageResult<TookAlimamaCookie> getAlimamaCookies(TookAlimamaCookie tookAlimamaCookie,Integer currentPage,Integer pageSize);
	
	/**
	 * alimama cookie 集合
	 * @param tookAlimamaCookie
	 * @return
	 */
	public ListResult<TookAlimamaCookie> getAlimamaCookies(TookAlimamaCookie tookAlimamaCookie);
	
	/**
	 * 增加alimama cookie
	 * @param tookAlimamaCookie
	 * @return
	 */
	public BaseResult addAlimamaCookie(TookAlimamaCookie tookAlimamaCookie);
	
	/**
	 * 修改alimama cookie
	 * @param tookAlimamaCookie
	 * @return
	 */
	public BaseResult modifyAlimamaCookie(TookAlimamaCookie tookAlimamaCookie);
	
	/**
	 * 删除alimama cookie
	 * @param alimamaCookieId
	 * @return
	 */
	public BaseResult delAlimamaCookie(Long alimamaCookieId);

}
