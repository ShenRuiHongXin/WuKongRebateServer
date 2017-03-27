package com.shenrui.wukong.utils;

import javax.servlet.ServletContextEvent;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;

public class ProjectInit extends ContextLoaderListener {
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		GetTaobaoTMC getTaobaoTMC = (GetTaobaoTMC) ContextLoader
				.getCurrentWebApplicationContext().getBean("getTaobaoTMC");
		getTaobaoTMC.getTaobaoMessage();
	}
}
