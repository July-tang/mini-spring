package com.july.minispring.core.convert.converter;

/**
 * 类型转换抽象接口
 *
 * @author july
 */
public interface Converter<S, T> {

    /**
     * 类型转换
     */
    T convert(S source);
}
