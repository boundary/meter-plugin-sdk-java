//Copyright 2014 Boundary, Inc.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//  http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.
package com.boundary.plugin.sdk.jmx;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.plugin.sdk.CollectorDispatcher;
import com.boundary.plugin.sdk.MeasurementSink;
import com.boundary.plugin.sdk.Plugin;
import com.boundary.plugin.sdk.PluginRunner;
import com.boundary.plugin.sdk.jmx.JMXCollector;
import com.boundary.plugin.sdk.jmx.JMXPluginConfiguration;
import com.boundary.plugin.sdk.jmx.JMXPluginConfigurationItem;
import com.boundary.plugin.sdk.jmx.MBeanMap;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JMXPlugin implements Plugin<JMXPluginConfiguration> {
	
	private static Logger LOG = LoggerFactory.getLogger(JMXPlugin.class);

	private JMXPluginConfiguration configuration;
	private CollectorDispatcher dispatcher;
	private MeasurementSink output;
	private MBeanMap mbeanMap;
	private final String MBEAN_MAP_PATH="plugin-config/mbeans.json";
	private final String PLUGIN_PARAM_PATH="plugin.json";
	
	@Override
	public void setMeasureOutput(MeasurementSink output) {
		LOG.info("setting measureoutput");
		this.output = output;
	}

	@Override
	public void setConfiguration(JMXPluginConfiguration configuration) {
		this.configuration = configuration;
	}

	public void loadConfiguration() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			JMXPluginConfiguration configuration = mapper.readValue(new File(PLUGIN_PARAM_PATH), JMXPluginConfiguration.class);
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
		try {
			mbeanMap = mapper.readValue(new File(MBEAN_MAP_PATH), MBeanMap.class);
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
	public void setDispatcher(CollectorDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	@Override
	public void run() {
		ArrayList<JMXPluginConfigurationItem> items = configuration.getItems();
		for(JMXPluginConfigurationItem i : items) {
			dispatcher.addCollector(new JMXCollector(i.getName(),i,mbeanMap,output));
		}
		dispatcher.run();
	}
}

