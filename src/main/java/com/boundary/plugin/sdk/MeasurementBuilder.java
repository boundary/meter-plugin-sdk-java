// Copyright 2014-2015 Boundary, Inc.
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

package com.boundary.plugin.sdk;

import java.util.Date;

/**
 * Because {@link Measurement} contains no mutators we provide this class to allow creation
 * of {@link Measurement} with this help class
 * 
 */
public class MeasurementBuilder {
	
    private String name;
    private Number value;
    private String source;
    private Date timestamp;
    
    public MeasurementBuilder() {
    	reset();
    }

	public MeasurementBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public MeasurementBuilder setValue(Number value) {
		this.value = value;
		return this;
	}

	public MeasurementBuilder setSource(String source) {
		this.source = source;
		return this;
	}

	public MeasurementBuilder setTimestamp(Date timestamp) {
		this.timestamp = (Date)timestamp.clone();
		return this;
	}
	
	public void reset() {
	    this.name = "";
	    this.value = 0;
	    this.source = "";
	    this.timestamp = null;
	}
  
	public Measurement build() {
		Measurement measurement = new Measurement(this.name,this.value,this.source,this.timestamp);
		return measurement;
	}
}
