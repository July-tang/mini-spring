package com.july.minispring.context.support;

import com.july.minispring.beans.factory.FactoryBean;
import com.july.minispring.beans.factory.InitializingBean;
import com.july.minispring.core.convert.ConversionService;
import com.july.minispring.core.convert.converter.Converter;
import com.july.minispring.core.convert.converter.ConverterFactory;
import com.july.minispring.core.convert.converter.GenericConverter;
import com.july.minispring.core.convert.support.DefaultConversionService;
import com.july.minispring.core.convert.support.GenericConversionService;

import java.util.Set;

/**
 * @author july
 */
public class ConversionServiceFactoryBean implements FactoryBean<ConversionService>, InitializingBean {

    private Set<?> converters;

    private GenericConversionService conversionService;

    @Override
    public void afterPropertiesSet() throws Exception {
        conversionService = new DefaultConversionService();
        registerConverters(converters, conversionService);
    }

    private void registerConverters(Set<?> converters, GenericConversionService registry) {
        if (converters != null) {
            for (Object converter : converters) {
                if (converter instanceof GenericConverter) {
                    registry.addConverter((GenericConverter)converter);
                } else if (converter instanceof Converter<?, ?>) {
                    registry.addConverter((Converter<?, ?>)converter);
                } else if (converter instanceof ConverterFactory<?, ?>) {
                    registry.addConverterFactory((ConverterFactory<?, ?>) converter);
                } else {
                    throw new IllegalArgumentException("Each converter object must implement one of the " +
                            "Converter, ConverterFactory, or GenericConverter interfaces");
                }
            }
        }
    }

    @Override
    public ConversionService getObject() throws Exception {
        return this.conversionService;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setConverters(Set<?> converters) {
        this.converters = converters;
    }
}
