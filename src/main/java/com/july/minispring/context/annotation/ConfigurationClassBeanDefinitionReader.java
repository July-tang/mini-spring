package com.july.minispring.context.annotation;

import com.july.minispring.beans.BeansException;
import com.july.minispring.beans.factory.config.BeanDefinition;
import com.july.minispring.beans.factory.support.BeanDefinitionRegistry;
import com.july.minispring.core.type.MethodMetadata;

import java.util.Set;

/**
 * @author july
 */
public class ConfigurationClassBeanDefinitionReader {

    private final BeanDefinitionRegistry registry;

    ConfigurationClassBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    /**
     * 读取解析后的注解配置类，加载其中的BeanDefinition
     *
     * @param configurationModel
     */
    public void loadBeanDefinitions(Set<ConfigurationClass> configurationModel) {
        for (ConfigurationClass configClass : configurationModel) {
            loadBeanDefinitionsForConfigurationClass(configClass);
        }
    }

    /**
     * 读取指定的注解配置类，加载其BeanDefinition
     *
     * @param configClass
     */
    private void loadBeanDefinitionsForConfigurationClass(ConfigurationClass configClass) {
        for (BeanMethod beanMethod : configClass.getBeanMethods().values()) {
            loadBeanDefinitionsForBeanMethod(beanMethod);
        }
    }

    private void loadBeanDefinitionsForBeanMethod(BeanMethod beanMethod) {
        MethodMetadata metadata = beanMethod.getMetadata();
        //获取方法名
        String methodName = metadata.getMethodName();
        Class<?> beanClass = metadata.getIntrospectedMethod().getReturnType();
        BeanDefinition beanDef = new BeanDefinition(beanClass);
        try {
            String name = metadata.getAnnotationName();
            String beanName = name.length() != 0 ? name : methodName;
            beanDef.setFactoryMethod(beanMethod);
            this.registry.registerBeanDefinition(beanName, beanDef);
        } catch (Exception e) {
            throw new BeansException("Bean " + methodName + " load failed");
        }
    }

}
