package com.tooklili.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tooklili.util.ClearCacheUtil;
import com.tooklili.util.HttpClientUtil;
import com.tooklili.util.PropertiesUtil;
import com.tooklili.util.TookliliCookieUtil;

/**
 * 采集商品
 * @author ding.shuai
 * @date 2017年8月15日下午10:10:51
 */
@Service
public class CollectItemsJob extends BaseJob{
	private static final Logger LOGGER = LoggerFactory.getLogger(CollectItemsJob.class);
	
	public CollectItemsJob() {
		this.setCorn(PropertiesUtil.getInstance("system.properties").getValue("collect_item_cron"));
	}

	@Override
	public void execute(){
		try{
			//女装-上衣
			this.collectItems("http://admin.tooklili.com/index.php?m=robots&a=collect&id=48&p=", 1, 3);
			//女装-裙装
			this.collectItems("http://admin.tooklili.com/index.php?m=robots&a=collect&id=50&p=", 1, 3);
			//女装-裤装
			this.collectItems("http://admin.tooklili.com/index.php?m=robots&a=collect&id=51&p=", 1, 3);
			//女装-特色服装
			this.collectItems("http://admin.tooklili.com/index.php?m=robots&a=collect&id=52&p=", 1, 3);
			
			//男装-上衣
			this.collectItems("http://admin.tooklili.com/index.php?m=robots&a=collect&id=53&p=", 1, 3);
			//男装-裤子
			this.collectItems("http://admin.tooklili.com/index.php?m=robots&a=collect&id=28&p=", 1, 3);
			//男装-内衣
			this.collectItems("http://admin.tooklili.com/index.php?m=robots&a=collect&id=29&p=", 1, 3);
			
			//鞋子-男鞋
			this.collectItems("http://admin.tooklili.com/index.php?m=robots&a=collect&id=24&p=", 1, 3);
			//鞋子-女鞋
			this.collectItems("http://admin.tooklili.com/index.php?m=robots&a=collect&id=25&p=", 1, 3);
			//鞋子-男包
			this.collectItems("http://admin.tooklili.com/index.php?m=robots&a=collect&id=26&p=", 1, 3);
			//鞋子-女包
			this.collectItems("http://admin.tooklili.com/index.php?m=robots&a=collect&id=27&p=", 1, 3);
			
			//母婴-孕妇
			this.collectItems("http://admin.tooklili.com/index.php?m=robots&a=collect&id=30&p=", 1, 3);
			//母婴-儿童
			this.collectItems("http://admin.tooklili.com/index.php?m=robots&a=collect&id=31&p=", 1, 3);
			
			//居家
			this.collectItems("http://admin.tooklili.com/index.php?m=robots&a=collect&id=33&p=", 1, 3);
			//美妆
			this.collectItems("http://admin.tooklili.com/index.php?m=robots&a=collect&id=34&p=", 1, 3);
			//数码
			this.collectItems("http://admin.tooklili.com/index.php?m=robots&a=collect&id=35&p=", 1, 3);
			//美食
			this.collectItems("http://admin.tooklili.com/index.php?m=robots&a=collect&id=36&p=", 1, 3);
			//文体
			this.collectItems("http://admin.tooklili.com/index.php?m=robots&a=collect&id=37&p=", 1, 3);
			//内衣
			this.collectItems("http://admin.tooklili.com/index.php?m=robots&a=collect&id=38&p=", 1, 3);
			//箱包
			this.collectItems("http://admin.tooklili.com/index.php?m=robots&a=collect&id=39&p=", 1, 3);
			//电器
			this.collectItems("http://admin.tooklili.com/index.php?m=robots&a=collect&id=40&p=", 1, 3);
			//其它
			this.collectItems("http://admin.tooklili.com/index.php?m=robots&a=collect&id=41&p=", 1, 3);
			//家纺
			this.collectItems("http://admin.tooklili.com/index.php?m=robots&a=collect&id=42&p=", 1, 3);
			
			//更新缓存
			ClearCacheUtil.clearCache();
		}catch(Exception e){
			LOGGER.error("exception",e);
		}		
	}
		
	private void collectItems(String urlPrefix,int start,int end) throws InterruptedException{
		for(int i=start;i<=end;i++){
			String url =urlPrefix+i;
			String content = HttpClientUtil.get(url, TookliliCookieUtil.getLoginCookies());
			LOGGER.info("url[{}]返回的内容：{}",url,content);
			
			Thread.sleep(100);
		}
	}
}
