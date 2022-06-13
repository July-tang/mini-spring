package com.july.minispring.aop;

import java.lang.reflect.Method;

/**
 * 匹配方法
 *
 * @author july
 */
public interface MethodMatcher {

    boolean matches(Method method, Class<?> targetClass);
}
