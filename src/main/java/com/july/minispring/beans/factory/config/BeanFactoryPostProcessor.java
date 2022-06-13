package com.july.minispring.beans.factory.config;

import com.july.minispring.beans.BeansException;
import com.july.minispring.beans.factory.ConfigurableListableBeanFactory;

/**
 * 允许自定义修改BeanDefinition的值
 *
 * @author july
 */

public interface BeanFactoryPostProcessor {

    /**
     * 在所有BeanDefinition加载完成后，但在Bean实例化之前，提供修改BeanDefinition的属性值的机制
     * @param beanFactory
     * @throws BeansException
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
