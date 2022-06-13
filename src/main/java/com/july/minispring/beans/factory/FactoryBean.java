package com.july.minispring.beans.factory;


/**
 * @author july
 */
public interface FactoryBean<T> {

    T getObject() throws Exception;

    boolean isSingleton();
}
