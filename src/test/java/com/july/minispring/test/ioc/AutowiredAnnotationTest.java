package com.july.minispring.test.ioc;

import com.july.minispring.context.support.ClassPathXmlApplicationContext;
import com.july.minispring.test.bean.Person;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author july
 */
public class AutowiredAnnotationTest {

    @Test
    public void testAutowiredAnnotation() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:autowired-annotation.xml");

        Person person = applicationContext.getBean(Person.class);
        System.out.println(person);
        assertThat(person.getCar()).isNotNull();
    }

}
