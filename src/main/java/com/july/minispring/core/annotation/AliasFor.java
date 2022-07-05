package com.july.minispring.core.annotation;

import java.lang.annotation.*;

/**
 * 别名
 *
 * @author july
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface AliasFor {

    @AliasFor("attribute")
    String value() default "";

    @AliasFor("value")
    String attribute() default "";
}
