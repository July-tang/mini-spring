package com.july.minispring.beans.factory;

import com.july.minispring.beans.BeansException;
import com.july.minispring.beans.factory.config.AutowireCapableBeanFactory;
import com.july.minispring.beans.factory.config.BeanDefinition;
import com.july.minispring.beans.factory.config.BeanPostProcessor;
import com.july.minispring.beans.factory.config.ConfigurableBeanFactory;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory, ConfigurableBeanFactory, AutowireCapableBeanFactory {

    /**
     * 根据名称查找BeanDefinition
     * @param beanName
     * @return
     * @throws BeansException
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 提前实例化所有单例实例
     * @throws BeansException
     */
    void preInstantiateSingletons() throws BeansException;

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
