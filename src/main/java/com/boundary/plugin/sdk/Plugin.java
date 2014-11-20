package com.boundary.plugin.sdk;

public interface Plugin {
	
	public void setConfiguration(PluginConfiguration<?> configuration);
	public void setDispatcher(PluginDispatcher dispatcher);

	public void run();
}
