package com.july.minispring.context.annotation;


import com.july.minispring.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ComponentScan {

    @AliasFor("basePackages")
    String[] value() default {};

    /**
     * 需扫描的包目录
     *
     * @return
     */
    @AliasFor("value")
    String[] basePackages() default {};
}
