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
 * Data structure the represents a dashboard in the <code>plugin.json</code> plugin manifest
 * 
 * <pre>
 * "dashboards" : [
        { "name": "Plugin Shell", "layout": "d-w=3&d-h=2&d-pad=5&d-bg=none&d-g-BOUNDARY_PORT_AVAILABILITY=0-1-1-1&d-g-BOUNDARY_RANDOM_NUMBER=0-0-1-1&d-g-BOUNDARY_PROCESS_COUNT=1-0-1-1&d-g-BOUNDARY_FILE_SPACE_CAPACITY=2-0-1-1&d-g-BOUNDARY_PORT_RESPONSE=1-1-1-1"},
        { "name": "CPU Load", "layout":"d-w=1&d-h=3&d-pad=5&d-bg=none&d-g-BOUNDARY_CPU_LOAD_1_MINUTE=0-0-1-1&d-g-BOUNDARY_CPU_LOAD_5_MINUTE=0-1-1-1&d-g-BOUNDARY_CPU_LOAD_15_MINUTE=0-2-1-1"}
    ]
 * </pre>
 *
 */
public class Dashboard {
	String name;
	String layout;
}
