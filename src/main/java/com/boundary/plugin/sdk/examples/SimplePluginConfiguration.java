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

package com.boundary.plugin.sdk.examples;

import com.boundary.plugin.sdk.PluginConfiguration;
import java.util.ArrayList;

public class SimplePluginConfiguration implements PluginConfiguration {

	private ArrayList<SimplePluginConfigurationItem> items;
	private int pollInterval;

	public SimplePluginConfiguration() {

	}

	public ArrayList<SimplePluginConfigurationItem> getItems() {
		return items;
	}

	public void setItems(ArrayList<SimplePluginConfigurationItem> items) {
		this.items = items;
	}

	public int getPollInterval() {
		return pollInterval;
	}

	public void setPollInterval(int pollInterval) {
		this.pollInterval = pollInterval;
	}

}
