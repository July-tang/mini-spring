package com.july.minispring.context.annotation;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import com.july.minispring.beans.BeansException;
import com.july.minispring.beans.factory.BeanFactory;
import com.july.minispring.beans.factory.BeanFactoryAware;
import com.july.minispring.beans.factory.config.ConfigurableBeanFactory;
import com.july.minispring.beans.factory.support.SimpleInstantiationStrategy;
import com.sun.istack.internal.Nullable;
import jdk.internal.org.objectweb.asm.Opcodes;
import net.sf.cglib.asm.$Type;
import net.sf.cglib.core.ClassGenerator;
import net.sf.cglib.core.DefaultGeneratorStrategy;
import net.sf.cglib.proxy.*;
import net.sf.cglib.transform.ClassEmitterTransformer;
import net.sf.cglib.transform.ClassTransformer;
import net.sf.cglib.transform.TransformingClassGenerator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author july
 */
public class ConfigurationClassEnhancer {

    // The callbacks to use. Note that these callbacks must be stateless.
    private static final Callback[] CALLBACKS = new Callback[] {
            new BeanMethodInterceptor(),
            new BeanFactoryAwareMethodInterceptor(),
            NoOp.INSTANCE
    };

    private static final ConditionalCallbackFilter CALLBACK_FILTER = new ConditionalCallbackFilter(CALLBACKS);

    private static final String BEAN_FACTORY_FIELD = "$$beanFactory";

    public Class<?> enhance(Class<?> configClass, @Nullable ClassLoader classLoader) {
        //被代理过
        if (EnhancedConfiguration.class.isAssignableFrom(configClass)) return configClass;

        return createClass(newEnhancer(configClass, classLoader));
    }

    /**
     * Creates a new CGLIB {@link Enhancer} instance.
     */
    private Enhancer newEnhancer(Class<?> configSuperClass, @Nullable ClassLoader classLoader) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(configSuperClass);
        enhancer.setInterfaces(new Class<?>[] {EnhancedConfiguration.class});
        enhancer.setUseFactory(false);
//        enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);
        enhancer.setStrategy(new BeanFactoryAwareGeneratorStrategy(classLoader));
        enhancer.setCallbackFilter(CALLBACK_FILTER);
        enhancer.setCallbackTypes(CALLBACK_FILTER.getCallbackTypes());
        return enhancer;
    }

    /**
     * Uses enhancer to generate a subclass of superclass,
     * ensuring that callbacks are registered for the new subclass.
     *
     */
    private Class<?> createClass(Enhancer enhancer) {
        Class<?> subclass = enhancer.createClass();
        Enhancer.registerStaticCallbacks(subclass, CALLBACKS);
        return subclass;
    }

    /**
     * Marker interface to be implemented by all @Configuration CGLIB subclasses.
     *
     */
    public interface EnhancedConfiguration extends BeanFactoryAware {
    }

    private static class BeanFactoryAwareMethodInterceptor implements MethodInterceptor, ConditionalCallback {

        @Override
        @Nullable
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            Field field = ReflectUtil.getField(obj.getClass(), BEAN_FACTORY_FIELD);
            Assert.state(field != null, "Unable to find generated BeanFactory field");
            field.set(obj, args[0]);
            if (BeanFactoryAware.class.isAssignableFrom(getUserClass(obj.getClass().getSuperclass()))) {
                return proxy.invokeSuper(obj, args);
            }
            return null;
        }

        /**
         * Return the user-defined class for the given class: usually simply the given
         * class, but the original class in case of a CGLIB-generated subclass.
         * @param clazz
         * @return
         */
        public static Class<?> getUserClass(Class<?> clazz) {
            if (clazz.getName().contains("$$")) {
                Class<?> superclass = clazz.getSuperclass();
                if (superclass != null && superclass != Object.class) {
                    return superclass;
                }
            }
            return clazz;
        }

        @Override
        public boolean isMatch(Method candidateMethod) {
            return isSetBeanFactory(candidateMethod);
        }

        public static boolean isSetBeanFactory(Method candidateMethod) {
            return (candidateMethod.getName().equals("setBeanFactory") &&
                    candidateMethod.getParameterCount() == 1 &&
                    BeanFactory.class == candidateMethod.getParameterTypes()[0] &&
                    BeanFactoryAware.class.isAssignableFrom(candidateMethod.getDeclaringClass()));
        }
    }

    private static class BeanFactoryAwareGeneratorStrategy extends DefaultGeneratorStrategy {

        private final ClassLoader classLoader;

        public BeanFactoryAwareGeneratorStrategy(ClassLoader classLoader) {
            this.classLoader = classLoader;
        }

        @Override
        protected ClassGenerator transform(ClassGenerator cg) throws Exception {
            ClassTransformer transformer = new ClassEmitterTransformer() {
                @Override
                public void end_class() {
                    //给代理类设置public类型为BeanFactory，名称是$$beanFactory的属性
                    declare_field(Opcodes.ACC_PUBLIC, BEAN_FACTORY_FIELD, $Type.getType(BeanFactory.class), null);
                    super.end_class();
                }
            };
            return new TransformingClassGenerator(cg, transformer);
        }
    }

    /**
     * 拦截@bean注解方法
     *
     */
    private static class BeanMethodInterceptor implements MethodInterceptor, ConditionalCallback {
        @Override
        public Object intercept(Object enhancedConfigInstance, Method beanMethod,
                                Object[] beanMethodArgs, MethodProxy cglibMethodProxy) throws Throwable {

            ConfigurableBeanFactory beanFactory = getBeanFactory(enhancedConfigInstance);
            String beanName = getBeanName(beanMethod);

            if (isCurrentlyInvokedFactoryMethod(beanMethod)) {
                return cglibMethodProxy.invokeSuper(enhancedConfigInstance, beanMethodArgs);
            }
            return resolveBeanReference(beanMethod, beanMethodArgs, beanFactory, beanName);
        }

        private Object resolveBeanReference(Method beanMethod, Object[] beanMethodArgs,
                                            ConfigurableBeanFactory beanFactory, String beanName) {
            return beanFactory.getBean(beanName);
        }

        /**
         * 判断是否为当前所调用方法是否为bean容器内的工厂方法
         *
         * @param method
         * @return
         */
        private boolean isCurrentlyInvokedFactoryMethod(Method method) {
            Method currentlyInvoked = SimpleInstantiationStrategy.getCurrentlyInvokedFactoryMethod();
            return (currentlyInvoked != null && method.getName().equals(currentlyInvoked.getName()) &&
                    Arrays.equals(method.getParameterTypes(), currentlyInvoked.getParameterTypes()));
        }

        /**
         * 获取@bean注解方法的名称, 默认取方法名
         * @param beanMethod
         * @return
         */
        private String getBeanName(Method beanMethod) {
            Bean beanAnnotation = beanMethod.getAnnotation(Bean.class);
            //name优先级大于value
            String name = beanAnnotation.name();
            String value = name.length() > 0 ? name : beanAnnotation.value();
            return value.length() > 0 ? value : beanMethod.getName();
        }

        private ConfigurableBeanFactory getBeanFactory(Object enhancedConfigInstance) {
            Field field = ReflectUtil.getField(enhancedConfigInstance.getClass(), BEAN_FACTORY_FIELD);
            Assert.state(field != null, "Unable to find generated bean factory field");
            Object beanFactory;
            try {
                beanFactory = field.get(enhancedConfigInstance);
            } catch (Exception e) {
                throw new BeansException("获取配置类中beanFactory失败！");
            }
            Assert.state(beanFactory != null, "BeanFactory has not been injected into @Configuration class");
            Assert.state(beanFactory instanceof ConfigurableBeanFactory,
                    "Injected BeanFactory is not a ConfigurableBeanFactory");
            return (ConfigurableBeanFactory) beanFactory;
        }

        @Override
        public boolean isMatch(Method candidateMethod) {
            return (candidateMethod.getDeclaringClass() != Object.class &&
                    !BeanFactoryAwareMethodInterceptor.isSetBeanFactory(candidateMethod) &&
                    candidateMethod.getAnnotation(Bean.class) != null);
        }
    }


    private interface ConditionalCallback extends Callback {

        boolean isMatch(Method candidateMethod);
    }

    private static class ConditionalCallbackFilter implements CallbackFilter {

        private final Callback[] callbacks;

        private final Class<?>[] callbackTypes;

        public ConditionalCallbackFilter(Callback[] callbacks) {
            this.callbacks = callbacks;
            this.callbackTypes = new Class<?>[callbacks.length];
            for (int i = 0; i < callbacks.length; i++) {
                this.callbackTypes[i] = callbacks[i].getClass();
            }
        }

        @Override
        public int accept(Method method) {
            for (int i = 0; i < this.callbacks.length; i++) {
                Callback callback = this.callbacks[i];
                if (!(callback instanceof ConditionalCallback) || ((ConditionalCallback)callback).isMatch(method)) {
                    return i;
                }
            }
            throw new IllegalStateException("No callback available for method " + method.getName());
        }

        public Class<?>[] getCallbackTypes() {
            return this.callbackTypes;
        }
    }
}
