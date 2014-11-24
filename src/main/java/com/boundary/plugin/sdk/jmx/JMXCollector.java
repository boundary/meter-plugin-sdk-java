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

package com.boundary.plugin.sdk.jmx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.plugin.sdk.Collector;
import com.boundary.plugin.sdk.Measure;

/**
 * Handles the collection of metrics from a JMX connection.
 * 
 * <ol>
 * <li>JMX Client connection and authentication information</li>
 * <li>MBean collection information</li>
 * </ol>
 * 
 * Reponsabilities
 * <ol>
 * <li>Establish connection to the java virtual machine.</li>
 * <li>Handle scheduling of sampling of the MBean attributes</li>
 * </ol>
 * @author davidg
 *
 */
public class JMXCollector implements Collector {
	
	private static Logger LOG = LoggerFactory.getLogger(JMXCollector.class);

	private JMXClient client;

	public JMXCollector() {
		this.client = new JMXClient();
	}

	@Override
	public Measure[] getMeasures() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}
