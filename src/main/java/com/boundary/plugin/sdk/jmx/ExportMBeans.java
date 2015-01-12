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

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

import com.boundary.plugin.sdk.MetricDefinitionList;

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
	private String host = null;
	private int port = -1;
	
	private static final String COMMAND_NAME="ExportMBeans.command.name";
	private String commandName;
	private String prefix;
	
	private Options options = new Options();
	private Option exportOption;
	private Option helpOption;
	private Option hostOption;
	private Option mergeFileOption;
	private Option outputFileOption;
	private Option portOption;
	private Option prefixOption;

	private CommandLine cmd;
	
	private enum ExportType {MBEANS,METRICS,PLUGINS};
	private ExportType exportType;

	/**
	 * Constructor
	 */
	private ExportMBeans() {
		options = new Options();
		this.jmxClient = new JMXClient();
		commandName = System.getProperty(COMMAND_NAME,this.getClass().toString());
		exportType = ExportType.MBEANS;
		prefix = "";
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
			connected = true;
		} else {
			System.err.println("Unable to RMI to virtual machine at "
					+ this.host + ":" + this.port);
			System.exit(1);
		}
		return connected;
	}
	
	private void exportMBEANS() {
		MBeanTransform<MBeanMap> transform = new MBeanMapTransform();
		MBeansTransformer<MBeanMap> transformer = new MBeansTransformer<MBeanMap>(jmxClient,transform,this.prefix);
		transformer.transform();
		transformer.convertToJson();
	}
	
	private void exportMETRICS() {
		MBeanTransform<MetricDefinitionList> transform = new MetricDefinitionTransform();
		MBeansTransformer<MetricDefinitionList> transformer = new MBeansTransformer<MetricDefinitionList>(jmxClient,transform,this.prefix);
		transformer.transform();
		transformer.convertToJson();
	}
	
	private void exportPLUGINS() {
		MBeanTransform<PluginMetricTransform> transform = new PluginMetricTransform();
		MBeansTransformer<PluginMetricTransform> transformer = new MBeansTransformer<PluginMetricTransform>(jmxClient,transform,this.prefix);
		transformer.transform();
		transformer.convertToJson();
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
		exportOption = OptionBuilder
				.withArgName("type")
				.hasArgs(1)
				.withDescription("Selects what to export which is either: mbeans, metrics, or plugins." +
				                 " defaults to mbeans")
				.withLongOpt("export")
				.create("e");
		mergeFileOption = OptionBuilder
				.withArgName("metric-definitions.json")
				.hasArgs(1)
				.withDescription("Metrics definition file to merge into exported metric definitions." +
				                 "Used to override previously specified values. Uses the metric identifer" +
						         " as the key.")
			    .withLongOpt("merge-file")
			    .create("m");
		outputFileOption = OptionBuilder
				.withArgName("path")
				.hasArgs(1)
				.withDescription("Path to output the exported json file")
				.create("f");
		prefixOption = OptionBuilder
				.withArgName("prefix")
				.hasArgs(1)
				.withDescription("Prefix to add to metric display names. Defaults to empty")
				.create("x");

		options.addOption(helpOption);
		options.addOption(hostOption);
		options.addOption(portOption);
		options.addOption(exportOption);
		options.addOption(mergeFileOption);
		options.addOption(outputFileOption);
		options.addOption(prefixOption);

		try {
			CommandLineParser parser = new BasicParser();
			cmd = parser.parse(options,args);

			// If the help argument is present then display usage
			if (cmd.hasOption("?") == true) {
				usage();
			}
			
			// Set the prefix default to empty if not specified.
			this.prefix = cmd.getOptionValue("x");
			if (cmd.getOptionValue("e") != null) {
				// Determine what is to be exported
				switch(cmd.getOptionValue("e")) {
				case "mbeans":
					exportType = ExportType.MBEANS;
					break;
				case "metrics":
					exportType = ExportType.METRICS;
					break;
				case "plugins":
					exportType = ExportType.PLUGINS;
					break;
				default:
					usage();
				}
			}
		} catch (Exception e) {
			usage();
		}
		
	}
	private void execute(String[] args) {

		parseCommandLineOptions(args);
	
		if (this.connect()) {
			
			switch(exportType) {
			case MBEANS:
				exportMBEANS();
				break;
			case METRICS:
				exportMETRICS();
				break;
			case PLUGINS:
				exportPLUGINS();
				break;
			default:
				assert false: "Undefined export type";
			}
			this.jmxClient.disconnect();
		}
		else {
			System.err.printf("Failed to connect to Virtual Machine: host => %s, port %d\n",
					this.host,this.port);
		}
	}

	public static void main(String[] args) {
		ExportMBeans lmbeans = new ExportMBeans();
		lmbeans.execute(args);
	}
}
