package com.july.minispring.core.type;

import java.lang.reflect.Method;

/**
 * 存储@bean注解方法属性
 *
 * @author july
 */
public interface MethodMetadata {

    /**
     * 获取方法名
     *
     * @return
     */
    String getMethodName();

    /**
     * 获取方法所在类名
     *
     * @return
     */
    String getDeclaringClassName();

    /**
     * 获取返回类型类名
     *
     * @return
     */
    String getReturnTypeName();

    /**
     * 获取注解指定的名称
     *
     * @return
     */
    String getAnnotationName();

    /**
     * 获取方法
     *
     * @return
     */
    Method getIntrospectedMethod();
}
