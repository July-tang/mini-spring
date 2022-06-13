package com.july.minispring.beans.factory.config;

import com.july.minispring.beans.BeansException;

/**
 * 用于修改 实例化后的bean 的修改扩展点
 * @author july
 */
public interface BeanPostProcessor {


    /**
     * 在bean执行初始化方法之前执行此方法
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * 在bean执行初始化方法之后执行此方法
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
