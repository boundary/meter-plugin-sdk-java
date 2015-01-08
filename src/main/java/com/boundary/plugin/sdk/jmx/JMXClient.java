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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

/**
 * Encapsulates the interaction with an virtual machine via JMX.
 */
public class JMXClient {

    private static Logger LOG = LoggerFactory.getLogger(JMXClient.class);

	private MBeanServerConnection mbeanServerConnection;
	private JMXConnector jmxConnector;
	private final String LOCAL_CONNECTOR_ADDRESS_PROPERTY = "com.sun.management.jmxremote.localConnectorAddress";
	private Exception exception;

	/**
	 * Default constructor
	 */
	public JMXClient() {
		jmxConnector = null;
		mbeanServerConnection = null;
	}

	/**
	 * Searches for the {@link VirtualMachineDescriptor} associated with the
	 * passed in name.
	 * 
	 * @param name Name of the virtual machine to look up.
	 * @return {@link VirtualMachineDescriptor}
	 */
	private VirtualMachineDescriptor getVMDescriptor(String name) {
		List<VirtualMachineDescriptor> vms = VirtualMachine.list();
		VirtualMachineDescriptor vmDescriptor = null;
		for (VirtualMachineDescriptor v : vms) {
			LOG.debug(v.id() + "-" + v.displayName());
			if (v.displayName().equals(name)) {
				vmDescriptor = v;
				break;
			}
		}
		return vmDescriptor;
	}

	/**
	 * Creates {@link MBeanServerConnection} from a url and environment.
	 * 
	 * @param url Service URL
	 * @param env Environment to be sent to create the connection.
	 * @throws IOException If we fail to connect.
	 */
	private void getMBeanServerConnection(String url,Map<String, String[]> env) throws IOException {
		JMXServiceURL serviceUrl = new JMXServiceURL(url);
		jmxConnector = JMXConnectorFactory.connect(serviceUrl,env);
		this.mbeanServerConnection = jmxConnector.getMBeanServerConnection();
	}
	
	/**
	 * Builds the path to the management agent.
	 * 
	 * @param javaHome Path to the "java.home" system property
	 * @return String path to management agent jar file
	 */
	private String getAgentPath(String javaHome) {
		return javaHome + File.separator + "lib" + File.separator + "management-agent.jar";
	}

	/**
	 * Connect to MBean Server via the Attach API.
	 * 
	 * TODO: Better handling of the exceptions, duplicate display names
	 * 
	 * @param name Display name of the VM.
	 * @throws Exception
	 */
	public boolean connect(String name) {
		boolean connected = false;

		try {
			VirtualMachineDescriptor vmd = getVMDescriptor(name);

			VirtualMachine vm = VirtualMachine.attach(vmd);

			String address = vm.getAgentProperties().getProperty(
					LOCAL_CONNECTOR_ADDRESS_PROPERTY);
			if (address == null) {
				String agentPath = this.getAgentPath(vm.getSystemProperties()
						.getProperty("java.home"));
				vm.loadAgent(agentPath);
				address = vm.getAgentProperties().getProperty(
						LOCAL_CONNECTOR_ADDRESS_PROPERTY);
			}
			getMBeanServerConnection(address, null);
			connected = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connected;
	}
	
	/**
	 * Formats a a service url from the input host and port.
	 * @param host
	 * @param port
	 * @return String
	 */
	private String getRMIUrl(String host,int port) {
		return String.format("service:jmx:rmi:///jndi/rmi://%s:%d/jmxrmi",host,port);
	}

	/**
	 * Connect to MBean Server via RMI using host and port.
	 * 
	 * @param host hostname where the jvm is running
	 * @param port Listening port of the jvm
	 * @returns boolean
	 */
	public boolean connect(String host, int port) {
		boolean connected = false;

		try {
			getMBeanServerConnection(this.getRMIUrl(host,port),null);
			connected = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connected;
	}
	
	public void disconnect() {
		try {
			jmxConnector.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			jmxConnector = null;
			mbeanServerConnection = null;
		}
	}

	/**
	 * Connect to MBean Server via RMI using host, port, user, password.
	 * @param host hostname where the jvm is running
	 * @param port Listening port of the jvm
	 * @param user User name to used to authenticate
	 * @param password Credentials used to authenticate
	 * @throws Exception
	 */
	public boolean connect(String host,int port,String user,String password) throws Exception {
		boolean connected = false;

		try {
			Map<String, String[]> env = new HashMap<>();
			String[] credentials = { user, password };
			env.put(JMXConnector.CREDENTIALS, credentials);
			getMBeanServerConnection(this.getRMIUrl(host, port), env);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connected;
	}
	
	/**
	 * Encapsulates a query to a {@link MBeanServerConnection}
	 * @param mbeanName
	 * @return {@link Set<ObjectInstance>} returns null if the query fails
	 * @throws IOException 
	 */
	public Set<ObjectInstance> queryMBeans(ObjectName mbeanName) {
		Set<ObjectInstance> result = null;
		try {
			result = this.mbeanServerConnection.queryMBeans(mbeanName, null); 
		} catch (Exception e) {
			this.exception = e;
		}
		return result;
	}
	
	/**
	 * Returns the underlying {@link MBeanServerConnection} of the instance.
	 * @return {@link MBeanServerConnection}
	 */
	public final MBeanServerConnection getMBeanServerConnection() {
		return this.mbeanServerConnection;
	}
	
	/**
	 * Returns an {@link Exception} if the connect() method fails, returns false and
	 * additional details about an {@link Exception} are required.
	 * @return {@link Exception}
	 */
	public final Exception getException() {
		return this.exception;
	}
}
