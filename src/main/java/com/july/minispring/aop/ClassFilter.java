package com.july.minispring.aop;

/**
 * 匹配类
 *
 * @author july
 */
public interface ClassFilter {

    boolean matches(Class<?> clazz);
}
