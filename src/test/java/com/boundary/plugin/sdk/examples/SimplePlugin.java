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

import java.util.Random;

import com.boundary.plugin.sdk.Measure;
import com.boundary.plugin.sdk.Plugin;
import com.boundary.plugin.sdk.PluginConfiguration;
import com.boundary.plugin.sdk.PluginDispatcher;
import com.boundary.plugin.sdk.MeasureWriter;
import com.boundary.plugin.sdk.MeasureOutput;

public class SimplePlugin implements Plugin {
	
	PluginConfiguration<SimpleConfiguration> configuration;
	PluginDispatcher dispatcher;

	@Override
	public void setConfiguration(PluginConfiguration<?> configuration) {
		this.configuration = (PluginConfiguration<SimpleConfiguration>)configuration;
	}

	@Override
	public void setDispatcher(PluginDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	@Override
	public void run() {
		MeasureOutput output = new MeasureWriter().getInstance();
		Random rand = new Random();
		
		while(true) {
			try {
				Measure m = new Measure();
				m.setName("SIMPLE_METRIC");
				m.setSource("simple");

				int  n = rand.nextInt(50) + 1;
				m.setValue(Integer.toString(n));
				
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void main(String[] args) {
		
	}
}
