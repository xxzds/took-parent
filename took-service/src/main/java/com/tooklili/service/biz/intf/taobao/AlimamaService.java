package com.tooklili.service.biz.intf.taobao;

import java.io.UnsupportedEncodingException;

import com.tooklili.model.taobao.AlimamaItem;
import com.tooklili.model.taobao.AlimamaItemLink;
import com.tooklili.model.taobao.AlimamaReqItemModel;
import com.tooklili.model.taobao.DirectionalAlimamaReqItemModel;
import com.tooklili.util.result.ListResult;
import com.tooklili.util.result.PageResult;
import com.tooklili.util.result.PlainResult;

/**
 * alimama服务
 * @author shuai.ding
 *
 * @date 2017年9月26日下午3:51:21
 */
public interface AlimamaService {
	
	/**
	 * 超级搜索
	 * @author shuai.ding
	 * @param alimamaReqItemModel
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public PageResult<AlimamaItem> superSearchItems(AlimamaReqItemModel alimamaReqItemModel) throws UnsupportedEncodingException;
	
	
	/**
	 * 生成推广链接
	 * 获取短链接、长链接、二维码、淘口令
	 * @author shuai.ding
	 * @return
	 */
	public PlainResult<AlimamaItemLink> generatePromoteLink(String auctionid,Long cookieId);
	
	
	/**
	 * 智能提示信息（建议），用在输入框的下拉提示上
	 * @param q  关键字
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public ListResult<String> suggest(String q) throws UnsupportedEncodingException;
	
	/**
	 * 定向搜索
	 * 超值9块9、20元封顶、特价好货
	 * @param directionalAlimamaReqItemModel
	 * @return
	 */
    public PageResult<AlimamaItem> directionalSuperSearchItems(DirectionalAlimamaReqItemModel directionalAlimamaReqItemModel);
}
