// Copyright 2014 Boundary, Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.boundary.plugin.sdk.jmx.hello;

import javax.management.*;

import com.sun.jdmk.comm.*;

public class HelloAgent implements NotificationListener {
	private MBeanServer mbs = null;

	public HelloAgent() {
		mbs = MBeanServerFactory.createMBeanServer("MyAgent");

		HtmlAdaptorServer adapter = new HtmlAdaptorServer();
		HelloWorld hw = new HelloWorld();
		ObjectName adapterName = null;
		ObjectName helloWorldName = null;

		try {
			adapterName = new ObjectName("HelloAgent:name=htmladapter,port=9092");

			mbs.registerMBean(adapter, adapterName);
			adapter.setPort(9092);
			adapter.start();
			helloWorldName = new ObjectName("HelloAgent:name=helloWorld1");
			mbs.registerMBean(hw,helloWorldName);
			hw.addNotificationListener(this,null,null );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		System.out.println("HelloAgent is running");
		HelloAgent agent = new HelloAgent();
	}

	@Override
	public void handleNotification(Notification notif, Object handback) {
		System.out.println("Receiving notification...");
		System.out.println(notif.getType());
		System.out.println(notif.getMessage());
	}
}

