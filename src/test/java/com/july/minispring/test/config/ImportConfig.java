package com.july.minispring.test.config;

import com.july.minispring.beans.factory.annotation.Value;
import com.july.minispring.context.annotation.Bean;
import com.july.minispring.context.annotation.ComponentScan;
import com.july.minispring.context.annotation.Configuration;
import com.july.minispring.context.annotation.PropertySource;
import com.july.minispring.test.bean.Car;

import java.time.LocalDate;

/**
 * @author july
 */

@Configuration
@PropertySource({"classpath:car2.properties", "classpath:car.properties"})
public class ImportConfig {

    @Value("${price}")
    private int price;
    @Value("${produceDate}")
    private LocalDate produceDate;
    @Value("${brand}")
    private String brand;


    @Bean
    public Car car() {
        Car car = new Car();
        car.setBrand(brand);
        car.setPrice(price);
        car.setProduceDate(produceDate);
        return car;
    }
}
