package com.july.minispring.aop.framework.adapter;

import com.july.minispring.aop.MethodAfterAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author july
 */
public class MethodAfterAdviceInterceptor implements MethodInterceptor {

    private MethodAfterAdvice advice;

    public MethodAfterAdviceInterceptor(MethodAfterAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object res = invocation.proceed();
        //执行代理方法之后，再执行after advice操作
        this.advice.after(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return res;
    }
}
