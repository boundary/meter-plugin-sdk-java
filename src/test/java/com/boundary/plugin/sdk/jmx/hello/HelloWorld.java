package com.boundary.plugin.sdk.jmx.hello;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

public class HelloWorld extends NotificationBroadcasterSupport implements
		HelloWorldMBean {
	private String greeting = null;

	public HelloWorld() {
		this.greeting = "Hello World! I am a Standard MBean";
	}

	public HelloWorld(String greeting) {
		this.greeting = greeting;
	}

	public void setGreeting(String greeting) {
		this.greeting = greeting;
		Notification notification = new Notification(
				"com.boundary.plugin.sdk.jmx.hello.HelloWorld.test", this, -1,
				System.currentTimeMillis(), greeting);
		sendNotification(notification);
	}

	public String getGreeting() {
		return greeting;
	}

	public void printGreeting() {
		System.out.println(greeting);
	}
}
