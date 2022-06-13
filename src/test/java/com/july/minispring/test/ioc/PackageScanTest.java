package com.july.minispring.test.ioc;

import com.july.minispring.context.support.ClassPathXmlApplicationContext;
import com.july.minispring.test.bean.Car;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author july
 */
public class PackageScanTest {

    @Test
    public void testPackageScan() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:package-scan.xml");

        Car car = applicationContext.getBean("car", Car.class);
        System.out.println(car);
        assertThat(car).isNotNull();
    }
}
