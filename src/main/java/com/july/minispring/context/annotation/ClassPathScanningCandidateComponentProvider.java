package com.july.minispring.context.annotation;

import cn.hutool.core.util.ClassUtil;
import com.july.minispring.beans.factory.config.BeanDefinition;
import com.july.minispring.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author july
 */
public class ClassPathScanningCandidateComponentProvider {

    public Set<BeanDefinition> findCandidateComponent(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        // 扫描有org.springframework.stereotype.Component注解的类
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> clazz : classes) {
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            candidates.add(beanDefinition);
        }
        return candidates;
    }
}
