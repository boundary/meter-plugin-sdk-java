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

package com.boundary.plugin.sdk;

import com.boundary.plugin.sdk.MeasureOutputSupport.Type;

public class PluginRunner {
	
	private String className;
	private PluginDispatcher dispatcher;
	
	/**
	 * Default constructor
	 * @param className Class name of the plugin to load
	 */
	public PluginRunner(String className) {
		this.className = className;
		this.dispatcher = new PluginDispatcher();
	}
	
	/**
	 * Loads the plugin class and calls its run() method.
	 */
	public void run() {
		Plugin plugin = null;
		try {
			plugin = (Plugin) Class.forName(this.className).newInstance();
			plugin.setDispatcher(dispatcher);
			plugin.loadConfiguration();
			plugin.setMeasureOutput(MeasureOutputSupport.getInstance(Type.STDOUT));
		} catch (Exception e) {
			e.printStackTrace();
		}
		plugin.run();
	}
	
	/**
	 * Entry point for running the plugin
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.exit(1);
		}
		PluginRunner plugin = new PluginRunner(args[0]);
		plugin.run();
	}
}
