package com.july.minispring.beans.factory.support;

import com.july.minispring.beans.BeansException;
import com.july.minispring.beans.factory.config.BeanDefinition;

public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy{


    @Override
    public Object instantiate(BeanDefinition beanDefinition) throws BeansException {
        //TODO 没了解cglib 无此功能
        throw new UnsupportedOperationException("CGLIB instantiation strategy is not supported");
    }
}
