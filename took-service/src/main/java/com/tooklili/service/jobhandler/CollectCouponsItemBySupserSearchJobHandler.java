package com.tooklili.service.jobhandler;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.tooklili.dao.redis.RedisItemCateAndKeywordRepository;
import com.tooklili.enums.admin.ApiTypeEnum;
import com.tooklili.model.taobao.AlimamaItem;
import com.tooklili.model.taobao.AlimamaReqItemModel;
import com.tooklili.model.taobao.TookKeywordInfo;
import com.tooklili.service.biz.intf.taobao.AlimamaService;
import com.tooklili.service.biz.intf.tooklili.ItemOperService;
import com.tooklili.service.exception.BusinessException;
import com.tooklili.util.JsonFormatTool;
import com.tooklili.util.result.PageResult;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;

/**
 * 通过超级搜索接口，采集优惠券商品
 * @author shuai.ding
 *
 * @date 2017年10月21日上午11:16:50
 */
@JobHander(value="collectCouponsItemBySupserSearchJobHandler")
@Service
public class CollectCouponsItemBySupserSearchJobHandler extends IJobHandler{
	private static final Logger LOGGER = LoggerFactory.getLogger(CollectCouponsItemBySupserSearchJobHandler.class);
	
	@Resource
	private AlimamaService alimamaService;
	
	@Resource(name = "itemEsOperServiceImpl")
	private ItemOperService itemOperService;
	
	@Resource
	private RedisItemCateAndKeywordRepository redisItemCateAndKeywordRepository;

	@Override
	public ReturnT<String> execute(String... params) throws Exception {		
		//每次调用采集10次
		for(int i=0;i<10;i++){
			this.collectCouponItemBySuperSearch();
		}
		
		return ReturnT.SUCCESS;
	}
	
	private void collectCouponItemBySuperSearch() throws UnsupportedEncodingException, ParseException{
		//从redis中随机选取关键字
		TookKeywordInfo tookKeywordInfo = redisItemCateAndKeywordRepository.getRandomKeywordInfo(ApiTypeEnum.SUPER_SEARCH_API);	
		if(tookKeywordInfo == null) return;
		
		XxlJobLogger.log("选择的分类：" + JSON.toJSONString(tookKeywordInfo));
		
		//调用超级接口
		AlimamaReqItemModel alimamaReqItemModel = new AlimamaReqItemModel();
		
		alimamaReqItemModel.setQ(tookKeywordInfo.getKeyword());
		alimamaReqItemModel.setYxjh(1);
		alimamaReqItemModel.setToPage(tookKeywordInfo.getCurrentPage());
		alimamaReqItemModel.setPerPageSize(1);
		//包含店铺优惠券
		alimamaReqItemModel.setDpyhq(1);
		//天猫
		alimamaReqItemModel.setUserType(1);
		//销量从高到低
		alimamaReqItemModel.setSortType(9);
		PageResult<AlimamaItem> result = alimamaService.superSearchItems(alimamaReqItemModel);
		LOGGER.info(JsonFormatTool.formatJson(JSON.toJSONString(result)));
		
		if(!result.isSuccess()) {
			throw new BusinessException(result.getMessage());
		}
		
		if(result.getData().size()<=0){
			LOGGER.info("通过关键词[{}]查询第{}页的商品没有查到",tookKeywordInfo.getKeyword(),tookKeywordInfo.getCurrentPage());
			return;
		}		
		AlimamaItem alimamaItem = result.getData().get(0);
		
		itemOperService.insertOrUpdate(alimamaItem, tookKeywordInfo.getCateId());
	}
}
