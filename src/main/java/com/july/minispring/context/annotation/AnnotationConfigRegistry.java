package com.july.minispring.context.annotation;

/**
 * 注解配置类接口
 *
 * @author july
 */
public interface AnnotationConfigRegistry {

    /**
     * 注册Component注解类型bean
     *
     * @param componentClasses
     */
    void register(Class<?>... componentClasses);

    /**
     * 扫描指定包目录
     *
     * @param basePackages
     */
    void scan(String... basePackages);
}
