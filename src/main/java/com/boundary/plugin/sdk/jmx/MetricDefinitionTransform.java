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

import static com.boundary.plugin.sdk.PluginUtil.camelCaseToSpaceSeparated;

import java.util.Hashtable;

import javax.management.MBeanAttributeInfo;
import javax.management.ObjectName;

import com.boundary.plugin.sdk.MetricAggregate;
import com.boundary.plugin.sdk.MetricDefinitionBuilder;
import com.boundary.plugin.sdk.MetricDefinitionList;
import com.boundary.plugin.sdk.MetricUnit;

public class MetricDefinitionTransform extends MBeanTransformBase<MetricDefinitionList> {
	
	private MetricDefinitionList metricList;
	private final static MetricAggregate DEFAULT_AGGREGATE=MetricAggregate.avg;
	private final static long DEFAULT_RESOLUTION = 1000L;
	private final static MetricUnit DEFAULT_UNIT = MetricUnit.number;
	private final static String DEFAULT_DISPLAY_NAME_SHORT = "";
	
	public MetricDefinitionTransform() {
		metricList = new MetricDefinitionList();
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endMBean() {
		// TODO Auto-generated method stub

	}


	@Override
	public void beginAttribute(ObjectName name, MBeanAttributeInfo info) {
		MetricDefinitionBuilder builder = new MetricDefinitionBuilder();
		Hashtable<String,String> keys = new Hashtable<String, String>(name.getKeyPropertyList());
		
		builder.setDefaultAggregate(DEFAULT_AGGREGATE);
		builder.setDefaultResolutionMS(DEFAULT_RESOLUTION);
		builder.setDescription(camelCaseToSpaceSeparated(info.getDescription()));
		
		StringBuilder nameBuilder = new StringBuilder();
		if (this.prefix != null) {
			nameBuilder.append(this.prefix);
			nameBuilder.append(' ');
		}
		// Prefix with type and then name and remove from
		// the hash table if they exist, so that names go
		// from the general to specific
		String attrType = keys.get("type");
		String attrName = keys.get("name");
		if (attrType != null) {
			nameBuilder.append(attrType);
		}
		if (attrName != null) {
			nameBuilder.append(attrName);
		}
		nameBuilder.append(info.getName());
		String displayName = nameBuilder.toString();
		builder.setDisplayName(camelCaseToSpaceSeparated(displayName));
		builder.setDisplayNameShort(displayName.length() <= 20 ? displayName : DEFAULT_DISPLAY_NAME_SHORT);
		builder.setName(this.getMetricName(name,info));
		builder.setUnit(DEFAULT_UNIT);
		
		metricList.getResult().add(builder.build());                                
	}

	@Override
	public void endAttribute() {
		
	}

	@Override
	public MetricDefinitionList getExport() {
		return metricList;
	}
}
