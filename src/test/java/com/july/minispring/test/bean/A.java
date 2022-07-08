package com.july.minispring.test.bean;


public class A {

	public A() {
		System.out.println("-----执行A的构造方法-----");
	}

	private B b;

	public void func(){}

	public B getB() {
		return b;
	}

	public void setB(B b) {
		this.b = b;
	}
}
