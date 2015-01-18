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

import java.util.Random;

import com.boundary.plugin.sdk.Collector;
import com.boundary.plugin.sdk.Measurement;
import com.boundary.plugin.sdk.MeasurementSink;
import com.boundary.plugin.sdk.MeasurementSinkFactory;
import com.boundary.plugin.sdk.MeasurementSinkFactory.Type;

public class SimpleCollector implements Collector {

	private String name;
	
	public SimpleCollector(String name) {
		this.name = name;
	}

	@Override
	public Measurement[] getMeasures() {
		// TODO Auto-generated method stub
		return null;
	}
	
	String getName() {
		return this.name;
	}

	@Override
	public void run() {
		MeasurementSink output = MeasurementSinkFactory.getInstance(Type.STDOUT);
		Random rand = new Random();
		
		while(true) {
			try {
				Number  n = rand.nextInt(50) + 1;
				Measurement m = new Measurement("SIMPLE_METRIC",n);
				
				output.send(m);
				
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
