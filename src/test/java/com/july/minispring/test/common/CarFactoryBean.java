package com.july.minispring.test.common;

import com.july.minispring.beans.factory.FactoryBean;
import com.july.minispring.test.bean.Car;

/**
 * @author derekyi
 * @date 2020/12/2
 */
public class CarFactoryBean implements FactoryBean<Car> {

	private String brand;

	@Override
	public Car getObject() throws Exception {
		Car car = new Car();
		car.setBrand(brand);
		return car;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
}
