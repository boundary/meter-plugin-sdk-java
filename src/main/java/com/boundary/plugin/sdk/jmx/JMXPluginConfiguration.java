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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.boundary.plugin.sdk.PluginConfiguration;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JMXPluginConfiguration implements PluginConfiguration{

	private ArrayList<JMXPluginConfigurationItem> items;
	private int pollInterval;
	
	public JMXPluginConfiguration() {
		
	}

	public ArrayList<JMXPluginConfigurationItem> getItems() {
		return items;
	}

	public void setItems(ArrayList<JMXPluginConfigurationItem> items) {
		this.items = items;
	}

	public int getPollInterval() {
		return pollInterval;
	}

	public void setPollInterval(int pollInterval) {
		this.pollInterval = pollInterval;
	}
	
	public static JMXPluginConfiguration getConfiguration() {
		ObjectMapper mapper = new ObjectMapper();
		JMXPluginConfiguration configuration = null;
		try {
			configuration = mapper.readValue(new File("param.json"), JMXPluginConfiguration.class);
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
		return configuration;
	}
	
}
