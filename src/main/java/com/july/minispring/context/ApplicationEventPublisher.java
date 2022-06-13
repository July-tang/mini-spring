package com.july.minispring.context;

/**
 * 事件发布者接口
 *
 * @author july
 */
public interface ApplicationEventPublisher {

    /**
     * 发布事件
     *
     * @param event
     */
    void publishEvent(ApplicationEvent event);
}
