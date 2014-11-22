package com.boundary.plugin.sdk.jmx.hello;

public class HelloWorld implements HelloWorldMBean {
	private String greeting = null;

	public HelloWorld() {
		this.greeting = "Hello World! I am a Standard MBean";
	}

	public HelloWorld(String greeting) {
		this.greeting = greeting;
	}

	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}

	public String getGreeting() {
		return greeting;
	}

	public void printGreeting() {
		System.out.println(greeting);
	}
}
