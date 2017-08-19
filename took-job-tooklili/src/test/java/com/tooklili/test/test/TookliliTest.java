package com.tooklili.test.test;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.tooklili.http.HttpCallService;
import com.tooklili.test.BaseTest;
import com.tooklili.util.result.PlainResult;
import com.tooklili.util.JsonFormatTool;


public class TookliliTest extends BaseTest{

	@Resource
	private HttpCallService httpCallService;
	
	
	@Test
	public void urlTest(){
		httpCallService.httpPost("http://www.tooklili.com/index.php?m=jump&a=index&id=60369");
	}
	
	@Test
	public void urlTest2(){
		Map<String, String> param= new HashMap<String,String>();
		param.put("cb", "jQuery162006229903063644837_1503135037146");
		param.put("pid", "mm_120259453_19682654_68664126");
		param.put("wt", "0");
		param.put("ti", "625");
		param.put("tl", "230x45");
		param.put("rd", "1");
		param.put("ct", "itemid=534012207716");
		param.put("st", "2");
		param.put("rf", "http://www.tooklili.com/index.php?m=jump&a=index&id=60369");
		param.put("et", "33239805");
		param.put("pgid", "d5bfdee5c55fe6d113f134c3c407c4bb");
		
		PlainResult<String> result = httpCallService.httpGet("http://g.click.taobao.com/display",param);
		logger.info(JsonFormatTool.formatJson(result.getData()));
	}
	
	
	@Test
	public void urlTest3(){
		httpCallService.httpGet("http://redirect.simba.taobao.com/rd?&f=http%3A%2F%2Fs.click.taobao.com%2Ft%3Fe%3Dm%253D2%2526s%253DHyoBQZkKw1ocQipKwQzePOeEDrYVVa64XoO8tOebS%252BduwRIiPOGbYJ9URCbiTn%252BEMMgx22UI05ZV6UHvZjTe1%252BEtm1zb2qbNnGKshE0Hf8QJtUsOVOUucQJfYMup5NY9KlR2qvKbP8wv7Q7IgQceuyDRAF4Oug%252FcjL%252BcI%252BkUeJvGDmntuH4VtA%253D%253D&k=7ca9e08409870ccd&p=mm_120259453_19682654_68664126&pvid=de5be111180db4306ae410ce09988d7b&posid=&b=display_1_625_0_0_0&w=unionapijs&c=un");
	}
	
	@Test
	public void urlTest4(){
		httpCallService.httpGet("http://pub.alimama.com/common/code/getAuctionCode.json");
	}
	
	@Test
	public void urlTest5(){
		httpCallService.httpGet("https://s.click.taobao.com/t?e=m%3D2%26s%3DEok4P1iHPFccQipKwQzePOeEDrYVVa64K7Vc7tFgwiHjf2vlNIV67oZilhHlY0kIByy0g7RzMQdC%2BIRp%2BAjlxQiw1d1b4EVEbBuslz09g9SokxCvfhVrrmMj7gz7UVKlypAbhWWbtaz4QP9EMGisuTQy0uNoXUcZCCwPdonooN4%3D&pvid=10_211.162.8.97_11386_1503133576034");
	}
	
}
