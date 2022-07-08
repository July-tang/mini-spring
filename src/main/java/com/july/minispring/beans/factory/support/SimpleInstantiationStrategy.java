package com.july.minispring.beans.factory.support;

import cn.hutool.core.util.ReflectUtil;
import com.july.minispring.beans.BeansException;
import com.july.minispring.beans.factory.BeanFactory;
import com.july.minispring.beans.factory.config.BeanDefinition;
import com.sun.istack.internal.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class SimpleInstantiationStrategy implements InstantiationStrategy{

    private static final ThreadLocal<Method> currentlyInvokedFactoryMethod = new ThreadLocal<>();

    @Nullable
    public static Method getCurrentlyInvokedFactoryMethod() {
        return currentlyInvokedFactoryMethod.get();
    }

    /**
     * 根据bean的无参构造器实例化对象
     * @param beanDefinition
     * @return
     * @throws BeansException
     */
    @Override
    public Object instantiate(BeanDefinition beanDefinition) throws BeansException {
        Class beanClass =beanDefinition.getBeanClass();
        try {
            //通过反射获取创建对象的构造方法
            Constructor constructor = beanClass.getDeclaredConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            throw new BeansException("Failed to instantiate [" + beanClass.getName() + "]", e);
        }
    }

    @Override
    public Object instantiate(BeanDefinition bd, BeanFactory owner,
                              @Nullable Object factoryBean, final Method factoryMethod, Object... args) throws BeansException {
        try {
            ReflectUtil.setAccessible(factoryMethod);
            Method priorInvokedFactoryMethod = currentlyInvokedFactoryMethod.get();
            try {
                currentlyInvokedFactoryMethod.set(factoryMethod);
                return factoryMethod.invoke(factoryBean, args);
            } finally {
                if (priorInvokedFactoryMethod != null) {
                    currentlyInvokedFactoryMethod.set(priorInvokedFactoryMethod);
                }
                else {
                    currentlyInvokedFactoryMethod.remove();
                }
            }
        } catch (Exception e) {
            throw new BeansException("FactoryMethod instantiate failed");
        }
    }
}
