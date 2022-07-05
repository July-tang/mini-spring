package com.july.minispring.test.config;

import com.july.minispring.context.annotation.ComponentScan;
import com.july.minispring.context.annotation.Configuration;
import com.july.minispring.context.annotation.PropertySource;

/**
 * @author july
 */
@Configuration
@ComponentScan("com.july.minispring.test.bean")
@PropertySource({"classpath:car.properties", "classpath:car2.properties"})
public class ComponentTestConfig {
}
