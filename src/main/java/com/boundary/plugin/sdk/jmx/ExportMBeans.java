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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import com.boundary.plugin.sdk.PluginUtil;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;

/**
 * Program the extracts MBeans from a running VM and converts to JSON.
 *
 * <pre>
 * $ java com.boundary.plugin.sdk.jmx.ExportMBeans
 * usage: com.boundary.plugin.sdk.jmx.ExportMBeans [virtual machine display name | host port]
 * </pre>
 *
 */
public class ExportMBeans {

	private String vmDisplayName = null;
	private JMXClient jmxClient;
	private MBeanServerConnection connection;
	private String[] domains;
	private String host = null;
	private int port = -1;
	private boolean useAttach;
	private MBeanMap map;

	/**
	 * Constructor
	 */
	public ExportMBeans() {
		this.jmxClient = new JMXClient();
		this.map = new MBeanMap();
	}

	/**
	 * Writes a usage statement if insufficient arguments are provide
	 * on the command line.
	 */
	private void usage() {
		System.err.println("usage: " + this.getClass()
				+ " [virtual machine display name | host port]");
	}

	/**
	 * Process the arguments passed on the command line
	 * 
	 * @param args command line arguments
	 */
	private void handleArguments(String[] args) {
		if (args.length == 1) {
			this.vmDisplayName = args[0];
			this.useAttach = true;
		} else if (args.length == 2) {
			this.host = args[0];
			this.port = Integer.parseInt(args[1]);
			this.useAttach = false;
		} else {
			usage();
			System.exit(1);
		}
	}

	/**
	 * Connects to a JVM using the attach API.
	 * 
	 * @return {@link boolean} <code>true</code> if connection was successful, <code>false</code> otherwise.
	 */
	private boolean connectAttach() {
		boolean connected = false;
		if (jmxClient.connect(this.vmDisplayName)) {
			this.connection = jmxClient.getMBeanServerConnection();
			connected = true;
		} else {
			System.err.println("Unable to attach to virtual machine: "
					+ this.vmDisplayName);
		}
		return connected;
	}

	/**
	 * Connects to JVM using a hostname and port
	 * 
	 * @return {@link boolean} <code>true</code>, if connection is successful, <code>false</code> otherwise.
	 */
	private boolean connectHostPort() {
		boolean connected = false;
		if (jmxClient.connect(this.host, this.port)) {
			this.connection = jmxClient.getMBeanServerConnection();
			connected = true;
		} else {
			System.err.println("Unable to RMI to virtual machine at "
					+ this.host + ":" + this.port);
		}
		return connected;
	}

	/**
	 * Handle connecting to the JVM
	 * 
	 * @return {@link boolean} <code>true</code>, if connection is successful, <code>false</code> otherwise.
	 */
	private boolean connect() {
		boolean connected = false;
		if (this.useAttach) {
			connected = this.connectAttach();
		} else {
			connected = this.connectHostPort();
		}

		return connected;
	}

	/**
	 * Returns a list of domains within the JVM.
	 */
	private void getDomains() {
		try {
			this.domains = this.connection.getDomains();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String generateMetricName(ObjectName o,MBeanAttributeInfo info) {
		Joiner joiner = Joiner.on('.');
		String s = o.getCanonicalName().toUpperCase();
	    String [] strs1 = s.split(":");
	    String [] strs2 = strs1[1].split(",");

	    ArrayList<String> lstr = new ArrayList<String>();
	    for (String s2 : strs1[1].split(",")) {
	    	lstr.add(s2.split("=")[1]);
	    }
	    
	    String s1 = lstr.toString().replaceAll("\\[","").replaceAll("\\]","").replaceAll(",", "").replaceAll("\\s",".");

		return joiner.join("BOUNDARY","JMX",strs1[0],s1,PluginUtil.toUpperUnderscore(info.getName(),'.'));
	}
	
	/**
	 * 
	 * @param name {@link ObjectName} name of the MBean
	 * @param info {@link MBeanAttributeInfo}
	 * @return {@link String}
	 */
	private String getMetricName(ObjectName o,MBeanAttributeInfo info) {
		StringBuffer buffer = new StringBuffer();
		
		String metricName = this.generateMetricName(o,info);
		buffer.append(metricName);
		return buffer.toString();
	}

	/**
	 * 
	 * @param name
	 * @param entry
	 */
	private void getBeanAttributes(ObjectName name, MBeanEntry entry) {
		MBeanInfo info;
		HashSet<String> checkTypes = new HashSet<String>();
		checkTypes.add("long");
		checkTypes.add("int");
		checkTypes.add("javax.management.openmbean.CompositeData");
		checkTypes.add("[Ljavax.management.openmbean.CompositeData;");
		try {
			info = this.connection.getMBeanInfo(name);
			MBeanAttributeInfo[] attributes = info.getAttributes();
			ArrayList<MBeanAttributes> mbeanAttributes = new ArrayList<MBeanAttributes>();
			for (MBeanAttributeInfo attrInfo : attributes) {
				if (checkTypes.contains(attrInfo.getType())) {
				MBeanAttributes attr = new MBeanAttributes();
				attr.setAttribute(attrInfo.getName());
				attr.setDataType(attrInfo.getType());
				attr.setMetricType(MBeanAttributes.MetricType.standard);
				attr.setMetricName(this.getMetricName(name,attrInfo));
				mbeanAttributes.add(attr);
				}
			}
			entry.setAttributes(mbeanAttributes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getEntries(ArrayList<MBeanEntry> entries) {
		try {
			Set<ObjectName> mbeans = this.connection.queryNames(null, null);
			List<ObjectName> mbeanList = new ArrayList<ObjectName>(mbeans);
			Collections.sort(mbeanList);
			for (ObjectName obj : mbeans) {
				MBeanEntry entry = new MBeanEntry();
				entry.setObjectName(obj.getCanonicalName());
				getBeanAttributes(obj, entry);
				entries.add(entry);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void convertToJson() {

		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(new File("metric-map.json"), this.map);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void exportMBeans() {

		ArrayList<MBeanEntry> beanEntries = new ArrayList<MBeanEntry>();

		for (String domain : domains) {
			getEntries(beanEntries);
			this.map.setMap(beanEntries);
		}

		this.convertToJson();
	}

	private void listMBeans(String[] args) {
		handleArguments(args);
		if (this.connect()) {
			this.getDomains();
			this.exportMBeans();
		}
	}

	public static void main(String[] args) {
		ExportMBeans lmbeans = new ExportMBeans();
		lmbeans.listMBeans(args);
	}
}
