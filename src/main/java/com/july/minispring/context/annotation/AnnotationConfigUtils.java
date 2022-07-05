package com.july.minispring.context.annotation;

import com.july.minispring.beans.factory.config.BeanDefinition;
import com.july.minispring.beans.factory.support.BeanDefinitionRegistry;
import com.july.minispring.beans.factory.support.DefaultListableBeanFactory;
import com.july.minispring.context.support.GenericApplicationContext;
import com.sun.istack.internal.Nullable;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author july
 */
public abstract class AnnotationConfigUtils {

    /**
     * The bean name of the internally managed Configuration annotation processor.
     */
    public static final String CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME =
            "org.springframework.context.annotation.internalConfigurationAnnotationProcessor";

    public static final String PROPERTY_SOURCE_PROCESSOR_BEAN_NAME =
            "com.july.minispring.beans.factory.PropertyPlaceholderConfigurer";

    public static Set<BeanDefinition> registerAnnotationConfigProcessors(BeanDefinitionRegistry registry) {
        Set<BeanDefinition> beanDefs = new LinkedHashSet<>(8);
        if (!registry.containsBeanDefinition(CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME)) {
            BeanDefinition def = new BeanDefinition(ConfigurationClassPostProcessor.class);
            registry.registerBeanDefinition(CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME, def);
            beanDefs.add(def);
        }
        return beanDefs;
    }
}
