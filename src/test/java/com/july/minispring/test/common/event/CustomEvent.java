package com.july.minispring.test.common.event;

import com.july.minispring.context.ApplicationEvent;

/**
 * @author july
 */
public class CustomEvent extends ApplicationEvent {
    public CustomEvent(Object source) {
        super(source);
    }
}
