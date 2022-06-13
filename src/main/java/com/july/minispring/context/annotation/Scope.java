package com.july.minispring.context.annotation;

import java.lang.annotation.*;

/**
 * @author july
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {

    String value() default "singleton";
}
