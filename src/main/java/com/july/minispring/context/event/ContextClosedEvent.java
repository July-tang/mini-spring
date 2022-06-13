package com.july.minispring.context.event;

import com.july.minispring.context.ApplicationContext;

/**
 * 定义容器关闭事件
 *
 * @author july
 */
public class ContextClosedEvent extends ApplicationContextEvent {
    public ContextClosedEvent(ApplicationContext source) {
        super(source);
    }
}
