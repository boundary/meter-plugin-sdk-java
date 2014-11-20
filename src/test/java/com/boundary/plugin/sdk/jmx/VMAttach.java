package com.boundary.plugin.sdk.jmx;

import java.lang.management.*;
import java.io.*;
import java.util.*;
import javax.management.*;
import javax.management.remote.*;
import com.sun.tools.attach.*;

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

