package com.july.minispring.test.common;


import com.july.minispring.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author july
 */
public class WorldServiceBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("BeforeAdvice: do something before the earth explodes");
    }
}
