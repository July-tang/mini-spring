package com.july.minispring.test.ioc;

import com.july.minispring.beans.BeansException;
import com.july.minispring.context.support.ClassPathXmlApplicationContext;
import com.july.minispring.test.bean.Car;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author july
 */
public class PrototypeBeanTest {


    @Test
    public void testPrototype() throws BeansException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:prototype-bean.xml");

        Car car1 = applicationContext.getBean("car", Car.class);
        Car car2 = applicationContext.getBean("car", Car.class);
        assertThat(car1 != car2).isTrue();
    }
}
