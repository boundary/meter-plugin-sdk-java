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

package com.boundary.plugin.sdk.jmx;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.boundary.plugin.sdk.jmx.MBeanAttribute.MetricType;

public class MBeanMapTest {

	@Test
	public void testMap() {
		MBeanAttribute attr = new MBeanAttribute();
		attr.setAttribute("gree");
		attr.setDataType("long");
		attr.setMetricType(MetricType.standard);
		attr.setScale(1.0);
		attr.setEnabled(false);
		
		List<MBeanAttribute> attributes = new ArrayList<MBeanAttribute>();
		attributes.add(attr);
		
		MBeanEntry entry = new MBeanEntry();
		entry.setEnabled(true);
		entry.setMbean("Foobar");
		entry.setAttributes(attributes);
		
		ArrayList<MBeanEntry> entries = new ArrayList<MBeanEntry>();
		entries.add(entry);
		
		MBeanMap map = new MBeanMap();
		map.setMap(entries);
		ArrayList<MBeanEntry> newEntries = map.getMap();
		MBeanEntry newEntry = newEntries.get(0);
		List<MBeanAttribute> newAttributes = newEntry.getAttributes();
		assertTrue("check attributes",attributes.containsAll(newAttributes));
	}
	
	@Test
	public void testEntries() {
		
	}
}
