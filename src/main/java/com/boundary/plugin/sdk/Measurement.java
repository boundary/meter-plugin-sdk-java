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
import static com.google.common.base.Preconditions.*;

/**
 * Encapsulates the data required for a single piece of time series data to be communicated to the Plugin manager
 * by a plugin.
 *
 */
public class Measurement {
	
    private String name;
    private Number value;
    private String source;
    private Date timestamp;
    
    /**
     * Default constructor
     */
	public Measurement() {
    	name = "";
    	value = 0;
    	source = "";
    	setTimestamp(new Date());
    }
	
	/**
	 * Constructs and instance from specified fields
	 * 
	 * @param name metric identifier
	 * @param value metric value
	 */
	public Measurement(String name,Number value) {
		this(name,value,"",new Date());
	}
	
	/**
	 * Constructs an instance from specified fields
	 * @param name metric identifier
	 * @param value metric value
	 * @param source metric source
	 */
	public Measurement(String name,Number value,String source) {
		this(name,value,source,new Date());
	}
	
	/**
	 * Constructs an instance from specified fields
	 * 
	 * @param name metric identifier
	 * @param value metric value
	 * @param source metric source
	 * @param timestamp metric timestamp
	 */
	public Measurement(String name,Number value,String source,Date timestamp) {
		checkNotNull(name);
		checkNotNull(value);
		checkNotNull(source);
		checkNotNull(timestamp);
		this.name = name;
		this.value = value;
		this.source = source;
		this.setTimestamp(timestamp);
	}
	
	/**
	 * Converts the instance to an output string suitable for sending to the meter plugin manager.
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(name);
		buf.append(" ");
		buf.append(value);
		if (source != null) {
			buf.append(" ");
			buf.append(source);
			if (timestamp != null) {
				buf.append(" ");
				buf.append(Long.toString(timestamp.getTime()));
			}
		}
		
		return buf.toString();
	}

	/**
	 * Returns the metric identifier that is associated with this instance.
	 * 
	 * @return {@link String} metric identifier
	 */
    public String getName() {
		return name;
	}

	/**
	 * Returns the value of the measure instance.
	 * 
	 * @return {@link Number}
	 */
	public Number getValue() {
		return value;
	}

	/**
	 * Set the value of this measure instance.
	 * 
	 * @param value {@link Number}
	 */
	public void setValue(Number value) {
		this.value = value;
	}

	/**
	 * Returns the metric source for the measurement
	 * 
	 * @return {@link String}
	 */
	public String getSource() {
		return source;
	}

	/**
	 * Returns the timestamp when the measurement value was collected.
	 * 
	 * @return {@link Date}
	 */
	public Date getTimestamp() {
		return (Date)timestamp.clone();
	}

	/**
	 * Private method that handles copying to ensure that
	 * the {@Measure} instances cannot be mutated externally
	 * 
	 * @param timestamp {@link Date}
	 */
	private void setTimestamp(Date timestamp) {
		this.timestamp = (Date)timestamp.clone();
	}
}

