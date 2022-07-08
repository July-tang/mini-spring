package com.july.minispring.beans.factory.support;

import com.july.minispring.beans.BeansException;
import com.july.minispring.beans.factory.BeanFactory;
import com.july.minispring.beans.factory.config.BeanDefinition;
import com.sun.istack.internal.Nullable;

import java.lang.reflect.Method;

/**
 * bean实例化策略
 */
public interface InstantiationStrategy {

    /**
     * 无参构造器实例化
     *
     * @param beanDefinition
     * @return
     * @throws BeansException
     */
    Object instantiate(BeanDefinition beanDefinition) throws BeansException;

    /**
     * 指定FactoryMethod实例化
     *
     * @param bd
     * @param owner
     * @param factoryBean
     * @param factoryMethod
     * @param args
     * @return
     * @throws BeansException
     */
    Object instantiate(BeanDefinition bd, BeanFactory owner,
                       @Nullable Object factoryBean, Method factoryMethod, Object... args)
            throws BeansException;
}
