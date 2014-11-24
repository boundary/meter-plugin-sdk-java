package com.boundary.plugin.sdk;

public interface Collector extends Runnable {
	
	public Measure [] getMeasures();
	
	void run();

}
