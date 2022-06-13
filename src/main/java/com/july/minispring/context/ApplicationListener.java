package com.july.minispring.context;

import java.util.EventListener;

/**
 * @author july
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

    void onApplicationEvent(E event);
}
