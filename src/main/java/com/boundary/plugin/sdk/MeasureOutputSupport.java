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
 * Factory for {@link MeasurementSink} types.
 */
public class MeasureOutputSupport {
	
	private static MeasureOutputStandardOut measureStandardOut = new MeasureOutputStandardOut();
	private static MeasureOutputAPI measureAPI = new MeasureOutputAPI();
	
	public enum Type {
		STDOUT,
		API
	};
	
	/**
	 * Factory method to get a {@link MeasurementSink}
	 * @param type {@link enum Output}
	 * @return {@link MeasurementSink}
	 */
	public static MeasurementSink getInstance(Type type) {
		MeasurementSink instance = null;
		switch(type) {
			case STDOUT:
			case API:
				instance = measureStandardOut;
				break;
			default:
				instance = measureAPI;
		}
		return instance;
	}
}
