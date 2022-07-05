package com.july.minispring.context.annotation;

import com.july.minispring.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Configuration {

    String value() default "";

    boolean proxyBeanMethods() default true;
}
