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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class that is able to start and stop an arbitrary process.
 *
 */
public class RunProcess implements Runnable {
	
    private static Logger LOG = LoggerFactory.getLogger(RunProcess.class);
    
    private final static String EXEC="java -cp target/test-classes com.boundary.plugin.sdk.jmx.ExampleAgent";

    private String command;
	private Process process;
	BufferedReader standardInput;
	BufferedReader standardError;
	Thread thread;
	
	/**
	 * Constructs an instance to managed an executeable
	 * 
	 * @param command Executable and options to run
	 */
	public RunProcess(String command) {
		this.command = command;
	}
	
	/**
	 * Starts a thread to run the process
	 */
	public void start() {
		this.thread = new Thread(this,this.command);
		this.thread.setDaemon(false);
		this.thread.start();
	}


	/**
	 * Handle the running of the command
	 */
	public void run() {
		String s = null;
		try {
			process = Runtime.getRuntime().exec(this.command);
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
	
	/**
	 * Terminates the running command
	 */
	public void stop() {
		process.destroy();
		try {
			process.waitFor();
		} catch (IllegalThreadStateException it) {

		} catch (InterruptedException e) {

		}
	}

	public static void main(String []args) throws InterruptedException {
		RunProcess agentExec = new RunProcess(RunProcess.EXEC);
		agentExec.start();
		Thread.sleep(5000);
		agentExec.stop();
	}
}
