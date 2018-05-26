package com.tooklili.service.alimama;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Maps;
import com.tooklili.dao.db.intf.tooklili.ItemDao;
import com.tooklili.enums.common.AlimamaItemCateEnum;
import com.tooklili.enums.common.ChannelEnum;
import com.tooklili.http.HttpCallService;
import com.tooklili.model.taobao.AlimamaItem;
import com.tooklili.model.taobao.AlimamaItemLink;
import com.tooklili.model.taobao.AlimamaReqItemModel;
import com.tooklili.model.taobao.DirectionalAlimamaReqItemModel;
import com.tooklili.model.tooklili.Item;
import com.tooklili.model.tooklili.ItemModel;
import com.tooklili.service.BaseTest;
import com.tooklili.service.biz.intf.taobao.AlimamaService;
import com.tooklili.service.biz.intf.tooklili.ItemOperService;
import com.tooklili.util.Arith;
import com.tooklili.util.DateUtil;
import com.tooklili.util.JsonFormatTool;
import com.tooklili.util.result.PageResult;
import com.tooklili.util.result.PlainResult;

public class AlimamaServiceTest  extends BaseTest{
	
	@Resource
	private HttpCallService httpCallService;
	
	@Resource
	private AlimamaService alimamaService;
	
	@Resource
	private ItemDao itemDao;
	
	@Resource(name = "itemEsOperServiceImpl")
	private ItemOperService itemOperService;
	
	@Test
	public void superSearchItemsTest() throws UnsupportedEncodingException{
		AlimamaReqItemModel alimamaReqItemModel = new AlimamaReqItemModel();
//		alimamaReqItemModel.setQ("https://item.taobao.com/item.htm?spm=a219t.7900221/10.1998910419.d30ccd691.465fa3568nG60d&id=546067706790");
		alimamaReqItemModel.setQ("服装");
		alimamaReqItemModel.setYxjh(1);
		alimamaReqItemModel.setToPage(10);
		alimamaReqItemModel.setPerPageSize(1);
		alimamaReqItemModel.setDpyhq(1);
		PageResult<AlimamaItem> result = alimamaService.superSearchItems(alimamaReqItemModel);
		logger.info(JsonFormatTool.formatJson(JSON.toJSONString(result)));
	}
	
	@Test
	public void generatePromoteLink(){
		PlainResult<AlimamaItemLink> result = alimamaService.generatePromoteLink("564261982480",1L);
		logger.info(JsonFormatTool.formatJson(JSON.toJSONString(result)));
	}
	
	@Test
	public void getCouponItemsTest() throws UnsupportedEncodingException, ParseException{
		AlimamaReqItemModel alimamaReqItemModel = new AlimamaReqItemModel();
		alimamaReqItemModel.setQ("女装上衣");
		alimamaReqItemModel.setYxjh(1);
		alimamaReqItemModel.setToPage(1);
		alimamaReqItemModel.setPerPageSize(1);
		alimamaReqItemModel.setDpyhq(1);
		alimamaReqItemModel.setB2c(1);
		alimamaReqItemModel.setSortType(9);
		PageResult<AlimamaItem> result = alimamaService.superSearchItems(alimamaReqItemModel);
		logger.info(JsonFormatTool.formatJson(JSON.toJSONString(result)));
		
		AlimamaItem alimamaItem = result.getData().get(0);
		
		Long numIid = alimamaItem.getAuctionId();
		Item item = itemDao.queryItemBynumId(numIid);
		
		ItemModel itemModel = new ItemModel();
		
		itemModel.setCouponStartTime(String.valueOf(DateUtil.parseDate(alimamaItem.getCouponEffectiveStartTime(),DateUtil.DEFAULT_DAY_STYLE).getTime()/1000));
		itemModel.setCouponEndTime(String.valueOf(DateUtil.parseDate(alimamaItem.getCouponEffectiveEndTime(),DateUtil.DEFAULT_DAY_STYLE).getTime()/1000));
		itemModel.setQuanSurplus(alimamaItem.getCouponTotalCount());
		itemModel.setQuanReceive(alimamaItem.getCouponTotalCount()-alimamaItem.getCouponLeftCount());
		itemModel.setCouponRate(String.valueOf(alimamaItem.getCouponLeftCount()));
		itemModel.setVolume(alimamaItem.getBiz30day().toString());
		itemModel.setAddTime(String.valueOf(new Date().getTime()/1000));
		String zkFinalPrice = alimamaItem.getZkPrice();
		itemModel.setPrice(zkFinalPrice);
		
		String couponInfo =alimamaItem.getCouponInfo();			
		String pattern="满(\\d+?)元减(\\d+?)元";			
		Matcher m = Pattern.compile(pattern).matcher(couponInfo);
		 if (m.find()) {
			 if(StringUtils.isNotEmpty(m.group(1))){
				 itemModel.setQuanCondition(m.group(1));
			 }else{
				 itemModel.setQuanCondition("");
			 }
			
			 itemModel.setQuan(m.group(2));
		 }	
		double couponPrice = Arith.sub(Double.valueOf(zkFinalPrice),Double.valueOf(itemModel.getQuan()));
		itemModel.setCouponPrice(String.valueOf(couponPrice));
		
		if(item!=null){ //更新
			itemModel.setId(item.getId());
			itemDao.updateItemById(itemModel);
			logger.info("更新数据库的商品主键为：{}",itemModel.getId());
		}else{  //insert
			itemModel.setCateId(35);
			itemModel.setNumIid(numIid);
			itemModel.setTitle(alimamaItem.getTitle());
			itemModel.setPicUrl(alimamaItem.getPictUrl());
			
			AlimamaItemLink alimamaItemLink =  alimamaService.generatePromoteLink(numIid.toString(),1L).getData();
			
			itemModel.setQuanUrl(alimamaItemLink.getCouponLink());
			
			itemModel.setIntro("");
			itemModel.setNick(alimamaItem.getNick());
			itemModel.setSellerId(alimamaItem.getSellerId());
			itemModel.setClickUrl(alimamaItemLink.getCouponLink());
			itemModel.setIsq(1);
			itemModel.setItemUrl(alimamaItem.getAuctionUrl());
			
			itemModel.setCommissionRate(alimamaItem.getTkRate().toString());			
			itemModel.setCommission(alimamaItem.getTkCommFee().toString());
					
			itemDao.insertItem(itemModel);
			logger.info("插入数据库的商品主键为：{}",itemModel.getId());
		}
		 
	}
	
	
	/**
	 * q:电风扇
_t:1506412177959
auctionTag:
perPageSize:40
shopTag:yxjh
t:1506412177968
_tb_token_:b3a7ee05ae6b
pvid:10_220.178.25.22_1388_1506412083091



q:电风扇 落地
_t:1506413980279
auctionTag:
perPageSize:40
shopTag:yxjh
t:1506413980282
_tb_token_:b3a7ee05ae6b
pvid:10_220.178.25.22_1590_1506413974418


q:手机
_t:1506418056375
auctionTag:
perPageSize:40
shopTag:yxjh
t:1506418056378
_tb_token_:b3a7ee05ae6b
pvid:10_220.178.25.22_1622_1506418050811

spm:a219t.7664554.1998457203.dfb730492.484397ae2XLta3
toPage:1
queryType:2
type:table
dpyhq:1
auctionTag:
perPageSize:40
shopTag:yxjh,dpyhq
t:1506419402654
_tb_token_:b3a7ee05ae6b
pvid:10_220.178.25.22_584_1506419397407

spm:a219t.7664554.1998457203.dfb730492.484397ae2XLta3
toPage:1
queryType:2
type:table
auctionTag:
perPageSize:40
shopTag:
t:1506419487994
_tb_token_:b3a7ee05ae6b
pvid:10_220.178.25.22_1773_1506419402203
	 * @author shuai.ding
	 */
	@Test
	public void millsToDateStr(){
		Date date =new Date(1506412177959L);   //毫秒数
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(simpleDateFormat.format(date));
	}
	
	/**
	 * 超级搜索接口
	 * @author shuai.ding
	 */
	@Test
	public void superSearchTest(){
		
		Map<String, String> params = Maps.newHashMap();
//		params.put("q", "男装");
		params.put("perPageSize","40");
		params.put("dpyhq", "1");  //店铺优惠券
//		params.put("shopTag","dpyhq");
		params.put("catIds", "50354021");
		
		PlainResult<String> result = httpCallService.httpGet("https://pub.alimama.com/items/search.json",params);
		
		
		String finalResult = JSON.parseObject(result.getData()).getJSONObject("data").getJSONArray("pageList").toJSONString();
		
		logger.info(JsonFormatTool.formatJson(finalResult));
		
	}
	
	@Test
	public void a(){
		
		Map<String, String> params = Maps.newHashMap();
		params.put("auctionid", "556495218786");
		params.put("adzoneid","2");
		params.put("siteid","2");
		params.put("scenes","2");
		params.put("t","2");
		params.put("_tb_token_","2");
		params.put("pvid","2");
		
		PlainResult<String> result = httpCallService.httpGet("http://pub.alimama.com/common/code/getAuctionCode.json",params);
		
		logger.info(JsonFormatTool.formatJson(result.getData()));
		
	}
	
	
	/**
	 * 智能提示
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void tipTest() throws UnsupportedEncodingException {
		Map<String, String> params = Maps.newHashMap();
		params.put("code", "utf-8");
		params.put("q",URLEncoder.encode("女士", "utf-8"));
		params.put("_",String.valueOf(new Date().getTime()));
		
		PlainResult<String> result = httpCallService.httpGet("https://suggest.taobao.com/sug",params);
		
		List<String> list = new ArrayList<String>();
		String data = result.getData();		
		JSONArray jsonArray = JSON.parseObject(data).getJSONArray("result");
		for(Object object:jsonArray) {
			JSONArray jsonArray2 = (JSONArray)object;
			list.add(jsonArray2.getString(0));
		}
		
		logger.info(JsonFormatTool.formatJson(JSON.toJSONString(list)));
	}
	
	@Test
	public void tehuiSearchItemsTest() {
		Map<String, String> params = Maps.newHashMap();
		params.put("channel", "tehui");
		params.put("toPage", "1");
		params.put("perPageSize","1");
		params.put("pvid", "18_112.27.197.5_5169_"+String.valueOf(new Date().getTime()));
		params.put("_t",String.valueOf(new Date().getTime()));
		params.put("catIds", "1");
		params.put("level", "1");
		params.put("dpyhq", "1");
		params.put("shopTag", "dpyhq");
		PlainResult<String> result = httpCallService.httpGet("https://pub.alimama.com/items/channel/tehui.json",params);
		logger.info(JsonFormatTool.formatJson(result.getData()));
	}
	
	@Test
	public void tehuiSuperSearchItemsTest() throws UnsupportedEncodingException, ParseException, InterruptedException {
		for(int i =1 ;i<2;i++) {
			DirectionalAlimamaReqItemModel tehuiAlimamaReqItemModel = new DirectionalAlimamaReqItemModel();
			tehuiAlimamaReqItemModel.setCatIds(AlimamaItemCateEnum.QI_CHE.getCateId());
			tehuiAlimamaReqItemModel.setLevel(1);
			tehuiAlimamaReqItemModel.setDpyhq(1);
			tehuiAlimamaReqItemModel.setSortType(9);
			
			tehuiAlimamaReqItemModel.setToPage(i);
			tehuiAlimamaReqItemModel.setPerPageSize(10);
			tehuiAlimamaReqItemModel.setChannel(ChannelEnum.TWENTY);
			
			PageResult<AlimamaItem> result = alimamaService.directionalSuperSearchItems(tehuiAlimamaReqItemModel);
//			logger.info(JsonFormatTool.formatJson(JSON.toJSONString(result)));
			
			itemOperService.insertOrUpdate(result.getData(), 11);
			
			Thread.sleep(80 *1000);
		}
		
	}
	
	@Test
	public void SearchItemsFor9k9Test() {
		Map<String, String> params = Maps.newHashMap();
		params.put("channel", "9k9");
		params.put("toPage", "1");
		params.put("perPageSize","1");
		params.put("_t",String.valueOf(new Date().getTime()));
		params.put("catIds", "1");
		params.put("level", "1");
		params.put("dpyhq", "1");
		params.put("shopTag", "dpyhq");
		params.put("t", "1525605822968");
		params.put("_tb_token_", "e1e13e0dbe77e");
		params.put("pvid", "16_211.162.8.83_659_1525605794704");
		PlainResult<String> result = httpCallService.httpGet("https://pub.alimama.com/items/channel/9k9.json",params);
		logger.info(JsonFormatTool.formatJson(result.getData()));
	}
}
