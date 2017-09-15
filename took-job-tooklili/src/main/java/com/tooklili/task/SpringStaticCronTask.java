package com.tooklili.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Spring静态周期定时任务
 * @author shuai.ding
 * @date 2017年9月15日下午4:00:04
 */
//@Component
public class SpringStaticCronTask {
	 private static final Logger logger = LoggerFactory.getLogger(SpringStaticCronTask.class);  
     
	    @Scheduled(cron="0/5 * * * * ?")  
	    public void staticCronTask() {  
	        logger.debug("staticCronTask is running...");  
	    }  
}
