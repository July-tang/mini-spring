package com.july.minispring.context.support;

import com.july.minispring.beans.BeansException;
import com.july.minispring.beans.factory.ConfigurableListableBeanFactory;
import com.july.minispring.beans.factory.config.BeanFactoryPostProcessor;
import com.july.minispring.beans.factory.config.BeanPostProcessor;
import com.july.minispring.beans.factory.config.ConfigurableBeanFactory;
import com.july.minispring.beans.factory.support.BeanDefinitionRegistry;
import com.july.minispring.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import com.july.minispring.context.ApplicationEvent;
import com.july.minispring.context.ApplicationListener;
import com.july.minispring.context.ConfigurableApplicationContext;
import com.july.minispring.context.event.ApplicationEventMulticaster;
import com.july.minispring.context.event.ContextClosedEvent;
import com.july.minispring.context.event.ContextRefreshedEvent;
import com.july.minispring.context.event.SimpleApplicationEventMulticaster;
import com.july.minispring.core.convert.ConversionService;
import com.july.minispring.core.convert.support.GenericConversionService;
import com.july.minispring.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

/**
 * 抽象应用上下文
 *
 * @author july
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    public static final String CONVERSION_SERVICE_BEAN_NAME = "conversionService";

    private ApplicationEventMulticaster applicationEventMulticaster;

    @Override
    public void refresh() throws BeansException {
        //创建BeanFactory，并加载BeanDefinition
        refreshBeanFactory();
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        //添加ApplicationContextAwareProcessor，让继承自ApplicationContextAware的bean能感知bean
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        //在bean实例化之前，执行BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        //BeanPostProcessor需要提前与其他bean实例化之前注册
        registerBeanPostProcessors(beanFactory);

        //初始化事件发布者
        initApplicationEventMulticaster();

        //注册事件监听器
        registerListeners();

        //注册类型转换器和提前实例化单例bean
        finishBeanFactoryInitialization(beanFactory);

        //发布容器刷新完成事件
        finishRefresh();
    }


    /**
     * 创建BeanFactory，并加载BeanDefinition
     *
     * @throws BeansException
     */
    protected abstract void refreshBeanFactory() throws BeansException;

    /**
     * 在Bean实例化之前，执行BeanFactoryPostProcessor
     * @param beanFactory
     */
    protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        //需先执行一次postProcessBeanDefinitionRegistry, 因其可以注册BeanDefinition，所以执行过程中可能新注册了BeanFactoryPostProcessor
        Map<String, BeanDefinitionRegistryPostProcessor> BeanDefinitionRegistryPostProcessorMap = beanFactory.getBeansOfType(BeanDefinitionRegistryPostProcessor.class);
        for (BeanDefinitionRegistryPostProcessor registryPostProcessor : BeanDefinitionRegistryPostProcessorMap.values()) {
            registryPostProcessor.postProcessBeanDefinitionRegistry((BeanDefinitionRegistry) beanFactory);
        }
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    /**
     * 注册BeanPostProcessor
     *
     * @param beanFactory
     */
    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    /**
     * 初始化事件发布者
     */
    private void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.addSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }

    /**
     * 注册事件监听器
     */
    protected void registerListeners() {
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for (ApplicationListener applicationListener : applicationListeners) {
            applicationEventMulticaster.addApplicationListener(applicationListener);
        }
    }

    /**
     * 注册类型转换器和提前实例化单例bean
     *
     * @param beanFactory
     */
    private void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
        ////设置类型转换器
        if (beanFactory.containsBean(CONVERSION_SERVICE_BEAN_NAME)) {
            Object conversionService = beanFactory.getBean(CONVERSION_SERVICE_BEAN_NAME);
            if (conversionService instanceof ConversionService) {
                beanFactory.setConversionService((ConversionService)conversionService);
            }
        }

        //提前实例化单例bean
        beanFactory.preInstantiateSingletons();
    }

    /**
     * 发布容器刷新完成事件
     */
    protected void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
    }

    public boolean containsBean(String name) {
        return getBeanFactory().containsBean(name);
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(requiredType);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    public abstract ConfigurableListableBeanFactory getBeanFactory();

    @Override
    public void registerShutdownHook() {
        Thread shutdownHook = new Thread(this::doClose);
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

    public void close() {
        doClose();
    }

    protected void doClose() {
        //发布容器关闭事件
        publishEvent(new ContextClosedEvent(this));

        //执行单例bean的销毁方法
        destroyBeans();
    }

    protected void destroyBeans() {
        getBeanFactory().destroySingletons();
    }
}
