package com.july.minispring.context.event;

import com.july.minispring.context.ApplicationEvent;
import com.july.minispring.context.ApplicationListener;

/**
 * @author july
 */
public interface ApplicationEventMulticaster {

    void addApplicationListener(ApplicationListener<?> listener);

    void removeApplicationListener(ApplicationListener<?> listener);

    void multicastEvent(ApplicationEvent event);
}
