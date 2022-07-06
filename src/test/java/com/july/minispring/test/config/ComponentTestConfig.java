package com.july.minispring.test.config;

import com.july.minispring.context.annotation.ComponentScan;
import com.july.minispring.context.annotation.Configuration;
import com.july.minispring.context.annotation.Import;
import com.july.minispring.context.annotation.PropertySource;

/**
 * @author july
 */
@Configuration
@Import(ImportConfig.class)
@ComponentScan("com.july.minispring.test.bean")
public class ComponentTestConfig {
}
