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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MoveFileTest {
	
	private final static String TARGET_DIR="target/unittest";
	private final static String FROM_PATH="target/unittest/example.txt";
	private final static String TO_PATH="target/unittest/example-copy.txt";
	
	private final static long DEFAULT_FILE_SIZE = 1024;
	
	public static void mkdir(String dirPath) throws IOException {
		File dir = new File(dirPath);
		
		if (dir.exists() == false) {
			dir.mkdirs();
		}
	}
	
	public static void mkfile(String path,long kbs) throws IOException {
		RandomAccessFile f = new RandomAccessFile(path, "rw");
		f.setLength(1024 * kbs);
		f.close();
	}
	
	public static void rmfile(String path) throws IOException {
		File f = new File(path);
		if (f.exists()) {
			f.delete();
		}
	}
	
	public static void touch(File file) throws IOException{
	    long timestamp = System.currentTimeMillis();
	    touch(file, timestamp);
	}

	public static void touch(File file, long timestamp) throws IOException{
	    if (file.exists() == false) {
	       new FileOutputStream(file).close();
	    }

	    file.setLastModified(timestamp);
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		mkdir(TARGET_DIR);
		mkfile(FROM_PATH,DEFAULT_FILE_SIZE);
	}

	@After
	public void tearDown() throws Exception {
		//rmfile(TARGET_DIR);
	}

	@Test
	public void testMoveFile() {
		MoveFile mv = new MoveFile();
		String [] args = {FROM_PATH,TO_PATH};
		mv.execute(args);
		assertTrue("Check if file exists", new File(TO_PATH).exists());
	}

	@Test
	public void testExecute() {
//		fail("Not yet implemented");
	}

	@Test
	public void testMain() {
//		fail("Not yet implemented");
	}

}
