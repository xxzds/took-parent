package com.tooklili.wechat.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tooklili.util.Config;

/**
 * 初始化配置监听器
 * @author shuai.ding
 * @date 2017年10月13日下午2:07:31
 */
public class InitConfigListener implements ServletContextListener{
	private static final Logger LOGGER = LoggerFactory.getLogger(InitConfigListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		 //先从jvm中获取参数，如果为空，从web容器上下文获取
		 String env = System.getProperty("spring.profiles.active");
		 if(env==null){
			 env = sce.getServletContext().getInitParameter("spring.profiles.active");
		 }
		 
		 if(env==null){
			 Config.ENV = "development";
		 }else{
			 Config.ENV=env;
		 }
		 LOGGER.info("ServletContext init,load config is {}",Config.ENV);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		LOGGER.info("ServletContext destroy");
	}

}
