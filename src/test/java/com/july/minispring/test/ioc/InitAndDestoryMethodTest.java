package com.july.minispring.test.ioc;

import com.july.minispring.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

/**
 * @author july
 */
public class InitAndDestoryMethodTest {

    @Test
    public void testInitAndDestroyMethod() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "classpath:init-and-destroy-method.xml");
        applicationContext.close();
    }
}
