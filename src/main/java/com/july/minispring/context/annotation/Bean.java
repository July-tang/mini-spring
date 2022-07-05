package com.july.minispring.context.annotation;

import com.july.minispring.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Bean {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";
}
