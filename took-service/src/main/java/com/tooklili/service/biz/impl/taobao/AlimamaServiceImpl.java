package com.tooklili.service.biz.impl.taobao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.tooklili.dao.db.intf.admin.TookAlimamaCookieDao;
import com.tooklili.enums.admin.IsAvailableEnum;
import com.tooklili.http.HttpCallService;
import com.tooklili.model.admin.TookAlimamaCookie;
import com.tooklili.model.taobao.AlimamaItem;
import com.tooklili.model.taobao.AlimamaItemLink;
import com.tooklili.model.taobao.AlimamaReqItemModel;
import com.tooklili.service.biz.intf.taobao.AlimamaService;
import com.tooklili.service.exception.BusinessException;
import com.tooklili.util.HttpClientUtil;
import com.tooklili.util.result.ListResult;
import com.tooklili.util.result.PageResult;
import com.tooklili.util.result.PlainResult;

/**
 * alimama服务
 * @author shuai.ding
 * @date 2017年9月26日下午3:51:59
 */
@Service
public class AlimamaServiceImpl implements AlimamaService{
	private static final Logger LOGGER = LoggerFactory.getLogger(AlimamaServiceImpl.class);
	
	@Resource
	private HttpCallService httpCallService;
	
	@Autowired(required=false)
	private TookAlimamaCookieDao tookAlimamaCookieDao;

	/**
	 * 调用接口失败
	 * {"data":{"head":{"version":"1.0","status":"NORESULT","pageNo":11,"pageSize":1,"searchUrl":null,"pvid":"100_10.103.67.17_11273_8921508558891787905","errmsg":"error! ha3 failed (search all feature failed)","fromcache":null,"processtime":7552,"ha3time":null,"docsfound":null,"docsreturn":null,"responseTxt":null},"condition":{"userType":null,"queryType":0,"sortType":null,"loc":null,"startDsr":null,"includeDxjh":null,"auctionTag":null,"freeShipment":null,"startTkRate":null,"endTkRate":null,"startTkTotalSales":null,"startPrice":null,"endPrice":null,"startRatesum":null,"endRatesum":null,"startQuantity":null,"startBiz30day":null,"startPayUv30":null,"hPayRate30":null,"hGoodRate":null,"jhs":null,"lRfdRate":null,"startSpay30":null,"hSellerGoodrat":null,"hSpayRate30":null,"hasUmpBonus":null,"isBizActivity":null,"subOeRule":null,"auctionTagRaw":null,"startRlRate":null,"shopTag":[{"value":"b2c","desc":"天猫旗舰店"},{"value":"dpyhq","desc":"包含店铺优惠券"},{"value":"yxjh","desc":"包含营销计划"}],"npxType":null,"picQuality":null,"selectedNavigator":null,"typeTagName":null},"paginator":null,"pageList":null,"navigator":null,"extraInfo":null},"info":{"message":null,"pvid":"10_220.178.25.22_5409_1508558891786","ok":true},"ok":true,"invalidKey":null}
	 * 调用接口成功
	 * {"data":{"head":{"version":"1.0","status":"OK","pageNo":51,"pageSize":1,"searchUrl":null,"pvid":"100_10.103.34.36_155543_5621508559049892042","errmsg":null,"fromcache":null,"processtime":24499,"ha3time":22265,"docsfound":1037,"docsreturn":1,"responseTxt":null},"condition":{"userType":null,"queryType":0,"sortType":null,"loc":null,"startDsr":null,"includeDxjh":null,"auctionTag":null,"jhs":null,"hasUmpBonus":null,"isBizActivity":null,"subOeRule":null,"auctionTagRaw":null,"freeShipment":null,"startTkRate":null,"endTkRate":null,"startTkTotalSales":null,"startPrice":null,"endPrice":null,"startRatesum":null,"endRatesum":null,"startQuantity":null,"startBiz30day":null,"startPayUv30":null,"hPayRate30":null,"hGoodRate":null,"lRfdRate":null,"startSpay30":null,"hSellerGoodrat":null,"hSpayRate30":null,"startRlRate":null,"shopTag":[{"value":"b2c","desc":"天猫旗舰店"},{"value":"dpyhq","desc":"包含店铺优惠券"},{"value":"yxjh","desc":"包含营销计划"}],"npxType":null,"picQuality":null,"selectedNavigator":null,"typeTagName":null},"paginator":{"length":1,"offset":500,"page":501,"beginIndex":501,"endIndex":501,"items":1037,"pages":1037,"itemsPerPage":1,"firstPage":1,"lastPage":1037,"previousPage":500,"nextPage":502,"slider":[498,499,500,501,502,503,504]},"pageList":[{"tkSpecialCampaignIdRateMap":{},"eventCreatorId":0,"rootCatId":0,"leafCatId":50025847,"debugInfo":null,"rootCatScore":0,"nick":"喜鹂旗舰店","userType":1,"title":"情侣棉拖鞋包跟女秋冬季家<span class=H>居家</span>厚底防滑棉鞋保暖鞋男彩虹拖鞋","sellerId":2243197133,"shopTitle":"喜鹂旗舰店","pictUrl":"//img.alicdn.com/bao/uploaded/i3/2243197133/TB16Q80SVXXXXX8XVXXXXXXXXXX_!!0-item_pic.jpg","auctionId":557256631017,"tkRate":5.50,"tkCommFee":0.81,"totalNum":1,"totalFee":0.23,"auctionUrl":"http://item.taobao.com/item.htm?id=557256631017","tkMktStatus":1,"couponActivityId":null,"reservePrice":56.00,"biz30day":21,"rlRate":73.57,"hasRecommended":null,"hasSame":null,"zkPrice":14.80,"tk3rdRate":null,"sameItemPid":"-2095925133","couponTotalCount":10000,"couponLeftCount":9982,"dayLeft":-17460,"couponAmount":1,"couponLink":"","couponLinkTaoToken":"","includeDxjh":0,"auctionTag":"587 843 907 1163 1478 1483 1995 2049 2059 3974 4166 4491 4550 5895 6603 8326 10571 11083 11339 12491 13707 13771 25282 28353 28802 30337 30401 30849 30977 37569 37633 39233 40897 51585 51905 67521 70465 74369 82306 101762 151362 202434","couponStartFee":13,"couponEffectiveStartTime":"2017-10-21","couponEffectiveEndTime":"2017-10-31","hasUmpBonus":null,"umpBonus":null,"isBizActivity":null,"couponShortLink":null,"couponInfo":"满13元减1元","eventRate":null,"rootCategoryName":null,"couponOriLink":null,"userTypeName":null}],"navigator":[{"name":"居家日用","id":21,"type":"category","level":1,"count":"286","flag":"qp_commend","subIds":[{"name":"居家鞋","id":50102692,"type":"category","level":1,"count":"212","flag":"qp_commend","subIds":null}]},{"name":"适用对象","id":122216608,"type":"property","level":1,"count":"3546","flag":"qp_commend","subIds":[{"name":"情侣","id":47698,"type":"property","level":1,"count":"2320","flag":"qp_commend","subIds":null},{"name":"青年","id":3267959,"type":"property","level":1,"count":"506","flag":"qp_commend","subIds":null},{"name":"女","id":20533,"type":"property","level":1,"count":"335","flag":"qp_commend","subIds":null},{"name":"一家三口","id":8609923,"type":"property","level":1,"count":"193","flag":"qp_commend","subIds":null},{"name":"中年","id":3267960,"type":"property","level":1,"count":"72","flag":"qp_commend","subIds":null},{"name":"儿童","id":27845,"type":"property","level":1,"count":"67","flag":"qp_commend","subIds":null},{"name":"男","id":20532,"type":"property","level":1,"count":"53","flag":"qp_commend","subIds":null}]},{"name":"风格","id":20608,"type":"property","level":1,"count":"3122","flag":"qp_commend","subIds":[{"name":"简约","id":109835873,"type":"property","level":1,"count":"1615","flag":"qp_commend","subIds":null},{"name":"卡通风","id":22107402,"type":"property","level":1,"count":"484","flag":"qp_commend","subIds":null},{"name":"韩式风","id":9587170,"type":"property","level":1,"count":"397","flag":"qp_commend","subIds":null},{"name":"公主风","id":6554818,"type":"property","level":1,"count":"192","flag":"qp_commend","subIds":null},{"name":"欧美风","id":8872005,"type":"property","level":1,"count":"107","flag":"qp_commend","subIds":null},{"name":"休闲","id":29535,"type":"property","level":1,"count":"65","flag":"qp_commend","subIds":null},{"name":"韩式","id":100196,"type":"property","level":1,"count":"64","flag":"qp_commend","subIds":null},{"name":"田园","id":29933,"type":"property","level":1,"count":"45","flag":"qp_commend","subIds":null},{"name":"中式","id":100197,"type":"property","level":1,"count":"38","flag":"qp_commend","subIds":null},{"name":"欧式","id":43746,"type":"property","level":1,"count":"27","flag":"qp_commend","subIds":null},{"name":"日式","id":67958,"type":"property","level":1,"count":"26","flag":"qp_commend","subIds":null},{"name":"日常休闲","id":3267766,"type":"property","level":1,"count":"18","flag":"qp_commend","subIds":null},{"name":"北欧风格","id":4124514,"type":"property","level":1,"count":"12","flag":"qp_commend","subIds":null},{"name":"古典民族风","id":51005203,"type":"property","level":1,"count":"8","flag":"qp_commend","subIds":null},{"name":"青春潮流","id":48816930,"type":"property","level":1,"count":"8","flag":"qp_commend","subIds":null},{"name":"日系","id":3411108,"type":"property","level":1,"count":"8","flag":"qp_commend","subIds":null},{"name":"法式","id":3782155,"type":"property","level":1,"count":"4","flag":"qp_commend","subIds":null},{"name":"美式","id":67956,"type":"property","level":1,"count":"4","flag":"qp_commend","subIds":null}]},{"name":"款式","id":122276315,"type":"property","level":1,"count":"2729","flag":"qp_commend","subIds":[{"name":"包头","id":46112,"type":"property","level":1,"count":"894","flag":"qp_commend","subIds":null},{"name":"一字型","id":139171,"type":"property","level":1,"count":"688","flag":"qp_commend","subIds":null},{"name":"露趾","id":3226297,"type":"property","level":1,"count":"335","flag":"qp_commend","subIds":null},{"name":"高包跟","id":652276131,"type":"property","level":1,"count":"259","flag":"qp_commend","subIds":null},{"name":"低包跟","id":652208858,"type":"property","level":1,"count":"254","flag":"qp_commend","subIds":null},{"name":"半包跟","id":90489642,"type":"property","level":1,"count":"187","flag":"qp_commend","subIds":null},{"name":"中包跟","id":590616644,"type":"property","level":1,"count":"64","flag":"qp_commend","subIds":null},{"name":"人字拖","id":134273,"type":"property","level":1,"count":"26","flag":"qp_commend","subIds":null},{"name":"交叉绑带","id":139181,"type":"property","level":1,"count":"22","flag":"qp_commend","subIds":null}]}],"extraInfo":null},"info":{"message":null,"pvid":"10_220.178.25.22_878_1508559049890","ok":true},"ok":true,"invalidKey":null}
	 * 通过status字段进行区别
	 */
	@Override
	public PageResult<AlimamaItem> superSearchItems(AlimamaReqItemModel alimamaReqItemModel) throws UnsupportedEncodingException {
		PageResult<AlimamaItem> result = new PageResult<AlimamaItem>();
				
		Map<String, String> params = Maps.newHashMap();
		
		//页面大小
		Integer pageSize = alimamaReqItemModel.getPerPageSize();
		if(pageSize==null){
			params.put("perPageSize", "40");
		}else{
			params.put("perPageSize", pageSize.toString());
		}
		
		//当前页
		Integer toPage = alimamaReqItemModel.getToPage();
		if(toPage!=null){
			params.put("toPage", toPage.toString());
		}
		
		//搜索的商品名称
		if(StringUtils.isNotEmpty(alimamaReqItemModel.getQ())){
			LOGGER.info("所搜商品的参数：{}",alimamaReqItemModel.getQ());
			params.put("q", URLEncoder.encode(alimamaReqItemModel.getQ(), "utf-8"));
		}
		
		//查询类型
		Integer queryType = alimamaReqItemModel.getQueryType();
		if(queryType==null){
			params.put("queryType", "0");  //默认排序
		}else{
			params.put("queryType", queryType.toString());
		}
		
		//排序类型
		if(alimamaReqItemModel.getSortType()!=null){
			params.put("sortType", alimamaReqItemModel.getSortType().toString());
		}
		
		//商品标签,默认为空
		String shopTag = "";
				
		//是否包邮
		Integer freeShipment = alimamaReqItemModel.getFreeShipment();
		if(freeShipment!=null){
			params.put("freeShipment", freeShipment.toString());
		}
		
		//是否是天猫旗舰店
		Integer b2c = alimamaReqItemModel.getB2c();
		if(b2c!=null){
			params.put("b2c", b2c.toString());
			shopTag += shopTag=="" ? "b2c":",b2c";
		}
		
		//是否包含店铺优惠券
		Integer dpyhq = alimamaReqItemModel.getDpyhq();
		if(dpyhq!=null){
			params.put("dpyhq", dpyhq.toString());
			shopTag += shopTag=="" ? "dpyhq":",dpyhq";
		}
		
		//是否月成交转化率高于行业均值
		Integer hPayRate30 = alimamaReqItemModel.gethPayRate30();
		if(hPayRate30!=null){
			params.put("hPayRate30", hPayRate30.toString());
		}
		
		//是否是营销和定向计划
		Integer yxjh = alimamaReqItemModel.getYxjh();
		if(yxjh!=null && yxjh==1){
			shopTag += shopTag=="" ? "yxjh":",yxjh";
		}
		
		//天猫
		Integer userType = alimamaReqItemModel.getUserType();
		if(userType!=null && userType==1){
			params.put("userType", "1");
		}
		
		//商品标签
		params.put("shopTag", shopTag);
		
		PlainResult<String> responseResult = httpCallService.httpGet("https://pub.alimama.com/items/search.json",params);
		
		JSONObject data = JSON.parseObject(responseResult.getData()).getJSONObject("data");
		//分页信息
		JSONObject head = data.getJSONObject("head");
		//判断是否返回数据
		String status = head.getString("status");
		if(status!= null && "OK".equals(status)){  //成功
			JSONObject paginator = data.getJSONObject("paginator");
			result.setCurrentPage(head.getIntValue("pageNo"));
			result.setPageSize(head.getIntValue("pageSize"));
			result.setTotalCount(paginator.getIntValue("items"));

			//商品信息
			List<AlimamaItem> datas = JSON.parseArray(data.getJSONArray("pageList").toJSONString(), AlimamaItem.class);
			result.setData(datas);
		}else{  //失败
			result.setData(new ArrayList<AlimamaItem>());
			result.setTotalCount(0);
			result.setCurrentPage(0);
			result.setPageSize(0);			
		}	
		return result;
	}

	@Override
	public PlainResult<AlimamaItemLink> generatePromoteLink(String auctionid,Long cookieId) {
		PlainResult<AlimamaItemLink> result = new PlainResult<AlimamaItemLink>();
		
		if(StringUtils.isEmpty(auctionid)){
			return result.setErrorMessage("auctionid不能为空");
		}
		
		TookAlimamaCookie tookAlimamaCookie = tookAlimamaCookieDao.findById(cookieId);
		if(tookAlimamaCookie == null || tookAlimamaCookie.getIsAvailable() == IsAvailableEnum.NO_AVAILIABLE.getCode()){
			LOGGER.info("cookieId:{}",cookieId);
			return result.setErrorMessage("用户标标识非法");
		}
		String pid = tookAlimamaCookie.getPid();
		if(StringUtils.isEmpty(pid)) {
			LOGGER.warn("cookieId:{}对用的pid不能为空",cookieId);
			return result.setErrorMessage("设置pid");
		}
		
		String siteid = null;  //媒体类型
		String adzoneid = null;  //渠道类型
		try {
			String[] temp = pid.split("_");
		    siteid = temp[2];
		    adzoneid = temp[3];
		}catch (Exception e) {
			throw new BusinessException("pid设置不正确");
		}
						
		String url="http://wx.tooklili.com/common/code/getAuctionCode.json";
//		url+=("?adzoneid="+adzoneid+"&siteid="+siteid+"&t="+new Date().getTime()+"&pvid=10_220.178.25.22_13754_"+new Date().getTime());
		url+=("?adzoneid="+adzoneid+"&siteid="+siteid+"&scenes=1&_tb_token_="+tookAlimamaCookie.getTbToken()+"&t="+new Date().getTime()+"&pvid=10_220.178.25.22_13754_"+new Date().getTime());
		url+=("&auctionid="+auctionid);	
		LOGGER.info("请求地址:{}",url);
		
		String cookie=tookAlimamaCookie.getAlimamaCookie();
		LOGGER.info("获取'{}'的cookie:{}",tookAlimamaCookie.getName(),cookie);
		String content = HttpClientUtil.get(url, cookie);
		LOGGER.info(content);
		
		AlimamaItemLink alimamaItemLink = JSON.parseObject(content).getObject("data", AlimamaItemLink.class);
		result.setData(alimamaItemLink);
		return result;
	}

	@Override
	public ListResult<String> suggest(String q) throws UnsupportedEncodingException {
		ListResult<String> result = new ListResult<String>();
		if(StringUtils.isEmpty(q)) {
			return result;
		}
		Map<String, String> params = Maps.newHashMap();
		params.put("code", "utf-8");
		params.put("q",URLEncoder.encode(q, "utf-8"));
		params.put("_",String.valueOf(new Date().getTime()));
		
		PlainResult<String> plainResult = httpCallService.httpGet("https://suggest.taobao.com/sug",params);
		if(!plainResult.isSuccess()) {
			LOGGER.info("调用接口失败，失败原因：{}",plainResult.getMessage());
			return result.setErrorMessage(plainResult.getMessage());
		}
		
		//解析返回的数据
		//{"result":[["女士春季衫","24.027244689557403"],["女士衫女 纯色","23.595556152701896"],["女士套装时尚","23.611882104403556"],["女士冬季外套","21.86277104101083"],["女士 上衣","23.64990784521851"],["女士时装外套","23.73661938475738"],["女士针织上衣","23.73815268075798"],["女士长款衣服","23.74286939131222"],["女士春季长袖","23.75152886301086"],["女士女士长","23.843351389218405"]]}
		List<String> list = new ArrayList<String>();
		String data = plainResult.getData();		
		JSONArray jsonArray = JSON.parseObject(data).getJSONArray("result");
		for(Object object:jsonArray) {
			JSONArray jsonArray2 = (JSONArray)object;
			list.add(jsonArray2.getString(0));
		}
		result.setData(list);
		return result;
	}

}