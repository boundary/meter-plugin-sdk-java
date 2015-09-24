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

import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.StringReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import com.boundary.plugin.sdk.PluginConfiguration;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class JMXPluginConfiguration implements PluginConfiguration {

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

	public static JMXPluginConfiguration getConfiguration(final String jsonString) {
		return getConfiguration(new StringReader(jsonString));
	}

	public static JMXPluginConfiguration getConfiguration() {
		JMXPluginConfiguration configuration = null;
		try {
			configuration = getConfiguration(new BufferedReader(new FileReader(PLUGIN_PARAMETER_FILENAME)));	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return configuration;
	}
	
	public static JMXPluginConfiguration getConfiguration(Reader reader) {
		Gson gson = new Gson();
		JMXPluginConfiguration configuration = null;
		try {
			configuration = gson.fromJson(reader, JMXPluginConfiguration.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return configuration;
	}
	
}
