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

package com.boundary.plugin.sdk;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit tests for {@link Measurement}
 */
public class MeasureTest {
	
	private final static String NAME = "foobar";
	private final static Number VALUE = 365;
	private final static String SOURCE = "some-machine.somewhere.com";
	private final static Date TIMESTAMP = new Date();


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	private static void assertFields(String name,Number value,String source,Date timestamp,Measurement m) {
		if (name != null) {
			assertEquals("check getName()",name,m.getName());
		}
		if (value != null) {
			assertEquals("check getValue",value,m.getValue());
		}
		if (source != null) {
			assertEquals("check getSource",source,m.getSource());
		}
		if (timestamp != null) {
			assertEquals("check getTimestamp()",timestamp,m.getTimestamp());
		}
	}
	
	@Test
	public void testDefaultConstructor() {
		Measurement m = new Measurement();
		assertFields("",0,"",null,m);
	}
	
	@Test
	public void test2ArgumentConstructor() {
		Measurement m = new Measurement(NAME,VALUE);
		assertFields(NAME,VALUE,null,null,m);
	}

	@Test
	public void test3ArgumentConstructor() {
		Measurement m = new Measurement(NAME,VALUE,SOURCE);
		assertFields(NAME,VALUE,SOURCE,null,m);
	}
	
	@Test
	public void test4ArgumentConstructor() {
		Measurement m = new Measurement(NAME,VALUE,SOURCE,TIMESTAMP);
		assertFields(NAME,VALUE,SOURCE,TIMESTAMP,m);
	}
	
	@Test
	public void testSourceMutablity() throws InterruptedException {
		Measurement m = new Measurement(NAME,VALUE,SOURCE,TIMESTAMP);
		Date d = m.getTimestamp();
		Thread.sleep(1000);
		d = new Date();
		assertFields(NAME,VALUE,SOURCE,TIMESTAMP,m);
	}

	@Test
	public void testFormattingWithNullTimestamp() {
		Measurement m = new Measurement(NAME, VALUE, SOURCE, null);
		final String output = String.format("%s %s %s", NAME, VALUE, SOURCE);
		assertEquals(output, m.toString());
	}

	@Test
	public void testFormattinThatRounds() {
		Measurement m = new Measurement(NAME, 1234.56789, SOURCE, null);
		final String output = "foobar 1234.5679 some-machine.somewhere.com";
		assertEquals(output, m.toString());
	}

}
