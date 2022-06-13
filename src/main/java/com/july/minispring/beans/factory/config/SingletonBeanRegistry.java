package com.july.minispring.beans.factory.config;


/**
 * 单例工厂注册表
 */
public interface SingletonBeanRegistry {

    Object getSingleton(String beanName);

    void addSingleton(String beanName, Object singletonObject);
}
