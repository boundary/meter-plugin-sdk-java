package com.boundary.plugin.sdk;

import java.util.List;

/**
 * Data structure the encapsulates the <code>plugin.json</code> plugin manifest.
 */
public class PluginJSON {
	
	String description;
	String icon;
	String command;

	List<String> metrics;
	List<Dashboard> dashboards;
	
	String postExtract;
	String ignore;
	
	ParamArray paramArray;
	ParamSchema paramSchema;
	
	public String getDescription() {
		return description;
	}
	public String getIcon() {
		return icon;
	}
	public String getCommand() {
		return command;
	}
	public List<String> getMetrics() {
		return metrics;
	}
	public List<Dashboard> getDashboards() {
		return dashboards;
	}
	public String getPostExtract() {
		return postExtract;
	}
	public String getIgnore() {
		return ignore;
	}
	public ParamArray getParamArray() {
		return paramArray;
	}
	public ParamSchema getParamSchema() {
		return paramSchema;
	}
	
	
}
