package com.july.minispring.context.annotation;

import cn.hutool.core.util.ClassUtil;
import com.july.minispring.beans.BeansException;
import com.july.minispring.beans.factory.ConfigurableListableBeanFactory;
import com.july.minispring.beans.factory.config.BeanDefinition;
import com.july.minispring.beans.factory.support.BeanDefinitionRegistry;
import com.july.minispring.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import com.sun.istack.internal.Nullable;

import java.util.*;

/**
 * @author july
 */
public class ConfigurationClassPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Nullable
    private ConfigurationClassBeanDefinitionReader reader;

    private ClassLoader beanClassLoader = ClassUtil.getClassLoader();

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        processConfigBeanDefinitions(registry);
    }

    private void processConfigBeanDefinitions(BeanDefinitionRegistry registry) {
        List<BeanDefinition> configCandidates = new ArrayList<>();
        String[] candidateNames = registry.getBeanDefinitionNames();
        //读取@configuration注解类
        for (String beanName: candidateNames) {
            BeanDefinition beanDefinition = registry.getBeanDefinition(beanName);
            if (ConfigurationClassUtils.checkConfigurationClassCandidate(beanDefinition)) {
                configCandidates.add(beanDefinition);
            }
        }
        if (configCandidates.isEmpty()) return;
        //解析注解配置类
        ConfigurationClassParser parser = new ConfigurationClassParser(registry);
        Set<BeanDefinition> candidates = new LinkedHashSet<>(configCandidates);
        parser.parse(candidates);
        Set<ConfigurationClass> configClasses = new HashSet<>(parser.getConfigurationClasses());
        if (this.reader == null) {
            this.reader = new ConfigurationClassBeanDefinitionReader(registry);
        }
        reader.loadBeanDefinitions(configClasses);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        enhanceConfigurationClasses(beanFactory);
    }

    private void enhanceConfigurationClasses(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanDefinition> configBeanDefs = new LinkedHashMap<>();
        for (String beanName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDef = beanFactory.getBeanDefinition(beanName);
            if (beanDef.isConfig()) {
                configBeanDefs.put(beanName, beanDef);
            }
        }
        if (configBeanDefs.isEmpty()) return;
        ConfigurationClassEnhancer enhancer = new ConfigurationClassEnhancer();
        for (Map.Entry<String, BeanDefinition> entry : configBeanDefs.entrySet()) {
            BeanDefinition beanDef = entry.getValue();
            Class<?> configClass = beanDef.getBeanClass();
            Class<?> enhancedClass = enhancer.enhance(configClass, this.beanClassLoader);
            if (configClass != enhancedClass) {
                beanDef.setBeanClass(enhancedClass);
            }
        }
    }
}
