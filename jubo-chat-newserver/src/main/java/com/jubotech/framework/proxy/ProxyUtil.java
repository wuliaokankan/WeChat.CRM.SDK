package com.jubotech.framework.proxy;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 获取SpringBean的工具类
 */
@SuppressWarnings("unchecked")
@Component
public class ProxyUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ProxyUtil.applicationContext = applicationContext;
	}

	public static <T> T getBean(String name) throws BeansException {
		return (T) applicationContext.getBean(name);
	}

	public static <T> T getBean(Class<T> clazz) throws BeansException {
		return applicationContext.getBean(clazz);
	}

	public static <T> T getBean(String name, Class<T> clazz) throws BeansException {
		if (StringUtils.isEmpty(name))
			return getBean(clazz);
		if (clazz == null)
			return getBean(name);
		return applicationContext.getBean(name, clazz);
	}

}
