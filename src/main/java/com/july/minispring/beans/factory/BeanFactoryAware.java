package com.july.minispring.beans.factory;

import com.july.minispring.beans.BeansException;

/**
 * 实现该接口，能感知所属BeanFactory
 *
 * @author july
 */
public interface BeanFactoryAware extends Aware {


    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
