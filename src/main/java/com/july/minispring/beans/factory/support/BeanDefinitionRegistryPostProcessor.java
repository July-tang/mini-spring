package com.july.minispring.beans.factory.support;

import com.july.minispring.beans.BeansException;
import com.july.minispring.beans.factory.config.BeanFactoryPostProcessor;

/**
 * 实现对BeanDefinition的增添
 *
 * @author july
 */
public interface BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor {

    /**
     * 在BeanDefinition加载后，BeanFactoryPostProcessor之前，提供增加BeanDefinition的机制
     *
     * @param registry
     * @throws BeansException
     */
    void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException;

}
