package com.july.minispring.test.config;

import com.july.minispring.context.annotation.Bean;
import com.july.minispring.context.annotation.Configuration;
import com.july.minispring.test.bean.A;
import com.july.minispring.test.bean.B;

/**
 * @author july
 */

@Configuration
public class SpringConfig {

    @Bean
    public A testA() {
        return new A();
    }

    //@Bean(name = "b")
    @Bean("b")
    public B testB() {
        return new B();
    }
}
