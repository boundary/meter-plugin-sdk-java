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

/**
 * Interface for java metric plugins.
 *
 */
public interface Plugin <T> {
	
	public void setMeasureOutput(MeasurementSink output);

    public void setEventOutput(EventSink output);
	
	/**
	 * Provides a configuration instance for the plugin
	 * @param configuration Plugin specific configuration
	 */
	public void setConfiguration(T configuration);
	
	/**
	 * Loads the required configuration for plugin
	 */
	public void loadConfiguration();
	
	/**
	 * Sets the dispatcher associated with the plugin
	 * 
	 * @param dispatcher {@link CollectorDispatcher}
	 */
	public void setDispatcher(CollectorDispatcher dispatcher);

	/**
	 * Called when the plugin is to start running.
	 */
	public void run();
}
