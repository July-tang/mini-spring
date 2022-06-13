package com.july.minispring.test.aop;

import com.july.minispring.aop.AdvisedSupport;
import com.july.minispring.aop.ClassFilter;
import com.july.minispring.aop.MethodMatcher;
import com.july.minispring.aop.TargetSource;
import com.july.minispring.aop.aspectj.AspectJExpressionPointcut;
import com.july.minispring.aop.aspectj.AspectJExpressionPointcutAdvisor;
import com.july.minispring.aop.framework.CglibAopProxy;
import com.july.minispring.aop.framework.JdkDynamicAopProxy;
import com.july.minispring.aop.framework.ProxyFactory;
import com.july.minispring.aop.framework.adapter.MethodAfterAdviceInterceptor;
import com.july.minispring.aop.framework.adapter.MethodAfterReturningAdviceInterceptor;
import com.july.minispring.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import com.july.minispring.test.common.WorldServiceAfterAdvice;
import com.july.minispring.test.common.WorldServiceAfterReturningAdvice;
import com.july.minispring.test.common.WorldServiceBeforeAdvice;
import com.july.minispring.test.common.WorldServiceInterceptor;
import com.july.minispring.test.service.WorldService;
import com.july.minispring.test.service.WorldServiceImpl;
import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Before;
import org.junit.Test;

/**
 * @author july
 */
public class DynamicProxyTest {

    private AdvisedSupport advisedSupport;

    @Before
    public void setup() {
        WorldService worldService = new WorldServiceImpl();

        advisedSupport   = new AdvisedSupport();
        TargetSource targetSource = new TargetSource(worldService);
        WorldServiceInterceptor methodInterceptor = new WorldServiceInterceptor();
        MethodMatcher methodMatcher = new AspectJExpressionPointcut("execution(* com.july.minispring.test.service.WorldService.explode(..))").getMethodMatcher();
        advisedSupport.setTargetSource(targetSource);
        advisedSupport.setMethodInterceptor(methodInterceptor);
        advisedSupport.setMethodMatcher(methodMatcher);
    }

    @Test
    public void testJdkDynamicProxy() throws Exception {
        WorldService proxy = (WorldService) new JdkDynamicAopProxy(advisedSupport).getProxy();
        proxy.explode();
    }

    @Test
    public void testCglibDynamicProxy() throws Exception {
        WorldService proxy = (WorldService) new CglibAopProxy(advisedSupport).getProxy();
        proxy.explode();
    }

    @Test
    public void testProxyFactory() throws Exception {
        // 使用JDK动态代理
        advisedSupport.setProxyTargetClass(false);
        WorldService proxy = (WorldService) new ProxyFactory(advisedSupport).getProxy();
        proxy.explode();

        // 使用CGLIB动态代理
        advisedSupport.setProxyTargetClass(true);
        proxy = (WorldService) new ProxyFactory(advisedSupport).getProxy();
        proxy.explode();
    }

    @Test
    public void testBeforeAdvice() throws Exception {
        //设置BeforeAdvice
        WorldServiceBeforeAdvice beforeAdvice = new WorldServiceBeforeAdvice();
        MethodBeforeAdviceInterceptor methodInterceptor = new MethodBeforeAdviceInterceptor(beforeAdvice);
        advisedSupport.setMethodInterceptor(methodInterceptor);

        WorldService proxy = (WorldService) new ProxyFactory(advisedSupport).getProxy();
        proxy.explode();
    }

    @Test
    public void testAfterAdvice() throws Exception {
        //设置AfterAdvice
        WorldServiceAfterAdvice afterAdvice = new WorldServiceAfterAdvice();
        MethodAfterAdviceInterceptor methodInterceptor = new MethodAfterAdviceInterceptor(afterAdvice);
        advisedSupport.setMethodInterceptor(methodInterceptor);

        WorldService proxy = (WorldService) new ProxyFactory(advisedSupport).getProxy();
        proxy.explode();
    }

    @Test
    public void testAfterReturningAdvice() throws Exception {
        //设置AfterReturningAdvice
        WorldServiceAfterReturningAdvice returningAdvice = new WorldServiceAfterReturningAdvice();
        MethodAfterReturningAdviceInterceptor methodInterceptor = new MethodAfterReturningAdviceInterceptor(returningAdvice);
        advisedSupport.setMethodInterceptor(methodInterceptor);

        WorldService proxy = (WorldService) new ProxyFactory(advisedSupport).getProxy();
        proxy.explode();
    }

    @Test
    public void testAdvisor() throws Exception {
        WorldService worldService = new WorldServiceImpl();

        String expression = "execution(* com.july.minispring.test.service.WorldService.*(..))";
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(expression);
        MethodAfterAdviceInterceptor methodInterceptor = new MethodAfterAdviceInterceptor(new WorldServiceAfterAdvice());
        advisor.setAdvice(methodInterceptor);

        ClassFilter classFilter = advisor.getPointCut().getClassFilter();
        if (classFilter.matches(worldService.getClass())) {
            AdvisedSupport advisedSupport = new AdvisedSupport();
            TargetSource targetSource = new TargetSource(worldService);
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointCut().getMethodMatcher());
            advisedSupport.setProxyTargetClass(true); //CGLIB

            WorldService proxy = (WorldService) new ProxyFactory(advisedSupport).getProxy();
            proxy.explode();
        }
    }
}
