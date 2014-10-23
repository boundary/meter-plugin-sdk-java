package com.boundary.plugin.sdk;

import java.util.ArrayList;


public class PluginConfiguration {
	
	private String description;
	private String icon;
	private String command;
	private ArrayList<String> metrics;

	private ArrayList<Dashboard> dashboards;
	
}
/*
	{
	    "description": "Provides a generic means to generate metrics via a command, or other scripting language",
	    "icon" : "icon.png",
	    "command": "python index.py",
	    "metrics" : [
	                 "BOUNDARY_CPU_LOAD_1_MINUTE",
	                 "BOUNDARY_CPU_LOAD_5_MINUTE",
	                 "BOUNDARY_CPU_LOAD_15_MINUTE",
	                 "BOUNDARY_RANDOM_NUMBER",
	                 "BOUNDARY_FILE_SPACE_CAPACITY",
	                 "BOUNDARY_PROCESS_COUNT",
	                 "BOUNDARY_PORT_AVAILABILITY",
	                 "BOUNDARY_PORT_RESPONSE"],
	    "dashboards" : [
	        { "name": "Plugin Shell", "layout": "d-w=3&d-h=2&d-pad=5&d-bg=none&d-g-BOUNDARY_PORT_AVAILABILITY=0-1-1-1&d-g-BOUNDARY_RANDOM_NUMBER=0-0-1-1&d-g-BOUNDARY_PROCESS_COUNT=1-0-1-1&d-g-BOUNDARY_FILE_SPACE_CAPACITY=2-0-1-1&d-g-BOUNDARY_PORT_RESPONSE=1-1-1-1"},
	        { "name": "CPU Load", "layout":"d-w=1&d-h=3&d-pad=5&d-bg=none&d-g-BOUNDARY_CPU_LOAD_1_MINUTE=0-0-1-1&d-g-BOUNDARY_CPU_LOAD_5_MINUTE=0-1-1-1&d-g-BOUNDARY_CPU_LOAD_15_MINUTE=0-2-1-1"}
	    ],
	    "postExtract" : "python postinstall.py",
	    "paramArray": { "itemTitle": [ "name" ], "schemaTitle": "Metric Command" },
	    "paramSchema": [
	        {
	            "title": "Name",
	            "name": "name",
	            "description": "Name of this metric configuration",
	            "type": "string",
	            "default": "",
	            "required": true
	        },
	        {
	            "title": "Poll Time (sec)",
	            "name": "pollInterval",
	            "description": "The Poll Interval to call the command. Defaults 5 seconds",
	            "type": "string",
	            "default": 5,
	            "required": false
	        },
	        {
	            "title": "Debug",
	            "name": "debug",
	            "description": "Turns on the debugging of the execution of the command",
	            "type": "boolean",
	            "default": false,
	            "required": false
		},
	        {
	            "title": "Command",
	            "name": "command",
	            "description": "Command or script to call to provide the metric",
	            "type": "string",
	            "default": "",
	            "required": false
	        }
	    ]
	}

}
*/
