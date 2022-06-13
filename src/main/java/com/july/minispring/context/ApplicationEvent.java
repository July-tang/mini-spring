package com.july.minispring.context;

import java.util.EventObject;

/**
 * @author july
 */
public abstract class ApplicationEvent extends EventObject {

    public ApplicationEvent(Object source) {
        super(source);
    }
}
