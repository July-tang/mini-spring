package com.july.minispring.beans.factory;


/**
 * @author july
 */
public interface InitializingBean {

    void afterPropertiesSet() throws Exception;
}
