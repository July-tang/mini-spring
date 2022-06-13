package com.july.minispring.test.common;

import com.july.minispring.aop.MethodAfterAdvice;
import com.july.minispring.aop.MethodAfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * @author july
 */
public class WorldServiceAfterReturningAdvice implements MethodAfterReturningAdvice {

    @Override
    public void afterReturn(Method method, Object[] args, Object target) {
        System.out.println("AfterReturningAdvice: do something after the earth has exploded");
    }
}
