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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.boundary.plugin.sdk.MeasureOutput;
import com.boundary.plugin.sdk.Plugin;
import com.boundary.plugin.sdk.PluginConfiguration;
import com.boundary.plugin.sdk.PluginDispatcher;
import com.boundary.plugin.sdk.PluginRunner;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SimplePlugin implements Plugin<SimplePluginConfiguration> {

	SimplePluginConfiguration configuration;
	PluginDispatcher dispatcher;
	MeasureOutput output;

	@Override
	public void setConfiguration(SimplePluginConfiguration configuration) {
		this.configuration = configuration;
	}
	
	@Override
	public void loadConfiguration() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			SimplePluginConfiguration configuration = mapper.readValue(new File("param.json"), SimplePluginConfiguration.class);
			setConfiguration(configuration);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setDispatcher(PluginDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	@Override
	public void run() {
		
		ArrayList<SimplePluginConfigurationItem> items = configuration.getItems();
		for(SimplePluginConfigurationItem i : items) {
			dispatcher.addCollector(new SimpleCollector(i.getName()));
		}

		dispatcher.run();
	}

	public static void main(String[] args) {
		PluginRunner plugin = new PluginRunner("com.boundary.plugin.sdk.examples.SimplePlugin");
		plugin.run();
	}

	@Override
	public void setMeasureOutput(MeasureOutput output) {
		this.output = output;
	}
}
