package com.july.minispring.core.type;

import cn.hutool.core.lang.Assert;
import com.july.minispring.context.annotation.Bean;

import java.lang.reflect.Method;

/**
 * bean注解方法属性实现类
 *
 * @author july
 */
public class StandardMethodMetadata implements MethodMetadata{

    private final Method introspectedMethod;

    private final String annotationName;

    public StandardMethodMetadata(Method introspectedMethod, String annotationName) {
        Assert.notNull(introspectedMethod, "Method must not be null");
        this.introspectedMethod = introspectedMethod;
        this.annotationName = annotationName;
    }

    @Override
    public String getMethodName() {
        return this.introspectedMethod.getName();
    }

    @Override
    public String getDeclaringClassName() {
        return this.introspectedMethod.getDeclaringClass().getName();
    }

    @Override
    public String getReturnTypeName() {
        return this.introspectedMethod.getReturnType().getName();
    }

    @Override
    public String getAnnotationName() {
        return this.annotationName;
    }

    @Override
    public Method getIntrospectedMethod() {
        return this.introspectedMethod;
    }

}
