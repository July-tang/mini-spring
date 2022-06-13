package com.july.minispring.test.ioc;

import com.july.minispring.beans.BeansException;
import com.july.minispring.context.support.ClassPathXmlApplicationContext;
import com.july.minispring.test.bean.Car;
import com.july.minispring.test.bean.Person;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author july
 */
public class ApplicationContextTest {

    @Test
    public void testApplicationContext() throws BeansException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");


        Person person = (Person) applicationContext.getBean("person");
        System.out.println(person);
        assertThat(person.getName()).isEqualTo("ivy");

        Car car = (Car) applicationContext.getBean("car");
        System.out.println(car);
        assertThat(car.getBrand()).isEqualTo("lamborghini");
    }
}
