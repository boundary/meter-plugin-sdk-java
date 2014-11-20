package com.boundary.plugin.sdk;

public class PluginRunner {
	
	String className;
	
	public PluginRunner(String className) {
		this.className = className;
	}
	
	void run() {
		Plugin plugin = null;
		try {
			plugin = (Plugin) Class.forName(this.className).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		plugin.run();
	}
	
	public static void main(String[] args) {
		PluginRunner plugin = new PluginRunner(args[0]);
		plugin.run();
	}
}
