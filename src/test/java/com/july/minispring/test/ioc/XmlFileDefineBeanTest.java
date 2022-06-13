package com.july.minispring.test.ioc;

import com.july.minispring.test.bean.Car;
import com.july.minispring.test.bean.Person;
import com.july.minispring.beans.factory.support.DefaultListableBeanFactory;
import com.july.minispring.beans.factory.xml.XmlBeanDefinitionReader;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class XmlFileDefineBeanTest {

    @Test
    public void testXmlFile() throws Exception {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        beanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");

        Person person = (Person) beanFactory.getBean("person");
        System.out.println(person);
        assertThat(person.getName()).isEqualTo("derek");
        assertThat(person.getCar().getBrand()).isEqualTo("porsche");

        Car car = (Car) beanFactory.getBean("car");
        System.out.println(car);
        assertThat(car.getBrand()).isEqualTo("porsche");
    }
}
