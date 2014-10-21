package com.boundary.plugin.sdk;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MeasureTest {
	
	Measure m;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		m = new Measure();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testName() {
		String expectedName = "foobar";
		m.setName(expectedName);
		assertEquals("Check name",expectedName,m.getName());
	}
	
	@Test
	public void testValue() {
		String expectedValue = "365";
		m.setValue(expectedValue);
		assertEquals("Check value",expectedValue,m.getValue());
	}
	
	@Test
	public void testSource() {
		String expectedSource = "some-machine.somewhere.com";
		m.setSource(expectedSource);
		assertEquals("Check source",expectedSource,m.getSource());
	}
	
	@Test
	public void testTimestamp() {
		Date expectedDate = new Date();
		m.setTimestamp(expectedDate);
		assertEquals("Check timestamp",expectedDate,m.getTimestamp());
	}
	
	

}
