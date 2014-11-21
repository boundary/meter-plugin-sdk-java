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
	public double getValue() {
		double t = new Date().getTime();
		return this.amplitude * Math.cos((this.frequency * t) + phase);
	}
}
