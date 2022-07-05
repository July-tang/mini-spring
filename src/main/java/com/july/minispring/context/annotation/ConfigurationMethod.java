package com.july.minispring.context.annotation;

import com.july.minispring.core.type.MethodMetadata;

/**
 * @author july
 */
abstract class ConfigurationMethod {

    protected final MethodMetadata metadata;

    protected final ConfigurationClass configurationClass;

    public ConfigurationMethod(MethodMetadata metadata, ConfigurationClass configurationClass) {
        this.metadata = metadata;
        this.configurationClass = configurationClass;
    }

    public MethodMetadata getMetadata() {
        return metadata;
    }

    public ConfigurationClass getConfigurationClass() {
        return configurationClass;
    }
}
