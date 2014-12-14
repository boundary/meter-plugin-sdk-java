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
		Date d = new Date();
		Measure m = new Measure("BOUNDARY_CPU",3.1459,"great-white-north",d);

		String expectedOutput = "BOUNDARY_CPU 3.1459 great-white-north " + Long.toString(d.getTime()) + "\n";
		
		writer.send(m);
		String output = baos.toString();
		assertEquals("check output",expectedOutput,output);
	}

}
