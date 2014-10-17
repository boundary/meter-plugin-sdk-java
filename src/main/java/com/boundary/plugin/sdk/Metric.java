package com.boundary.plugin.sdk;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Metric {

    @JsonProperty
    private final String name;

    @JsonProperty
    private final String displayName;

    public Metric(@JsonProperty("name") String name, @JsonProperty("displayName") String displayName) {
      this.name = name;
      this.displayName = displayName;
    }

    public Metric(String displayName) {
        this(displayName, displayName);
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }
}

