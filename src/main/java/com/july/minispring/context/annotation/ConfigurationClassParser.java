package com.july.minispring.context.annotation;

import com.july.minispring.beans.BeansException;
import com.july.minispring.beans.PropertyValue;
import com.july.minispring.beans.factory.PropertyPlaceholderConfigurer;
import com.july.minispring.beans.factory.config.BeanDefinition;
import com.july.minispring.beans.factory.support.BeanDefinitionRegistry;
import com.july.minispring.core.type.MethodMetadata;
import com.july.minispring.core.type.StandardMethodMetadata;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 注解配置类解析器
 *
 * @author july
 */
class ConfigurationClassParser {

    private final BeanDefinitionRegistry registry;

    private final ComponentScanAnnotationParser componentScanParser;

    private final Map<ConfigurationClass, ConfigurationClass> configurationClasses = new LinkedHashMap<>();

    ConfigurationClassParser(BeanDefinitionRegistry registry) {
        this.registry = registry;
        this.componentScanParser = new ComponentScanAnnotationParser(registry);
    }

    public void parse(Set<BeanDefinition> configCandidates) {
        for (BeanDefinition configDefinition : configCandidates) {
            processConfigurationClass(new ConfigurationClass(configDefinition.getBeanClass(), configDefinition.getBeanClass().getSimpleName()));
        }
    }

    private void processConfigurationClass(ConfigurationClass configClass) {
        ConfigurationClass existingClass = this.configurationClasses.get(configClass);
        if (existingClass != null) return;
        doProcessConfigurationClass(configClass);
    }

    protected void doProcessConfigurationClass(ConfigurationClass configClass) {
        //处理@PropertySource注解
        processPropertySource(configClass.getClazz());

        //处理@componentScan注解
        ComponentScan scanAnnotation = configClass.getClazz().getAnnotation(ComponentScan.class);
        if (scanAnnotation != null) {
            String[] basePackages = scanAnnotation.basePackages().length == 0 ? scanAnnotation.value() : scanAnnotation.basePackages();
            componentScanParser.parse(basePackages);
        }

        //处理@Import注解
        processImports(configClass.getClazz());

        //处理@bean注解
        Set<MethodMetadata> beanMethods = retrieveBeanMethodMetadata(configClass);
        for (MethodMetadata beanMethod : beanMethods) {
            configClass.addBeanMethod(beanMethod.getMethodName(), new BeanMethod(beanMethod, configClass));
        }
        this.configurationClasses.put(configClass, configClass);
    }

    /**
     * 处理指定配置类的@Import注解
     *
     * @param clazz
     */
    private void processImports(Class<?> clazz) {
        Import importAnnotation = clazz.getAnnotation(Import.class);
        if (importAnnotation != null) {
            Class<?>[] classes = importAnnotation.value();
            for (Class<?> newClazz : classes) {
                if (newClazz.getAnnotation(Configuration.class) != null) {
                    ConfigurationClass configClass = new ConfigurationClass(newClazz, newClazz.getSimpleName());
                    processConfigurationClass(configClass);
                }
            }
        }
    }

    /**
     * 处理指定配置类的@PropertySource注解
     *
     * @param clazz
     */
    private void processPropertySource(Class<?> clazz) {
        PropertySource propertyAnnotation = clazz.getAnnotation(PropertySource.class);
        if (propertyAnnotation != null) {
            String[] locations = propertyAnnotation.value();
            this.registry.registerBeanDefinition(AnnotationConfigUtils.PROPERTY_SOURCE_PROCESSOR_BEAN_NAME,
                    new BeanDefinition(PropertyPlaceholderConfigurer.class));
            this.registry.getBeanDefinition(AnnotationConfigUtils.PROPERTY_SOURCE_PROCESSOR_BEAN_NAME)
                        .addPropertyValue(new PropertyValue("locations", locations));
        }
    }

    private Set<MethodMetadata> retrieveBeanMethodMetadata(ConfigurationClass configClass) {
        Set<MethodMetadata> methodSet = new LinkedHashSet<>();
        Class<?> clazz = configClass.getClazz();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Bean beanAnnotation = method.getAnnotation(Bean.class);
            if (beanAnnotation != null) {
                methodSet.add(new StandardMethodMetadata(method, beanAnnotation.name().equals("") ? beanAnnotation.value() : beanAnnotation.name()));
            }
        }
        return methodSet;
    }

    public Set<ConfigurationClass> getConfigurationClasses() {
        return this.configurationClasses.keySet();
    }

}
