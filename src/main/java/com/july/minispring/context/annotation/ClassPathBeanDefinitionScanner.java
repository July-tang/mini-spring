package com.july.minispring.context.annotation;

import cn.hutool.core.util.StrUtil;
import com.july.minispring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.july.minispring.beans.factory.config.BeanDefinition;
import com.july.minispring.beans.factory.support.BeanDefinitionRegistry;
import com.july.minispring.stereotype.Component;

import java.util.Set;

/**
 * @author july
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {

    public static final String AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME = "com.july.minispring.context.annotation.internalAutowiredAnnotationProcessor";

    private final BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponent(basePackage);
            for (BeanDefinition candidate : candidates) {
                //解析bean的作用域
                String beanScope = resolveBeanScope(candidate);
                if (StrUtil.isNotEmpty(beanScope)) {
                    candidate.setScope(beanScope);
                }
                String beanName = resolveBeanName(candidate);
                registry.registerBeanDefinition(beanName, candidate);
            }
        }
        //注册处理@Autowired和@Value注解的BeanPostProcessor
        registry.registerBeanDefinition(AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME, new BeanDefinition(AutowiredAnnotationBeanPostProcessor.class));
    }

    /**
     * 获取bean的作用域
     *
     * @param candidate
     * @return
     */
    private String resolveBeanScope(BeanDefinition candidate) {
        Class<?> beanClass = candidate.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if (scope != null) {
            return scope.value();
        }
        return StrUtil.EMPTY;
    }

    private String resolveBeanName(BeanDefinition candidate) {
        Class<?> beanClass = candidate.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String value = component.value();
        if (StrUtil.isEmpty(value)) {
            value = StrUtil.lowerFirst(beanClass.getSimpleName());
        }
        return value;
    }

}
