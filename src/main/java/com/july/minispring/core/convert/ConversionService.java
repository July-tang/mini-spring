package com.july.minispring.core.convert;

/**
 * 类型转换抽象接口
 *
 * @author july
 */
public interface ConversionService {

    boolean canConvert(Class<?> sourceType, Class<?> targetType);

    <T> T convert(Object source, Class<T> targetType);
}
