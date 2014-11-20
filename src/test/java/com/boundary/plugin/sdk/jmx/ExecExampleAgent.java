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

import java.io.*;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecExampleAgent implements Runnable {
	
    private static Logger LOG = LoggerFactory.getLogger(ExecExampleAgent.class);
    
    private final String EXEC="java -cp target/test-classes com.boundary.plugin.sdk.jmx.ExampleAgent";

	private Process process;
	BufferedReader standardInput;
	BufferedReader standardError;
	Thread thread;
	
	public ExecExampleAgent() {
		
	}
	
	/**
	 * Starts a thread to run the MBean server
	 */
	public void start() {
		this.thread = new Thread(this,"ExecExampleAgent");
		this.thread.setDaemon(false);
		this.thread.start();
	}


	public void run() {
		String s = null;
		try {
			process = Runtime.getRuntime().exec(this.EXEC);
			standardInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
			standardError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

			while ((s = standardInput.readLine()) != null) {
				LOG.debug("stdout: " + s);
			}

			while ((s = standardError.readLine()) != null) {
				LOG.debug("stderr: " + s);
			}
		} catch (IOException e) {
		}
	}
	
	public void stop() {
		while(process.isAlive()) {
			process.destroy();
			try {
				process.waitFor(1000,TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String []args) throws InterruptedException {
		ExecExampleAgent agentExec = new ExecExampleAgent();
		agentExec.start();
		Thread.sleep(5000);
		agentExec.stop();
	}
}
