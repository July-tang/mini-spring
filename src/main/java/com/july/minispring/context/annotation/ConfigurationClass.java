package com.july.minispring.context.annotation;

import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 注解配置类
 *
 * @author july
 */
public class ConfigurationClass {

    @Nullable
    private String beanName;

    private Class<?> clazz;

    private final Map<String, BeanMethod> beanMethods = new HashMap<>();

    void addBeanMethod(String methodName, BeanMethod method) {
        this.beanMethods.put(methodName, method);
    }

    Map<String, BeanMethod> getBeanMethods() {
        return this.beanMethods;
    }

    BeanMethod getBeanMethod(String methodName) {
        return getBeanMethods().get(methodName);
    }

    public ConfigurationClass(Class<?> clazz, String beanName) {
        this.clazz = clazz;
        this.beanName = beanName;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    @Override
    public String toString() {
        return "ConfigurationClass{" +
                "beanName='" + beanName + '\'' +
                ", clazz=" + clazz +
                ", beanMethods=" + beanMethods +
                '}';
    }
}
