package com.july.minispring.test.common;

import com.july.minispring.beans.BeansException;
import com.july.minispring.beans.PropertyValue;
import com.july.minispring.beans.PropertyValues;
import com.july.minispring.beans.factory.ConfigurableListableBeanFactory;
import com.july.minispring.beans.factory.config.BeanDefinition;
import com.july.minispring.beans.factory.config.BeanFactoryPostProcessor;

/**
 * @author july
 */
public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("CustomBeanFactoryPostProcessor#postProcessBeanFactory");
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("person");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("name", "ivy"));
    }
}
