package com.july.minispring.aop;

import java.lang.reflect.Method;

/**
 * @author july
 */
public interface MethodBeforeAdvice extends BeforeAdvice {

    void before(Method method, Object[] args, Object target) throws Throwable;
}
