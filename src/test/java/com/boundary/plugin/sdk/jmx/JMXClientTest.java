package com.boundary.plugin.sdk.jmx;

import static org.junit.Assert.*;

import java.util.Set;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class JMXClientTest {
	
	private static ExecExampleAgent agent;
	
	private MBeanServerConnection connection;
	
	private final String name = "com.boundary.plugin.sdk.jmx.ExampleAgent";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		agent = new ExecExampleAgent();
		agent.start();

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		agent.stop();
	}

	@Before
	public void setUp() throws Exception {
		JMXClient client = new JMXClient();
		client.connect(name);
		this.connection = client.getMBeanServerConnection();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testJMXClient() {
		

	}

	@Test
	public void testConnectString() throws Exception {
		JMXClient client = new JMXClient();
		
		System.out.println();
		assertTrue("Check attach connect",client.connect(name));
	}

	@Test
	public void testConnectStringInt() throws Exception {
		JMXClient client = new JMXClient();
		
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
		JMXClient client = new JMXClient();
		client.connect(name);
		MBeanServerConnection conn = client.getMBeanServerConnection();
		ObjectName mbean = new ObjectName("com.boundary.plugin.jmx:type=Harmonic");
		MBeanInfo info = conn.getMBeanInfo(mbean);
		MBeanAttributeInfo attr[] = info.getAttributes();
		System.out.println(info);
		for (MBeanAttributeInfo i : attr) {
			System.out.println(i.getName());
		}
//		for (ObjectInstance b : results) {
//			conn.get
//		}
	}
	
	@Test
	public void testGetMBeanValue() throws Exception {
		
	}

}
