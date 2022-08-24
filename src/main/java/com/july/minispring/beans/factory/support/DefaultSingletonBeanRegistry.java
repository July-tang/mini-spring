package com.july.minispring.beans.factory.support;

import com.july.minispring.beans.BeansException;
import com.july.minispring.beans.factory.DisposableBean;
import com.july.minispring.beans.factory.ObjectFactory;
import com.july.minispring.beans.factory.config.SingletonBeanRegistry;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    //一级缓存
    private Map<String, Object> singletonObjects = new HashMap<>();

    //二级缓存
    protected Map<String, Object> earlySingletonObjects = new HashMap<>();

    //三级缓存
    private Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>();

    private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        //检查一级缓存
        Object singletonObject = singletonObjects.get(beanName);
        if (singletonObject == null) {
            //检查二级缓存
            singletonObject = earlySingletonObjects.get(beanName);
            if (singletonObject == null) {
                //检查三级缓存
                ObjectFactory<?> singletonFactory = singletonFactories.get(beanName);
                if (singletonFactory != null) {
                    singletonObject = singletonFactory.getObject();
                    //从三级缓存放进二级缓存
                    earlySingletonObjects.put(beanName, singletonObject);
                    singletonFactories.remove(beanName);
                }
            }
        }
        return singletonObject;
    }

    /**
     * 将bean实例放入一级缓存
     *
     * @param beanName
     * @param singletonObject
     */
    public void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
        earlySingletonObjects.remove(beanName);
        singletonFactories.remove(beanName);
    }

    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory) {
        singletonFactories.put(beanName, singletonFactory);
    }

    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeans.put(beanName, bean);
    }

    public void destroySingletons() {
        Set<String> beanNames = disposableBeans.keySet();
        for (String beanName : beanNames) {
            DisposableBean disposableBean = disposableBeans.get(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }
}
