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

    public MetricDefinition() {
        this.name = "";
        this.description = "";
        this.displayName = "";
        this.displayNameShort = "";
        this.unit = "";
        this.defaultAggregate = "";
        this.defaultResolutionMS = 1000L;
        this.isDisabled = false;
    }
}

