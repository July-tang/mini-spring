package com.july.minispring.context.support;

import cn.hutool.core.lang.Assert;
import com.july.minispring.beans.BeansException;
import com.july.minispring.context.annotation.AnnotatedBeanDefinitionReader;
import com.july.minispring.context.annotation.AnnotationConfigRegistry;
import com.july.minispring.context.annotation.ClassPathBeanDefinitionScanner;

/**
 * @author july
 */
public class AnnotationConfigApplicationContext extends GenericApplicationContext implements AnnotationConfigRegistry {

    private final AnnotatedBeanDefinitionReader reader;

    private final ClassPathBeanDefinitionScanner scanner;


    /**
     * 从注解配置类中加载BeanDefinition,并自动刷新上下文
     *
     * @param componentClasses 注解配置类
     * @throws BeansException 应用上下文创建失败
     */
    public AnnotationConfigApplicationContext(Class<?>... componentClasses) throws BeansException {
        this.reader = new AnnotatedBeanDefinitionReader(this);
        this.scanner = new ClassPathBeanDefinitionScanner(this);
        register(componentClasses);
        refresh();
    }

    @Override
    public void register(Class<?>... componentClasses) {
        Assert.notEmpty(componentClasses, "At least one component class must be specified");
        reader.register(componentClasses);
    }

    @Override
    public void scan(String... basePackages) {
        Assert.notEmpty(basePackages, "At least one base package must be specified");
        scanner.doScan(basePackages);
    }
}
