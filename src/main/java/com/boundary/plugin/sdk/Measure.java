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
package com.boundary.plugin.sdk;

import java.util.Date;

/**
 * Encapsulates the data required for a single piece of time series data to be communicated to the Plugin manager
 * by a plugin.
 * 
 * @author davidg
 *
 */
public class Measure {
	
    private String name;
    private String value;
    private String source;
    private Date timestamp;
    
    /**
     * Default constructor
     */
	public Measure() {
    	name = "";
    	value = "";
    	source = null;
    	timestamp = null;
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
	 * @return String identifies the metric
	 */
    public String getName() {
		return name;
	}

    /**
     * Set the metric identifier of this instance.
     * 
     * @param name
     */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the value of the measure instance.
	 * 
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Set the value of this measure instance.
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 
	 * @return
	 */
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}

