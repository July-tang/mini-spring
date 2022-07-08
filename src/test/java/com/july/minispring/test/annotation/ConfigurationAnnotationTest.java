package com.july.minispring.test.annotation;

import com.july.minispring.context.ApplicationContext;
import com.july.minispring.context.support.AnnotationConfigApplicationContext;
import com.july.minispring.test.bean.A;
import com.july.minispring.test.bean.B;
import com.july.minispring.test.bean.Car;
import com.july.minispring.test.bean.Person;
import com.july.minispring.test.config.SpringConfig;
import com.july.minispring.test.config.ComponentTestConfig;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author july
 */
public class ConfigurationAnnotationTest {

    @Test
    public void testConfiguration() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);

        A a = applicationContext.getBean("testA", A.class);
        B b = applicationContext.getBean("b", B.class);
        Car car = applicationContext.getBean("car", Car.class);
        Person person = applicationContext.getBean(Person.class);
        System.out.println(person);
        assertThat(b.getA() == a).isTrue();
        assertThat(car == person.getCar()).isTrue();
    }
}
