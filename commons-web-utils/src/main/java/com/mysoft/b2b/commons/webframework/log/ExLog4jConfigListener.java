package com.mysoft.b2b.commons.webframework.log;

import javax.servlet.ServletContextEvent;

import org.springframework.web.util.Log4jConfigListener;
import org.springframework.web.util.Log4jWebConfigurer;

public class ExLog4jConfigListener extends Log4jConfigListener {
    
	public void contextInitialized(ServletContextEvent event) {
		ExLog4jWebConfigurer.initLogging(event.getServletContext());
	}

	public void contextDestroyed(ServletContextEvent event) {
		Log4jWebConfigurer.shutdownLogging(event.getServletContext());
	}
	
}
