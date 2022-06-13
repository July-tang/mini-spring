package com.july.minispring.context.event;

import com.july.minispring.context.ApplicationContext;
import com.july.minispring.context.ApplicationEvent;

/**
 * 定义Context相关事件
 * @author july
 */
public abstract class ApplicationContextEvent extends ApplicationEvent {

	public ApplicationContextEvent(ApplicationContext source) {
		super(source);
	}

	public final ApplicationContext getApplicationContext() {
		return (ApplicationContext) getSource();
	}
}
