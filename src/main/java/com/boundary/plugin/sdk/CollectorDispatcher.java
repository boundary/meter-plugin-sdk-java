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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Handles the running of collectors.
 *
 */
public class CollectorDispatcher {
	
	List<Collector> collectorList;
	
	/**
	 * Default constructor
	 */
	public CollectorDispatcher() {
		collectorList = new ArrayList<Collector>();
	}
	
	/**
	 * Adds a collector so that it can be dispatched.
	 * 
	 * @param collector {@link Collector}
	 */
	public void addCollector(Collector collector) {
		collectorList.add(collector);
	}
	
	/**
	 * Runs the collectors in separate threads
	 */
	public void run() {
		Executor executor = Executors.newFixedThreadPool(collectorList.size()) ; 
		for (Collector collector : collectorList ) {
			executor.execute(collector);
		}
	}
}
