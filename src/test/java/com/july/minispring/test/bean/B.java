package com.july.minispring.test.bean;


public class B {

	public B() {
		System.out.println("-----执行B的构造方法-----");
	}

	private A a;

	public A getA() {
		return a;
	}

	public void setA(A a) {
		this.a = a;
	}
}
