package com.july.minispring.test.common;

import com.july.minispring.core.convert.converter.Converter;

/**
 * @author july
 */
public class StringToIntegerConverter implements Converter<String, Integer> {
    @Override
    public Integer convert(String source) {
        return Integer.valueOf(source);
    }
}
