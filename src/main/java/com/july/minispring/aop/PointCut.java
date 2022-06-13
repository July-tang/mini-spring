package com.july.minispring.aop;

/**
 * 切入点抽象
 *
 * @author july
 */
public interface PointCut {

    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();
}
