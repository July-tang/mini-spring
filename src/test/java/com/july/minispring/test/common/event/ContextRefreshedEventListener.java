package com.july.minispring.test.common.event;

import com.july.minispring.context.event.ContextRefreshedEvent;
import com.july.minispring.context.ApplicationListener;

/**
 * @author derekyi
 * @date 2020/12/5
 */
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.println(this.getClass().getName());
	}
}
