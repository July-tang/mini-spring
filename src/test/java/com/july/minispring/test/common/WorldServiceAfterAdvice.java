package com.july.minispring.test.common;

import com.july.minispring.aop.MethodAfterAdvice;

import java.lang.reflect.Method;

/**
 * @author july
 */
public class WorldServiceAfterAdvice implements MethodAfterAdvice {
    @Override
    public void after(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("AfterAdvice: do something after the earth explodes");
    }
}
