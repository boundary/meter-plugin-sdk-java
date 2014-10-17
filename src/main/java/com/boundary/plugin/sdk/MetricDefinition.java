package com.boundary.plugin.sdk;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MetricDefinition {
    /**
     * Name of the metric, must be globally unique if creating
     */
    @JsonProperty
    private final String name;
    /**
     * Description of the metric (optional if updating)
     */
    private final String description;
    /**
     * Short name to use when referring to the metric (optional if updating)
     */
    @JsonProperty
    private final String displayName;
    /**
     * Terse short name when referring to the metric and space is limited, less than 15 characters preferred. (optional if updating)
     */
    @JsonProperty
    private final String displayNameShort;
    /**
     * The units of measurement for the metric, can be percent, number, bytecount, or duration (optional if updating)
     */
    @JsonProperty
    private final String unit;
    /**
     * When graphing (or grouping at the 1 second interval) the aggregate function that makes most sense for this metric.
     * Can be sum, avg, max, or min. (optional if updating)
     */
    @JsonProperty
    private final String defaultAggregate;
    /** 
     * Expected polling time of data in milliseconds. Used to improve rendering of graphs for non-one-second polled metrics. (optional if updating)
     */
    @JsonProperty
    private final long defaultResolutionMS;
    /**
     * Is this metric disabled (optional if updating)
     */
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

