package com.boundary.plugin.sdk;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MeasureWriterTest {
	private PrintStream old;
	ByteArrayOutputStream baos;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		old = System.out;
		baos = new ByteArrayOutputStream();
		System.setOut(new PrintStream(baos));
	}

	@After
	public void tearDown() throws Exception {
		System.setOut(old);
	}

	@Test
	public void testWriter() {
		MeasureOutputStandardOut writer = new MeasureOutputStandardOut();
		Measure m = new Measure();
		Date d = new Date();
		
		m.setName("BOUNDARY_CPU");
		m.setValue("3.1459");
		m.setSource("great-white-north");
		m.setTimestamp(d);
		String expectedOutput = "BOUNDARY_CPU 3.1459 great-white-north " + Long.toString(d.getTime()) + "\n";
		
		writer.send(m);
		String output = baos.toString();
		assertEquals("check output",expectedOutput,output);
	}

}
