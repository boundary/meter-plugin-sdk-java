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

import javax.management.MBeanAttributeInfo;
import javax.management.ObjectName;

import com.boundary.plugin.sdk.MetricAggregate;
import com.boundary.plugin.sdk.MetricDefinitionBuilder;
import com.boundary.plugin.sdk.MetricDefinitionList;
import com.boundary.plugin.sdk.MetricUnit;

public class MetricDefinitionTransform extends MBeanTransformBase {
	

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
		
		builder.setDefaultAggregate(DEFAULT_AGGREGATE);
		builder.setDefaultResolutionMS(DEFAULT_RESOLUTION);
		builder.setDescription(camelCaseToSpaceSeparated(info.getDescription()));
		StringBuilder n = new StringBuilder();
		n.append(prefix);
		n.append(' ');
		n.append(camelCaseToSpaceSeparated(info.getName()));
		String displayName = n.toString();
		builder.setDisplayName(displayName);
		builder.setDisplayNameShort(displayName.length() <= 20 ? displayName : DEFAULT_DISPLAY_NAME_SHORT);
		builder.setMetric(this.getMetricName(name, info));
		builder.setUnit(DEFAULT_UNIT);
		
		metricList.getResults().add(builder.build());                                
	}
	
	public MetricDefinitionList getMetricList() {
		return this.metricList;
	}

	@Override
	public void endAttribute() {
		// TODO Auto-generated method stub
		
	}

}
