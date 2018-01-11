package com.iptv.core.common;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class JdfServletContextListener implements ServletContextListener {
	private static final String env = System.getenv("JAVA_ENV");

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		if (env != null && env.equals("production")) {
			System.setProperty("spring.profiles.active", "production");
		} else {
			System.setProperty("spring.profiles.active", "development");
		}
	}
}
