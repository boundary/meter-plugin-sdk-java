package com.boundary.plugin.sdk;

public interface Plugin {
	
	public void setConfiguration(PluginConfiguration configuration);
	public void setScheduler(PluginScheduler scheduler);

	public void run();
}
