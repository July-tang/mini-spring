package com.july.minispring.core.convert.converter;

/**
 * 类型转换工厂
 *
 * @author july
 */
public interface ConverterFactory<S, R> {

    <T extends R> Converter<S, T> getConverter(Class<T> targetType);
}
