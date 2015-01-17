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

package com.boundary.plugin.sdk.jmx.extractor;

import javax.management.openmbean.CompositeDataSupport;

import com.boundary.plugin.sdk.jmx.MBeanAttribute;

/**
 * Translates a {@link CompositeDataSupport} instance to a {@link Number}
 */
public class CompositeDataToNumber implements AttributeExtractor {

	@Override
	public Number getValue(Object object, MBeanAttribute attribute) {
		Number value;
        CompositeDataSupport cData = (CompositeDataSupport)object;
        value = (Number)cData.get(attribute.getKey());
		return value;
	}

}
