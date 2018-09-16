package com.tooklili.service.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.tooklili.http.HttpCallService;
import com.tooklili.model.tooklili.TookItemSelect;
import com.tooklili.service.BaseTest;
import com.tooklili.service.biz.intf.admin.took.ItemSelectService;
import com.tooklili.util.Arith;
import com.tooklili.util.result.PlainResult;

/**
 * jsoupc 测试
 * @author ding.shuai
 * @date 2018年6月21日下午7:47:42
 */
public class JsoupTest2 extends BaseTest{
	
	@Resource
	private HttpCallService httpCallService;
	
	@Resource
	private ItemSelectService itemSelectService;
	
	@Test
	public void collectItemTest() {
		for(int i=1;i<=100;i++) {	
//			collectItem("美妆护肤", i,"5");
			collectItem("内衣裤特惠场", i,"3");
		}
		
		
		
	}
		
	
	private void collectItem(String kwd,Integer page,String cid) {
		Map<String, String> param = Maps.newHashMap();
		param.put("page", String.valueOf(page));
		param.put("status", "y");
		param.put("cid", cid);
		PlainResult<String> plainResult = httpCallService.httpPost("http://shop.tty900.com.cn/index.php?g=wap&r=ajax",param);		
		String html = plainResult.getData();
		Document doc = Jsoup.parse(html);		
	
		Elements lists = doc.getElementsByClass("list_1");
		List<TookItemSelect> tookItemSelects = new ArrayList<TookItemSelect>();
		for(Element list :lists) {
			TookItemSelect item = new TookItemSelect();
			item.setCateName(kwd);
			
			String href  = list.select("a").attr("href");
			System.out.println(href);
			itemDetailTest(item, href);
			
			String picUrl = list.select("a >img").attr("src");
			System.out.println(picUrl);
			item.setPicUrl(picUrl);
			
			Element div = list.getElementsByClass("list_1_out").first();
			
			String title = div.select(".list_1_title > .details >a").html();
			System.out.println(title);
			item.setTitle(title);
			
			String quan = div.select("a  > div.new-quan >div").html();
			System.out.println(quan);
			item.setQuan(quan.substring("￥".length()));
			
			String couponPrice = div.select("span.list_price >span").html();
			System.out.println(couponPrice);
			item.setCouponPrice(couponPrice.substring("￥".length()));
			
			String volume = div.select("span.purchase").html();
			System.out.println(volume);
			item.setVolume(volume.substring("销量".length()));
			
			double price = Arith.add(Double.parseDouble(item.getQuan()), Double.parseDouble(item.getCouponPrice()));
			System.out.println(price);
			item.setPrice(String.valueOf(price));
			
			logger.info("josn:{}",JSON.toJSONString(item));
			tookItemSelects.add(item);
			
		}
		
		if(tookItemSelects.size()>0) {
			itemSelectService.insertOrUpdate(tookItemSelects);
		}	
	}
	
	
	private void itemDetailTest(TookItemSelect item,String itemDetailUrl) {		
		PlainResult<String> plainResult = httpCallService.httpGet(itemDetailUrl);
		
		String html = plainResult.getData();		
		Document doc = Jsoup.parse(html);
		
		String shopType = "C";
		String iconUrl = doc.select("div.title-jieshao > p.title >img").attr("src");
		System.out.println(iconUrl);
		if(iconUrl.contains("tm1.png")) {
			shopType = "B";
		}
		System.out.println(shopType);
		item.setShopType(shopType);
		
		Elements e =  doc.getElementsByTag("script").eq(9);
		System.out.println(e);

		Long numIid = null;
		Pattern pt = Pattern.compile("goods_id=(\\d+)");
		Matcher match = pt.matcher(e.toString());
        if(match.find()) {
           	numIid = Long.parseLong(match.group(1));
        }
        item.setNumIid(numIid);
	}

}
