package com.july.minispring.beans.factory.config;

import com.july.minispring.beans.PropertyValue;
import com.july.minispring.beans.PropertyValues;
import com.july.minispring.context.annotation.BeanMethod;


/**
 *  保存bean的信息，包括class类型、构造参数、属性值等
 */

public class BeanDefinition {

    public static String SCOPE_SINGLETON = "singleton";

    public static String SCOPE_PROTOTYPE = "prototype";

    private Class beanClass;

    private PropertyValues propertyValues;

    private String initMethodName;

    private String destroyMethodName;

    private String scope = SCOPE_SINGLETON;

    private BeanMethod factoryMethod;

    private boolean singleton = true;

    private boolean prototype = false;

    private boolean isConfig = false;

    public void setScope(String scope) {
        this.scope = scope;
        this.singleton = SCOPE_SINGLETON.equals(scope);
        this.prototype = SCOPE_PROTOTYPE.equals(scope);
    }

    public boolean isSingleton() {
        return this.singleton;
    }

    public boolean isPrototype() {
        return this.prototype;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }

    public BeanMethod getFactoryMethod() {
        return factoryMethod;
    }

    public void setFactoryMethod(BeanMethod factoryMethod) {
        this.factoryMethod = factoryMethod;
    }

    public boolean isConfig() {
        return isConfig;
    }

    public void setConfig(boolean config) {
        isConfig = config;
    }

    public BeanDefinition() {
    }

    public BeanDefinition(Class beanClass) {
        this(beanClass, null);
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues == null ? new PropertyValues() : propertyValues;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void addPropertyValue(PropertyValue propertyValue) {
        this.getPropertyValues().addPropertyValue(propertyValue);
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

}
