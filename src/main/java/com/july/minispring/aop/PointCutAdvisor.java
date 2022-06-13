package com.july.minispring.aop;

/**
 * @author july
 */
public interface PointCutAdvisor extends Advisor{

    PointCut getPointCut();
}
