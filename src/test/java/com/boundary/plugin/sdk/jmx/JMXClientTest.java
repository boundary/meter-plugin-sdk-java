package com.boundary.plugin.sdk.jmx;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class JMXClientTest {
	
	private static ExampleAgent agent;
	
	private final String name = "com.boundary.plugin.sdk.jmx.ExampleAgent";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		agent = new ExampleAgent();
		agent.start();

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		agent.stop();
	}

	@Before
	public void setUp() throws Exception {
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
	public void testConnectStringIntStringString() {
//		fail("Not yet implemented");
	}

}
