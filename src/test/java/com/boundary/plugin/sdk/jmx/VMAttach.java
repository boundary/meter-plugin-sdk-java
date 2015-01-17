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

package com.boundary.plugin.sdk.jmx;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.List;
import java.util.Set;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

public class VMAttach {

	public static void main(String args[]) throws Exception {
//		if (args.length != 1) {
//			System.err.println("Please provide process id");
//			System.exit(-1);
//		}
		List <VirtualMachineDescriptor> vmDescriptors = VirtualMachine.list();
		VirtualMachineDescriptor vmd = null;
		for (VirtualMachineDescriptor v: vmDescriptors) {
			System.out.println(v.displayName());
		}
		
		VirtualMachine vm = VirtualMachine.attach(vmd);
		String connectorAddr = vm.getAgentProperties().getProperty(
				"com.sun.management.jmxremote.localConnectorAddress");
		if (connectorAddr == null) {
			String agent = vm.getSystemProperties().getProperty("java.home")
					+ File.separator + "lib" + File.separator
					+ "management-agent.jar";
			vm.loadAgent(agent);
			connectorAddr = vm.getAgentProperties().getProperty(
					"com.sun.management.jmxremote.localConnectorAddress");
		}
		JMXServiceURL serviceURL = new JMXServiceURL(connectorAddr);
		JMXConnector connector = JMXConnectorFactory.connect(serviceURL);
		MBeanServerConnection mbsc = connector.getMBeanServerConnection();
		
		
		ObjectName objName = new ObjectName(
				ManagementFactory.THREAD_MXBEAN_NAME);
		Set<ObjectName> mbeans = mbsc.queryNames(objName, null);
		for (ObjectName name : mbeans) {
			ThreadMXBean threadBean;
			threadBean = ManagementFactory.newPlatformMXBeanProxy(mbsc,
					name.toString(), ThreadMXBean.class);
			long threadIds[] = threadBean.getAllThreadIds();
			for (long threadId : threadIds) {
				ThreadInfo threadInfo = threadBean.getThreadInfo(threadId);
				System.out.println(threadInfo.getThreadName() + " / "
						+ threadInfo.getThreadState());
			}
		}
	}
}

