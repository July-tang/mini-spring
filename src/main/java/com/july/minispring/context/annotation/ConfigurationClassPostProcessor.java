package com.july.minispring.context.annotation;

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

    }
}
