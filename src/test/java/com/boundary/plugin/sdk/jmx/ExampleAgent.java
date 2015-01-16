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

package com.boundary.plugin.sdk.jmx;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * Implements a test MBeanServer for testing.
 *
 */
public class ExampleAgent implements Runnable {

	private Thread thread;

	/**
	 * Starts a thread to run the MBean server
	 */
	public void start() {
		this.thread = new Thread(this,"MBeanServer");
		this.thread.setDaemon(false);
		this.thread.start();
	}
	
	public void stop() {
		this.thread.interrupt();
	}

	public static void main(String[] args) throws InterruptedException {
		ExampleAgent agent = new ExampleAgent();
		agent.start();
		Thread.sleep(Long.MAX_VALUE);
		agent.stop();
	}
	@Override
	public void run() {
		boolean started = false;
		while (started == false) {
			try {
				MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
				ObjectName example = new ObjectName(
						"com.boundary.plugin.jmx:type=Example");
				ObjectName harmonic = new ObjectName(
						"com.boundary.plugin.jmx:type=Harmonic");

				Example exampleMBean = new Example();
				Harmonic harmonicMBean = new Harmonic();
				mbeanServer.registerMBean(exampleMBean, example);
				mbeanServer.registerMBean(harmonicMBean, harmonic);

			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				System.out.println("MBServer started");
				started = true;
				Thread.sleep(Long.MAX_VALUE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}