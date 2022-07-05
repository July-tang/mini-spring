package com.july.minispring.context.annotation;

import com.july.minispring.beans.factory.config.BeanDefinition;
import com.sun.istack.internal.NotNull;

/**
 * 针对@configuration注解的实用类
 *
 * @author july
 */
public class ConfigurationClassUtils {

    public static final String CONFIGURATION_CLASS_FULL = "full";

    public static final String CONFIGURATION_CLASS_LITE = "lite";

    /**
     * 判断BeanDefinition是否为@Configuration注解类
     *
     * @param beanDef 给定的BeanDefinition
     * @return
     */
    public static boolean checkConfigurationClassCandidate(BeanDefinition beanDef) {
        Class<?> clazz = beanDef.getBeanClass();
        if (clazz == null) return false;
        Configuration configurationAnnotation = clazz.getDeclaredAnnotation(Configuration.class);
        return configurationAnnotation != null;
    }
}
