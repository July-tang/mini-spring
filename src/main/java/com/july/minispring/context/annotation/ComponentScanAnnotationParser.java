package com.july.minispring.context.annotation;

import com.july.minispring.beans.factory.support.BeanDefinitionRegistry;

/**
 * ComponentScan注解解析器
 *
 * @author july
 */
public class ComponentScanAnnotationParser {

    private final BeanDefinitionRegistry registry;

    public ComponentScanAnnotationParser(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void parse(String[] basePackages) {
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
        scanner.doScan(basePackages);
    }
}
