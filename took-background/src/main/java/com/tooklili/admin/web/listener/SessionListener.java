package com.tooklili.admin.web.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * session 监听器
 * @author shuai.ding
 * @date 2017年12月14日下午5:29:17
 */
public class SessionListener implements HttpSessionListener{
	private static final Logger LOGGER = LoggerFactory.getLogger(SessionListener.class);

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		LOGGER.info("session create:{}",se.getSession().getId());
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		LOGGER.info("session destroy:{}",se.getSession().getId());
	}

}
