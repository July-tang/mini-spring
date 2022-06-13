package com.july.minispring.beans.factory;

/**
 * @author july
 */
public interface DisposableBean {

    void destroy() throws Exception;
}
