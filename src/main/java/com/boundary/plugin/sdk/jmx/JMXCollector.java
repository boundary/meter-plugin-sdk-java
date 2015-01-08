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

import java.util.Date;

import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.plugin.sdk.Collector;
import com.boundary.plugin.sdk.Measurement;
import com.boundary.plugin.sdk.MeasurementSink;
import com.boundary.plugin.sdk.PluginUtil;

/**
 * Handles the collection of metrics from a JMX connection.
 * 
 * <ol>
 * <li>JMX Client connection and authentication information</li>
 * <li>MBean collection information</li>
 * </ol>
 * 
 * Reponsabilities
 * <ol>
 * <li>Establish connection to the java virtual machine.</li>
 * <li>Handle scheduling of sampling of the MBean attributes</li>
 * </ol>
 * @author davidg
 *
 */
public class JMXCollector implements Collector {
	
	private static Logger LOG = LoggerFactory.getLogger(JMXCollector.class);

	private JMXClient client;
	private JMXPluginConfigurationItem item;
	private MBeanMap mbeanMap;
	private MeasurementSink output;
	private String name;

	public JMXCollector(String name,
			JMXPluginConfigurationItem item,
			MBeanMap mbeanMap,
			MeasurementSink output) {
		this.name = name;
		this.client = new JMXClient();
		this.item = item;
		this.mbeanMap = mbeanMap;
		this.output = output;
	}

	@Override
	public Measurement[] getMeasures() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void run() {
		
		if (client.connect(item.getHost(), item.getPort()) == false) {
			LOG.error("Failed to connect to host: {}, port: ",item.getHost(),item.getPort());
			new Exception(String.format("Failed to connect to host: %s, port: %d",item.getHost(),item.getPort()));
		}

		MBeanServerConnection connection = client.getMBeanServerConnection();
		if (connection == null) {
			
		}
		
		String source = null;
		if (item.getSource() == null) {
			source = PluginUtil.getHostname();
		}
		else {
			source = item.getSource();
		}

		while(true) {
			try {
				long start = new Date().getTime();
				for (MBeanEntry entry : mbeanMap.getMap()) {
					ObjectName name = new ObjectName(entry.getObjectName());
					ObjectInstance instance = connection.getObjectInstance(name);
					for (MBeanAttributes attr : entry.getAttributes()) {
						Object obj = connection.getAttribute(instance.getObjectName(),attr.getAttribute());
						Number v = (Number)obj.getClass().cast(obj);
						Measurement m = new Measurement(attr.getMetricName(),v);
						output.send(m);
					}
				}
				long stop = new Date().getTime();
				long delta = stop - start;
				delta = delta < 0 ? 0 : delta;
				Thread.sleep(Long.parseLong(item.getPollInterval()) - delta);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}
