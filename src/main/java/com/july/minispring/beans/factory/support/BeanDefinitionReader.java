package com.july.minispring.beans.factory.support;

import com.july.minispring.beans.BeansException;
import com.july.minispring.core.io.Resource;
import com.july.minispring.core.io.ResourceLoader;

/**
 *  读取bean定义信息即BeanDefinition的接口
 */

public interface BeanDefinitionReader {


    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(String location) throws BeansException;

    void loadBeanDefinitions(String[] locations) throws BeansException;

    void loadBeanDefinitions(Resource resource) throws BeansException;

}
