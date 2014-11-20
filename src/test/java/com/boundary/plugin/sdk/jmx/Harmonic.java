/**
 * 
 */
package com.boundary.plugin.sdk.jmx;

import java.util.Date;

/**
 * @author davidg
 *
 */
public class Harmonic implements HarmonicMBean {
	
	private double amplitude;
	private double frequency;
	private double phase;

	/**
	 * 
	 */
	public Harmonic() {
		this.amplitude = 1.0;
		this.frequency = 60.0;
		this.phase = 0.0;
	}

	/* (non-Javadoc)
	 * @see com.boundary.plugin.sdk.jmx.HarmonicMBean#setAmplitude(double)
	 */
	@Override
	public void setAmplitude(double amplitude) {
		this.amplitude = amplitude;
	}

	/* (non-Javadoc)
	 * @see com.boundary.plugin.sdk.jmx.HarmonicMBean#getAmplitude()
	 */
	@Override
	public double getAmplitude() {
		return this.amplitude;
	}

	/* (non-Javadoc)
	 * @see com.boundary.plugin.sdk.jmx.HarmonicMBean#setFrequency(double)
	 */
	@Override
	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}

	/* (non-Javadoc)
	 * @see com.boundary.plugin.sdk.jmx.HarmonicMBean#getFrequency()
	 */
	@Override
	public double getFrequency() {
		return this.frequency;
	}

	/* (non-Javadoc)
	 * @see com.boundary.plugin.sdk.jmx.HarmonicMBean#setPhase(double)
	 */
	@Override
	public void setPhase(double phase) {
		this.phase = phase;
	}

	/* (non-Javadoc)
	 * @see com.boundary.plugin.sdk.jmx.HarmonicMBean#getPhase()
	 */
	@Override
	public double getPhase() {
		return this.phase;
	}

	/* (non-Javadoc)
	 * @see com.boundary.plugin.sdk.jmx.HarmonicMBean#evaluate()
	 */
	@Override
	public double evaluate() {
		double t = new Date().getTime();
		return this.amplitude * Math.cos((this.frequency * t) + phase);
	}

}
