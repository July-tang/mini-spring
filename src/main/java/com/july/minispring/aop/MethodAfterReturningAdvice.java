package com.july.minispring.aop;

import java.lang.reflect.Method;

/**
 * @author july
 */
public interface MethodAfterReturningAdvice extends AfterReturningAdvice {

    void afterReturn(Method method, Object[] args, Object target);
}
