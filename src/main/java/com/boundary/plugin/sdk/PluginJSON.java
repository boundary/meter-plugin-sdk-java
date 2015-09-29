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

import java.util.List;

/**
 * Data structure the encapsulates the <code>plugin.json</code> plugin manifest.
 */
public class PluginJSON {
    String name;
    String version;
    String meterVersionRequired;
    List<String> unsupportedPlatforms;
    List<String> tags;
	String description;
	String icon;
	String command;

	List<String> metrics;
	List<Dashboard> dashboards;
	
	String postExtract;
	String ignore;
	
	ParamArray paramArray;
	ParamSchema paramSchema;

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getMeterVersionRequired() {
        return meterVersionRequired;
    }

    public List<String> getUnsupportedPlatforms() {
        return unsupportedPlatforms;
    }

    public List<String> getTags() {
        return tags;
    }

	public String getDescription() {
		return description;
	}
	public String getIcon() {
		return icon;
	}
	public String getCommand() {
		return command;
	}
	public List<String> getMetrics() {
		return metrics;
	}
	public List<Dashboard> getDashboards() {
		return dashboards;
	}
	public String getPostExtract() {
		return postExtract;
	}
	public String getIgnore() {
		return ignore;
	}
	public ParamArray getParamArray() {
		return paramArray;
	}
	public ParamSchema getParamSchema() {
		return paramSchema;
	}
	
	
}
