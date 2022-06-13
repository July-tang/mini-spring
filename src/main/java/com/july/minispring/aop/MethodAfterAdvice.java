package com.july.minispring.aop;

import java.lang.reflect.Method;

/**
 * @author july
 */
public interface MethodAfterAdvice extends AfterAdvice{

    void after(Method method, Object[] args, Object target) throws Throwable;
}
