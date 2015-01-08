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

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Max;

public class MetricDefinition {
    /**
     * Name of the metric, must be globally unique if creating
     */
    @JsonProperty
    private String name;
    /**
     * Description of the metric (optional if updating)
     */
    @JsonProperty
    private String description;
    /**
     * Short name to use when referring to the metric (optional if updating)
     */
    @JsonProperty
    private String displayName;
    /**
     * Terse short name when referring to the metric and space is limited, less than 15 characters preferred. (optional if updating)
     */
    @Max(20)
    @JsonProperty
    private String displayNameShort;
    /**
     * The units of measurement for the metric, can be percent, number, bytecount, or duration (optional if updating)
     */
    @JsonProperty
    private String unit;
    /**
     * When graphing (or grouping at the 1 second interval) the aggregate function that makes most sense for this metric.
     * Can be sum, avg, max, or min. (optional if updating)
     */
    @JsonProperty
    private String defaultAggregate;
    /** 
     * Expected polling time of data in milliseconds. Used to improve rendering of graphs for non-one-second polled metrics. (optional if updating)
     */
    @JsonProperty
    private long defaultResolutionMS;
    /**
     * Is this metric disabled (optional if updating)
     */
    @JsonProperty
    private boolean isDisabled;

    /**
     * Default constructor
     */
    public MetricDefinition() {
        this.name = "";
        this.description = "";
        this.displayName = "";
        this.displayNameShort = "";
        this.unit = "number";
        this.defaultAggregate = "avg";
        this.defaultResolutionMS = 1000L;
        this.isDisabled = false;
    }
    
    /**
     * Creates a metric definition from the provided values
     * 
     * @param name metric identifier
     */
    public MetricDefinition(String name) {
    	this.name = name;
    	this.description = name;
    	this.displayName = name;
    	this.displayNameShort = name;
        this.unit = "number";
        this.defaultAggregate = "avg";
        this.defaultResolutionMS = 1000L;
        this.isDisabled = false;
    }

    /**
     * Returns the metric identifier
     * 
     * @return {@link String}
     */
	public String getName() {
		return name;
	}

	/**
	 * Sets the metric identifier
	 * 
	 * @param name metric identifier
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the description of the metric
	 * 
	 * @return {@link String}
	 */
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayNameShort() {
		return displayNameShort;
	}

	public void setDisplayNameShort(String displayNameShort) {
		this.displayNameShort = displayNameShort;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getDefaultAggregate() {
		return defaultAggregate;
	}

	public void setDefaultAggregate(String defaultAggregate) {
		this.defaultAggregate = defaultAggregate;
	}

	public long getDefaultResolutionMS() {
		return defaultResolutionMS;
	}

	public void setDefaultResolutionMS(long defaultResolutionMS) {
		this.defaultResolutionMS = defaultResolutionMS;
	}

	public boolean isDisabled() {
		return isDisabled;
	}

	public void setDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
	}
}

