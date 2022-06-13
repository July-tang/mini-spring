package com.july.minispring.beans.factory.config;


import com.july.minispring.beans.factory.HierarchicalBeanFactory;
import com.july.minispring.core.convert.ConversionService;
import com.july.minispring.util.StringValueResolver;


public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    /**
     * @param beanPostProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 销毁单例bean
     */

    void destroySingletons();

    void addEmbeddedValueResolver(StringValueResolver valueResolver);

    void setConversionService(ConversionService conversionService);

    ConversionService getConversionService();

    String resolveEmbeddedValue(String value);
}
