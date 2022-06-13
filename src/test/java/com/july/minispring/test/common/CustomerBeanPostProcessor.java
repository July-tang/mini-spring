package com.july.minispring.test.common;

import com.july.minispring.beans.BeansException;
import com.july.minispring.beans.factory.config.BeanPostProcessor;
import com.july.minispring.test.bean.Car;

/**
 * @author july
 */
public class CustomerBeanPostProcessor implements BeanPostProcessor {


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("CustomerBeanPostProcessor#postProcessBeforeInitialization, beanName: " + beanName);
        if ("car".equals(beanName)) {
            ((Car)bean).setBrand("lamborghini");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("CustomerBeanPostProcessor#postProcessAfterInitialization, beanName: " + beanName);
        return bean;
    }
}
