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
import java.util.Formatter;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Export the MBeans from a running JVM process into a JSON file
 * 
 * Java Virtual Machine to export the MBeans from must be configured
 * to communicate over JMX using RMI. The following system properties 
 * configure the JVM to allow connecting without a password.
 * 
 * <pre>
 * -Dcom.sun.management.jmxremote.port=PORT
 * -Dcom.sun.management.jmxremote.authenticate=false
 * -Dcom.sun.management.jmxremote.ssl=false
 * </pre>
 *
 */
public class ExportMBeans {

	private JMXClient jmxClient;
	private MBeanServerConnection connection;
	private String[] domains;
	private String host = null;
	private int port = -1;
	private MBeanMap map;
	
	private static final String COMMAND_NAME="ExportMBeans.command.name";
	private final String METRIC_NAME_SEPARATOR=".";
	private String commandName;
	
	private Options options = new Options();
	private Option helpOption;
	private Option hostOption;
	private Option portOption;
	private CommandLine cmd;

	public ExportMBeans() {
		options = new Options();
		this.jmxClient = new JMXClient();
		this.map = new MBeanMap();
		commandName = System.getProperty(COMMAND_NAME,this.getClass().toString());
		System.out.println();
	}

	/**
	 * Outputs help for the command and its options.
	 */
	private void usage() {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(this.commandName, this.options);
		System.exit(0);
	}

	/**
	 * Connects to java virtual machine using RMI
	 * 
	 * @return {@link boolean} returns <code>true</code> if connection successful.
	 */
	private boolean connect() {
		boolean connected = false;
		String host = cmd.getOptionValue("h");
		int port = Integer.parseInt(cmd.getOptionValue("p"));
		if (jmxClient.connect(host,port)) {
			this.connection = jmxClient.getMBeanServerConnection();
			connected = true;
		} else {
			System.err.println("Unable to RMI to virtual machine at "
					+ this.host + ":" + this.port);
		}
		return connected;
	}

	private void getDomains() {
		try {
			this.domains = this.connection.getDomains();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getMetricName(ObjectName name,MBeanAttributeInfo info) {
		StringBuilder builder = new StringBuilder();
		StringBuilder nameBuilder = new StringBuilder();
		Hashtable<String, String> keys = name.getKeyPropertyList();
		for (String s : keys.values()) {
			nameBuilder.append(METRIC_NAME_SEPARATOR);
			nameBuilder.append(s);
		}

		builder.append(String.format("%s%s_%s",
				name.getDomain().toUpperCase(),
				nameBuilder.toString().toUpperCase(),
				info.getName().toUpperCase()));
		return builder.toString();
	}

	private void getBeanAttributes(ObjectName name, MBeanEntry entry) {
		MBeanInfo info;
		try {
			info = this.connection.getMBeanInfo(name);
			MBeanAttributeInfo[] attributes = info.getAttributes();
			ArrayList<MBeanAttributes> mbeanAttributes = new ArrayList<MBeanAttributes>();
			for (MBeanAttributeInfo attrInfo : attributes) {
				MBeanAttributes attr = new MBeanAttributes();
				String attrName = attrInfo.getName() + ":" + attrInfo.getType() + ":"
						+ attrInfo.getDescription();
				attr.setAttribute(attrInfo.getName());
				attr.setMetricType(MBeanAttributes.MetricType.standard);
				attr.setMetricName(this.getMetricName(name,attrInfo));
				mbeanAttributes.add(attr);
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
			//mapper.writeValue(new File("metric-map.json"),this.map);
			mapper.writeValue(System.out,this.map);
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
	
	@SuppressWarnings("static-access")
	private void parseCommandLineOptions(String[] args) {
		helpOption = OptionBuilder
				.withDescription("Display help information")
				.withLongOpt("help")
				.create("?");
		hostOption = OptionBuilder
				.withArgName("host")
				.hasArgs(1)
				.isRequired()
				.withDescription("JMX host")
				.withLongOpt("host")
				.create("h");
		portOption = OptionBuilder
				.withArgName("port")
				.hasArgs(1)
				.isRequired()
				.withDescription("JMX port")
				.withLongOpt("port")
				.create("p");

		options.addOption(helpOption);
		options.addOption(hostOption);
		options.addOption(portOption);
		
		try {
			CommandLineParser parser = new BasicParser();
			cmd = parser.parse(options,args);

			if (cmd.hasOption("?") == true) {
				usage();
			}
		} catch (Exception e) {
			usage();
		}
		
	}
	private void listMBeans(String[] args) {

		parseCommandLineOptions(args);

		if (this.connect()) {
			this.getDomains();
			this.exportMBeans();
			this.jmxClient.disconnect();
		}
	}

	public static void main(String[] args) {
		ExportMBeans lmbeans = new ExportMBeans();
		lmbeans.listMBeans(args);
	}
}
