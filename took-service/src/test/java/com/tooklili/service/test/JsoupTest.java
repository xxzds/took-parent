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
import com.tooklili.util.result.PlainResult;

/**
 * jsoupc 测试
 * @author ding.shuai
 * @date 2018年6月21日下午7:47:42
 */
public class JsoupTest extends BaseTest{
	
	@Resource
	private HttpCallService httpCallService;
	
	@Resource
	private ItemSelectService itemSelectService;
	
	@Test
	public void collectItemTest() {
		for(int i=1;i<=100;i++) {
			collectItem("纸巾",String.valueOf(i));
		}
	}
		
	
	private void collectItem(String kwd,String page) {
		Map<String, String> param = Maps.newHashMap();
		param.put("page", page);
		param.put("status", "s");
		param.put("ord", "0");
		param.put("kwd", kwd);
		param.put("cha", "cha");
		PlainResult<String> plainResult = httpCallService.httpPost("http://shop.tty900.com.cn/index.php?g=wap&r=ajax",param);		
		String html = plainResult.getData();
		Document doc = Jsoup.parse(html);		
		Elements lis = doc.select("li");
		List<TookItemSelect> tookItemSelects = new ArrayList<TookItemSelect>();
		for(Element li : lis) {
			TookItemSelect item = new TookItemSelect();
			item.setCateName(kwd);
			
			String href = li.select("a.cbp-vm-image").attr("href");
			System.out.println(href);
			itemDetailTest(item, href);
			
			String picUrl = li.select("a.cbp-vm-image > img").attr("src");
			System.out.println(picUrl);
			item.setPicUrl(picUrl);
			
			Element div = li.getElementsByClass("cbp-vm-group").first();
			
			String title = div.select(".cbp-vm-name>a>span").html();
			System.out.println(title);
			item.setTitle(title);
			
			String quan = div.select(".cbp-vm-ticket >span[class='num']").html();
			System.out.println(quan);
			item.setQuan(quan.substring("￥".length()));
			
			String couponPrice = div.select(".cbp-vm-price .price").html();
			System.out.println(couponPrice);
			item.setCouponPrice(couponPrice.substring("￥".length()));
			
			String volume = div.select(".cbp-vm-price .span_").html();
			System.out.println(volume);
			item.setVolume(volume.substring("销量 ".length()));
			
			String price = div.select(".cbp-vm-yuanjia").html();
			System.err.println(price);
			item.setPrice(price.substring("￥".length()));
			
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
