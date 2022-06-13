package com.july.minispring.context.event;

import com.july.minispring.context.ApplicationContext;
import com.july.minispring.context.ApplicationEvent;

/**
 * 定义容器刷新完成事件
 *
 * @author july
 */
public class ContextRefreshedEvent extends ApplicationContextEvent {

    public ContextRefreshedEvent(ApplicationContext source) {
        super(source);
    }
}
