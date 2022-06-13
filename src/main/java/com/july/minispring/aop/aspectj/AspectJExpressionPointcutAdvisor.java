package com.july.minispring.aop.aspectj;

import com.july.minispring.aop.PointCut;
import com.july.minispring.aop.PointCutAdvisor;
import org.aopalliance.aop.Advice;

/**
 * @author july
 */
public class AspectJExpressionPointcutAdvisor implements PointCutAdvisor {

    private AspectJExpressionPointcut pointcut;

    private Advice advice;

    private String expression;

    public void setExpression(String expression) {
        this.expression = expression;
        pointcut = new AspectJExpressionPointcut(expression);
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public PointCut getPointCut() {
        if (pointcut == null) {
            pointcut = new AspectJExpressionPointcut(expression);
        }
        return pointcut;
    }
}
