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

package com.boundary.plugin.sdk.examples;

import com.boundary.plugin.sdk.Plugin;
import com.boundary.plugin.sdk.PluginConfiguration;
import com.boundary.plugin.sdk.PluginDispatcher;
import com.boundary.plugin.sdk.PluginRunner;

public class SimplePlugin implements Plugin<SimpleConfiguration> {

	PluginConfiguration<SimpleConfiguration> configuration;
	PluginDispatcher dispatcher;

	@Override
	public void setConfiguration(
			PluginConfiguration<SimpleConfiguration> configuration) {
		this.configuration = configuration;
	}

	@Override
	public void setDispatcher(PluginDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	@Override
	public void run() {
		SimpleCollector one = new SimpleCollector("COLLECTOR_ONE");
		SimpleCollector two = new SimpleCollector("COLLECTOR_TWO");

		dispatcher.addCollector(one);
		dispatcher.addCollector(two);
		dispatcher.run();
	}

	public static void main(String[] args) {
		PluginRunner plugin = new PluginRunner(
				"com.boundary.plugin.sdk.examples.SimplePlugin");
		plugin.run();
	}
}
