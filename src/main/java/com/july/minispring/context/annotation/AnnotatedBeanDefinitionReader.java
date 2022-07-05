package com.july.minispring.context.annotation;

import com.july.minispring.beans.factory.config.BeanDefinition;
import com.july.minispring.beans.factory.support.BeanDefinitionRegistry;

/**
 * @author july
 */
public class AnnotatedBeanDefinitionReader {

    private final BeanDefinitionRegistry registry;

    public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
        AnnotationConfigUtils.registerAnnotationConfigProcessors(registry);
    }

    public final BeanDefinitionRegistry getRegistry() {
        return this.registry;
    }

    /**
     * 注册待处理的Component注解bean
     *
     * @param componentClasses
     */
    public void register(Class<?>... componentClasses) {
        for (Class<?> componentClass : componentClasses) {
            registerBean(componentClass);
        }
    }

    public void registerBean(Class<?> beanClass) {
        doRegisterBean(beanClass);
    }

    /**
     * 注册指定类型的bean
     *
     * @param beanClass
     */
    private void doRegisterBean(Class<?> beanClass) {
        BeanDefinition beanDefinition = new BeanDefinition(beanClass);
        this.registry.registerBeanDefinition(beanClass.toGenericString(), beanDefinition);
    }
}
