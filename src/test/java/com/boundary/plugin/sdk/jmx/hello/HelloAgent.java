package com.boundary.plugin.sdk.jmx.hello;

import javax.management.*;
import com.sun.jdmk.comm.*;

public class HelloAgent {
	private MBeanServer mbs = null;

	public HelloAgent() {
		mbs = MBeanServerFactory.createMBeanServer("MyAgent");

		HtmlAdaptorServer adapter = new HtmlAdaptorServer();
		HelloWorld helloWorld = new HelloWorld();
		ObjectName adapterName = null;
		ObjectName helloWorldName = null;

		try {
			helloWorldName = new ObjectName("HelloAgent:name=helloWorld1");
			mbs.registerMBean(helloWorld, helloWorldName);
			adapterName = new ObjectName("HelloAgent:name=htmladapter,port=9092");
			adapter.setPort(9092);
			mbs.registerMBean(adapter, adapterName);
			adapter.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		System.out.println("HelloAgent is running");
		HelloAgent agent = new HelloAgent();
	}
}

