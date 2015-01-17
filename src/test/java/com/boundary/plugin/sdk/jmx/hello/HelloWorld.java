// Copyright 2014-2015 Boundary, Inc.
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
