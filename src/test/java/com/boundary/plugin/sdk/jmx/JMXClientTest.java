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

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Set;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class JMXClientTest {
	
	private static RunProcess attachAgent;
	private static RunProcess rmiAgent;
	
	private MBeanServerConnection connection;
	
	private final String name = "com.boundary.plugin.sdk.jmx.ExampleAgent";
    private final static String ATTACH_EXEC = "java -cp target/test-classes com.boundary.plugin.sdk.jmx.ExampleAgent";
    private final static int RMI_PORT = 12345;
    private final static String RMI_EXEC = "java -cp target/test-classes com.boundary.plugin.sdk.jmx.ExampleAgent -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.port=" 
    		+ RMI_PORT;
	private ObjectName harmonicBean;
	private String HARMONIC_BEAN_ATTR_VALUE = "Value";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		attachAgent = new RunProcess(JMXClientTest.ATTACH_EXEC);
		attachAgent.start();
		
		rmiAgent = new RunProcess(JMXClientTest.RMI_EXEC);
		rmiAgent.start();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		attachAgent.stop();
		rmiAgent.stop();
	}

	@Before
	public void setUp() throws Exception {
		JMXClient client = new JMXClient();
		client.connect(name);
		this.connection = client.getMBeanServerConnection();
		harmonicBean = new ObjectName("com.boundary.plugin.jmx:type=Harmonic");
	}

	@After
	public void tearDown() throws Exception {

	}
	
	@Ignore("Not working from JUnit test")
	@Test
	public void testConnectHostPort() {
		JMXClient client = new JMXClient();
		try {
			client.connect("localhost",RMI_PORT);
			MBeanServerConnection connection = client.getMBeanServerConnection();
			System.out.println(connection.getMBeanCount());
		} catch(IOException i) {
			
		}
	}

	@Test
	public void testConnectString() throws Exception {
		JMXClient client = new JMXClient();
		
		System.out.println();
		assertTrue("Check attach connect",client.connect(name));
	}

	@Test
	public void testQueryMBeans() throws Exception {
		JMXClient client = new JMXClient();
		client.connect(name);
		ObjectName mbean = new ObjectName("com.boundary.plugin.jmx:type=Harmonic");
		Set<ObjectInstance> results = client.queryMBeans(mbean);
		assertNotNull("test for not null", results);
		assertEquals("ensure count of 1",1,results.size());
	}
	
	@Test
	public void testGetMBeanInfo() throws Exception {

		ObjectName mbean = new ObjectName("com.boundary.plugin.jmx:type=Harmonic");
		MBeanInfo info = connection.getMBeanInfo(mbean);
		MBeanAttributeInfo attr[] = info.getAttributes();
		System.out.println(info);
		for (MBeanAttributeInfo i : attr) {
			System.out.println(i);
		}
//		for (ObjectInstance b : results) {
//			conn.get
//		}
	}
	
	@Test
	public void testGetMBeanValue() throws Exception {
		double currentValue = (double) this.connection.getAttribute(harmonicBean,HARMONIC_BEAN_ATTR_VALUE);
		System.out.println(currentValue);
	}

}
