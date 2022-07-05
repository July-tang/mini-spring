package com.july.minispring.context.annotation;


import com.july.minispring.core.type.MethodMetadata;

/**
 * 被@bean注解的方法
 *
 * @author july
 */
public class BeanMethod extends ConfigurationMethod {

    public BeanMethod(MethodMetadata metadata, ConfigurationClass configurationClass) {
        super(metadata, configurationClass);
    }
}
