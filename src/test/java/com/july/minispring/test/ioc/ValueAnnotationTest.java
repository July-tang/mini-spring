package com.july.minispring.test.ioc;

import com.july.minispring.context.support.ClassPathXmlApplicationContext;
import com.july.minispring.test.bean.Car;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author july
 */
public class ValueAnnotationTest {

    @Test
    public void testValueAnnotation() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:value-annotation.xml");

        Car car = applicationContext.getBean("car", Car.class);
        System.out.println(car);
        assertThat(car.getBrand()).isEqualTo("lamborghini");
    }
}
