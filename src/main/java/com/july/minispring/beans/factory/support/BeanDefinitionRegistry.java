package com.july.minispring.beans.factory.support;


import com.july.minispring.beans.BeansException;
import com.july.minispring.beans.factory.config.BeanDefinition;

/**
 *  BeanDefinition注册表接口
 *  定义注册BeanDefinition的方法
 */
public interface BeanDefinitionRegistry {

    /**
     * 向注册表中注入BeanDefinition
     *
     * @param beanName
     * @param beanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 根据名称查找BeanDefinition
     * @param beanName
     * @return
     * @throws BeansException
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 是否已包含指定名称的BeanDefinition
     * @param beanName
     * @return
     */
    boolean containsBeanDefinition(String beanName);
}
