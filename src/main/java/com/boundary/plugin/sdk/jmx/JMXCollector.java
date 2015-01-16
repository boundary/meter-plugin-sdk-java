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

import java.io.IOException;
import java.util.Date;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.RuntimeMBeanException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.plugin.sdk.Collector;
import com.boundary.plugin.sdk.Measurement;
import com.boundary.plugin.sdk.MeasurementBuilder;
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
	
	/**
	 * Defines the states of our state machine
	 *
	 */
	private enum CollectorState {
		INITIALIZING,
		CONNECTING,
		CONNECTED,
		COLLECTING,
		DISCONNECTED,
		TERMINATED,
		EXIT
	};
	
	private CollectorState state;

	private JMXClient client;
	private JMXPluginConfigurationItem item;
	private MBeanMap mbeanMap;
	private MeasurementSink output;
	private String name;
	private AttributeValueExtractor valueExtractor;
	private String source;
	private MBeanServerConnection mbeanServerConnection;

	public JMXCollector(String name,
			JMXPluginConfigurationItem item,
			MBeanMap mbeanMap,
			AttributeValueExtractor valueExtractor,
			MeasurementSink output) {
		this.name = name;
		this.client = new JMXClient();
		this.item = item;
		this.mbeanMap = mbeanMap;
		this.output = output;
		this.valueExtractor = valueExtractor;
		this.state = CollectorState.INITIALIZING;
	}

	@Override
	public Measurement[] getMeasures() {
		// TODO Part of the scheme to generalize collectors
		// in the framework. Does nothing for now
		return null;
	}
	
	public void getMetricsFromAttributes(MBeanServerConnection connection,ObjectInstance instance,MBeanAttribute attr) throws InstanceNotFoundException {
		try {
			System.out.println("metric: " + attr.getMetricName());
			LOG.debug("object: {},attribute: {}, type: {}",
					instance.getObjectName(), attr.getAttribute(),
					attr.getDataType());
			Object obj = connection.getAttribute(instance.getObjectName(),attr.getAttribute());
			LOG.debug("metric: {}, object class: {}, value: {}",
					attr.getMetricName(), obj.getClass(), obj);
			Number value = valueExtractor.getValue(obj,attr);
			MeasurementBuilder builder = new MeasurementBuilder();
			builder.setName(attr.getMetricName())
			       .setSource(this.source)
				   .setValue(value)
				   .setTimestamp(new Date());
			Measurement m = builder.build();
			this.output.send(m);
			
		} catch (ReflectionException re) {
			LOG.error("Reflection exception ocurred while getting attribute {} from {}",
					attr.getAttribute(),instance.getObjectName());
		} catch (IOException io) {
			LOG.error("Reflection exception ocurred while getting attribute {} from {}",
					attr.getAttribute(),instance.getObjectName());
		} catch (AttributeNotFoundException nf) {
			LOG.warn("AttributeNotFoundException exception ocurred while getting attribute {} from {}.",
					attr.getAttribute(),instance.getObjectName());
		} catch (MBeanException m) {
			LOG.error("MBeanException exception ocurred while getting attribute {} from {}",
					attr.getAttribute(),instance.getObjectName());
		} catch (RuntimeMBeanException rt) {
			LOG.error("RuntimeMBeanException exception ocurred while getting attribute {} from {}: {}",
					attr.getAttribute(),instance.getObjectName(),rt.getMessage());
		} catch (NullPointerException np) {
			LOG.warn("Null value for attribute {} for {}",
					instance.getObjectName(), attr.getAttribute());
		} catch (UnsupportedOperationException o) {
			LOG.warn("UnsupportedOperationException while getting attribute {} for {}",
					instance.getObjectName(), attr.getAttribute());
		}
	}
	
	/**
	 * Fetches an MBean attributes and then collects metrics
	 * 
	 * @param entry {@link MBeanEntry}
	 */
	private void queryMBean(MBeanEntry entry) {
		try {
			ObjectName name = new ObjectName(entry.getMbean());
			ObjectInstance instance = this.mbeanServerConnection.getObjectInstance(name);
			for (MBeanAttribute attr : entry.getAttributes()) {
				if (attr.isEnabled()) {
					getMetricsFromAttributes(this.mbeanServerConnection,instance,attr);
				}
			}
		} catch (MalformedObjectNameException o) {
			System.out.println("NullPointerException");
		} catch (InstanceNotFoundException i) {
			System.out.println("InstanceNotFoundException");
		} catch (IOException io) {
			System.out.println("MBeanException");
		}
	}
	
	/**
	 * 
	 * @return {@link CollectorState}
	 */
	private CollectorState stateInitializing() {
		// Used the source specified in the configuration or use the
		// host name as the default
		if (item.getSource() == null) {
			this.source = PluginUtil.getHostname();
		} else {
			this.source = item.getSource();
		}
		return CollectorState.CONNECTING;
	}
	
	private CollectorState stateConnecting() {
		CollectorState nextState = CollectorState.CONNECTING;
		if (client.connect(item.getHost(),item.getPort())) {
			this.mbeanServerConnection = client.getMBeanServerConnection();
			if (this.mbeanServerConnection == null) {
				LOG.error("MBean Server Connection is null for {}",this.name);
				client.disconnect();
			}
			else {
				nextState = CollectorState.CONNECTED;
			}
		}
		else {
			LOG.error("Failed to connect to host: {}, port: ",item.getHost(),item.getPort());
		}
		return nextState;
	}
	
	private CollectorState stateConnected() {
		return CollectorState.COLLECTING;
	}
	
	private CollectorState stateCollecting() {
		CollectorState nextState = CollectorState.DISCONNECTED;
		
		// Continuously loop until the thread is interrupted 
		while (nextState != CollectorState.TERMINATED) {
			try {
				long start = new Date().getTime();
				for (MBeanEntry entry : mbeanMap.getMap()) {
					if (entry.isEnabled()) {
						queryMBean(entry);
					}
				}
				// TBD: How to handle if the sample time is longer
				// than the amount of time it takes to collect the metrics.
				long stop = new Date().getTime();
				long delta = stop - start;
				delta = delta < 0 ? 0 : delta;
				Thread.sleep(Long.parseLong(item.getPollInterval()) - delta);
			} catch (InterruptedException e) {
				LOG.warn("Processing thread {} interrupted",Thread.currentThread().getName());
				nextState = CollectorState.TERMINATED;
			}
		}
		return nextState;
	}
	
	private CollectorState stateDisconnected() {
		return CollectorState.CONNECTING;
	}
	
	private CollectorState stateTerminated() {
		return CollectorState.EXIT;
	}

	/**
	 * This is our main processing loop that is run by a separate thread.
	 * 
	 * All exceptions and retry cases need to be handled in this loop.
	 */
	@Override
	public void run() {
		
		do {
			LOG.info("Collector {} state is {}",name,state);
			switch(this.state) {
			case INITIALIZING:
				this.state = stateInitializing();
				System.out.println("INITIALIZING");
				break;
			case CONNECTING:
				this.state = stateConnecting();
				System.out.println("CONNECTING");
				break;
			case CONNECTED:
				this.state = stateConnected();
				System.out.println("CONNECTED");
				break;
			case COLLECTING:
				this.state = stateCollecting();
				System.out.println("COLLECTING");
				break;
			case DISCONNECTED:
				this.state = stateDisconnected();
				System.out.println("DISCONNECTED");
				break;
			case TERMINATED:
				this.state = stateTerminated();
				System.out.println("TERMINATED");
				break;
			case EXIT:
				System.out.println("EXIT");

				break;
			}
		} while(this.state != CollectorState.EXIT);
		
		System.out.println("complete");
	}
}
