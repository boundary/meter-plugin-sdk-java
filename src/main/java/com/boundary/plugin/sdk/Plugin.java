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

/**
 * Interface for java metric plugins.
 *
 */
public interface Plugin <C> {
	
	/**
	 * Provides a configuration instance for the plugin
	 * @param configuration Plugin specific configuration
	 */
	public void setConfiguration(PluginConfiguration<C> configuration);
	
	/**
	 * Sets the dispatcher associated with the plugin
	 * @param dispatcher
	 */
	public void setDispatcher(PluginDispatcher dispatcher);

	/**
	 * Called when the plugin is to start running.
	 */
	public void run();
}
