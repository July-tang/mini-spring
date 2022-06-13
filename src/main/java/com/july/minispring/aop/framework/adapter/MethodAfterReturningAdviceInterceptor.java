package com.july.minispring.aop.framework.adapter;

import com.july.minispring.aop.MethodAfterReturningAdvice;
import com.july.minispring.aop.MethodBeforeAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author july
 */
public class MethodAfterReturningAdviceInterceptor implements MethodInterceptor {

    private MethodAfterReturningAdvice advice;

    public MethodAfterReturningAdviceInterceptor(MethodAfterReturningAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            return invocation.proceed();
        } finally {
            this.advice.afterReturn(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        }
    }
}
