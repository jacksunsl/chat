package com.hipishare.chat.server.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @Description: 获取spring容器，以访问容器中定义的其他bean
 * @date 2015年8月7日
 * @author sunlei
 * @version 1.0
 * <p>Company:海城创投</p>
 * <p>Copyright:Copyright(c)2015</p>
 */
public class SpringContextUtil implements ApplicationContextAware {

	private static Logger LOG = LogManager.getLogger(SpringContextUtil.class.getName());
	
	// Spring应用上下文环境
	private static ApplicationContext applicationContext;

	/**  
	 * 实现ApplicationContextAware接口的回调方法，设置上下文环境  
	 *   
	 * @param applicationContext  
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextUtil.applicationContext = applicationContext;
	}

	/**  
	 * @return ApplicationContext  
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**  
	 * 获取对象  
	 * 这里重写了bean方法，起主要作用  
	 * @param name  
	 * @return Object 一个以所给名字注册的bean的实例  
	 * @throws Exception  
	 */
	public static Object getBean(String name) throws Exception {
		return applicationContext.getBean(name);
	}

	@SuppressWarnings("resource")
	public static void initSpringConfig() throws BeansException {
		LOG.info("初始化spring容器...");
		AbstractApplicationContext context = null;
		try {
	        context = new ClassPathXmlApplicationContext("applicationContext.xml");
		} catch(BeansException e) {
			LOG.info("spring容器初始化失败。");
			if (null != context) {
		        context.close();// 释放资源
				LOG.info("spring context 容器关闭。");
			}
			throw e;
		} 
		LOG.info("spring容器初始化成功。");
	}
}