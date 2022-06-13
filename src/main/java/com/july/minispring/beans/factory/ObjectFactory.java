package com.july.minispring.beans.factory;

import com.july.minispring.beans.BeansException;

/**
 * @author july
 */
public interface ObjectFactory<T> {

    T getObject() throws BeansException;
}
