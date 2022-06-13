package com.july.minispring.context.support;

import com.july.minispring.beans.BeansException;
import com.july.minispring.beans.factory.config.BeanPostProcessor;
import com.july.minispring.context.ApplicationContext;
import com.july.minispring.context.ApplicationContextAware;

/**
 * @author july
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
