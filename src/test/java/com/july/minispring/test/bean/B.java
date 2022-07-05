package com.july.minispring.test.bean;


import com.july.minispring.beans.factory.annotation.Autowired;

public class B {

	private A a;

	public A getA() {
		return a;
	}

	public void setA(A a) {
		this.a = a;
	}
}
