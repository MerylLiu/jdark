package com.iptv.core.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ServiceLocator implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		ServiceLocator.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static Object getService(String servName) {
		servName = String.format("%s%s", servName.substring(0, 1).toLowerCase(), servName.substring(1));

		try {
			Object bean = applicationContext.getBean(servName);
			return bean;
		} catch (Exception e) {
			return applicationContext.getBean(servName + "Impl");
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object getService(String servName, Class clazz) {
		servName = String.format("%s%s", servName.substring(0, 1).toLowerCase(), servName.substring(1));

		try {
			Object bean = applicationContext.getBean(servName, clazz);
			return bean;
		} catch (Exception e) {
			return applicationContext.getBean(servName + "Impl", clazz);
		}
	}
}
