package com.boundary.plugin.sdk.jmx;

public interface HarmonicMBean {
	
	public void setAmplitude(double amplitude);
	public double getAmplitude();
	
	public void setFrequency(double frequency);
	public double getFrequency();
	
	public void setPhase(double phase);
	public double getPhase();
	
	public double evaluate();
}
