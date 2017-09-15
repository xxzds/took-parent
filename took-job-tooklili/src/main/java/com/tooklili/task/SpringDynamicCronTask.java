package com.tooklili.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

/**
 * Spring动态周期定时任务
 * 在不停应用的情况下更改任务执行周期 
 * @author shuai.ding
 * @date 2017年9月15日下午3:15:01
 */
//@Component
//@EnableScheduling
public class SpringDynamicCronTask implements SchedulingConfigurer{
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringDynamicCronTask.class);
	
	 private static String cron;
	 
	public SpringDynamicCronTask() {
		cron = "0/5 * * * * ?";

		// 开启新线程模拟外部更改了任务执行周期
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(15 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				cron = "0/1 * * * * ?";
				System.err.println("cron change to: " + cron);
			}
		}).start();
	} 

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {		
		taskRegistrar.addTriggerTask(new Runnable() {
			
			@Override
			public void run() {
				LOGGER.info("-----------------------task run-----------------------");				
			}
		}, new Trigger() {
			
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				// 任务触发，可修改任务的执行周期  
                CronTrigger trigger = new CronTrigger(cron);
                Date nextExec = trigger.nextExecutionTime(triggerContext);  
                return nextExec;  
			}
		});
		
	}

}
