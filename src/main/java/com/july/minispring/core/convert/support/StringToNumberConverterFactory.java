package com.july.minispring.core.convert.support;

import com.july.minispring.core.convert.converter.Converter;
import com.july.minispring.core.convert.converter.ConverterFactory;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author july
 */
public class StringToNumberConverterFactory implements ConverterFactory<String, Number> {

    @Override
    public <T extends Number> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToNumber<>(targetType);
    }

    @SuppressWarnings("unchecked")
    private static final class StringToNumber<T extends Number> implements Converter<String, T> {

        private final Class<T> targetType;

        public StringToNumber(Class<T> targetType) {
            this.targetType = targetType;
        }

        @Override
        public T convert(String source) {
            if (source.isEmpty()) return null;
            if (targetType.equals(Integer.class)) {
                return (T)Integer.valueOf(source);
            } else if (targetType.equals(Long.class)) {
                return (T)Long.valueOf(source);
            } else if (targetType.equals(BigInteger.class)) {
                return (T)new BigInteger(source);
            } else if (targetType.equals(BigDecimal.class) || targetType.equals(Number.class)) {
                return (T)new BigDecimal(source);
            }
            else {
                throw new IllegalArgumentException(
                        "Cannot convert String [" + source + "] to target class [" + targetType.getName() + "]");
            }
        }
    }
}
