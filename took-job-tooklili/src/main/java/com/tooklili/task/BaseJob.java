package com.tooklili.task;

import java.util.Date;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

public abstract class BaseJob implements SchedulingConfigurer{
	
	/**
	 * 任务周期
	 */
	private String corn="0 0 1 * * ?";
	
	/**
	 * 任务内容
	 * @author shuai.ding
	 */
	public abstract void execute();

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.addTriggerTask(new Runnable() {			
			@Override
			public void run() {
				execute();				
			}
		}, new Trigger() {			
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				// 任务触发，可修改任务的执行周期 
                CronTrigger trigger = new CronTrigger(getCorn());
                Date nextExec = trigger.nextExecutionTime(triggerContext);  
                return nextExec;  
			}
		});		
	}

	public String getCorn() {
		return corn;
	}

	public void setCorn(String corn) {
		this.corn = corn;
	}

}
