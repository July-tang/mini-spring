package com.july.minispring.core.convert.converter;

/**
 * 类型转换器注册接口
 *
 * @author july
 */
public interface ConverterRegistry {

    void addConverter(Converter<?, ?> converter);

    void addConverterFactory(ConverterFactory<?, ?> converterFactory);

    void addConverter(GenericConverter converter);
}
