package com.july.minispring.test.ioc;

import com.july.minispring.context.support.ClassPathXmlApplicationContext;
import com.july.minispring.test.bean.A;
import com.july.minispring.test.bean.B;
import org.junit.Test;


import static org.assertj.core.api.Java6Assertions.assertThat;


public class CircularReferenceWithoutProxyBeanTest {

	@Test
	public void testCircularReference() throws Exception {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:circular-reference-without-proxy-bean.xml");
		A a = applicationContext.getBean("a", A.class);
		B b = applicationContext.getBean("b", B.class);
		assertThat(a.getB() == b).isTrue();
	}
}
