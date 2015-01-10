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


import java.util.ArrayList;

import javax.management.MBeanAttributeInfo;
import javax.management.ObjectName;

public class MBeanMapTransform extends MBeanTransformBase<MBeanMap> {
	
	private MBeanMap map;
	private ArrayList<MBeanEntry> entries;
	private MBeanEntry entry;
	private ArrayList<MBeanAttributes> mbeanAttributes;
	
	public MBeanMapTransform() {
		this.map = new MBeanMap();
		entries = new ArrayList<MBeanEntry>();

	}

	@Override
	public void beginDomain(String domain) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endDomain() {
		// TODO Auto-generated method stub

	}

	@Override
	public void beginMBean(ObjectName name) {
		this.entry = new MBeanEntry();
		this.entry.setObjectName(name.getCanonicalName());
		entries.add(entry);
		mbeanAttributes = new ArrayList<MBeanAttributes>();

	}

	@Override
	public void endMBean() {
		entry.setAttributes(mbeanAttributes);
	}

	@Override
	public void beginAttribute(ObjectName name,MBeanAttributeInfo info) {
		MBeanAttributes attr = new MBeanAttributes();
		attr.setAttribute(info.getName());
		attr.setDataType(info.getType());
		attr.setMetricType(MBeanAttributes.MetricType.standard);
		attr.setMetricName(this.getMetricName(name,info));
		mbeanAttributes.add(attr);
	}
	@Override
	public void endAttribute() {
		
	}

	@Override
	public MBeanMap getExport() {
		this.map.setMap(entries);
		return map;
	}
}
