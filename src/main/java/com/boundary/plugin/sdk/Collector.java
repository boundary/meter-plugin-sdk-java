package com.boundary.plugin.sdk;

public interface Collector extends Runnable {
	
	public Measurement [] getMeasures();
	
	void run();

}
