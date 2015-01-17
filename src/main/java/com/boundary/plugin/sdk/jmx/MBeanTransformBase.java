//Copyright 2014-2015 Boundary, Inc.
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//  http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.
package com.boundary.plugin.sdk.jmx;

import java.util.Hashtable;

import javax.management.MBeanAttributeInfo;
import javax.management.ObjectName;

import com.boundary.plugin.sdk.PluginUtil;

public abstract class MBeanTransformBase<E> implements MBeanTransform<E> {
	
	protected final char METRIC_NAME_SEPARATOR='.';
	protected String prefix;
	private E export;

	/**
	 * Generates a metrics name from the MBean name and MBean attribute name
	 * @param name {@link ObjectName}
	 * @param info {@link MBeanAttributeInfo}
	 * @return {@link String}
	 */
	protected String getMetricName(ObjectName name,MBeanAttributeInfo info) {
		StringBuilder builder = new StringBuilder();
		StringBuilder nameBuilder = new StringBuilder();
		Hashtable<String,String> keys = new Hashtable<String, String>(name.getKeyPropertyList());
		
		// Prefix with type and then name and remove from
		// the hash table if they exist, so that names go
		// from the general to specific
		String attrType= keys.get("type");
		String attrName = keys.get("name");
		if (attrType != null) {
			nameBuilder.append(METRIC_NAME_SEPARATOR);
			nameBuilder.append(attrType);
			keys.remove("type");
		}
		if (attrName != null) {
			nameBuilder.append(METRIC_NAME_SEPARATOR);
			nameBuilder.append(attrName);
			keys.remove("name");
		}

		for (String s : keys.values()) {
			nameBuilder.append(METRIC_NAME_SEPARATOR);
			nameBuilder.append(PluginUtil.toUpperUnderscore(s,METRIC_NAME_SEPARATOR));
		}

		builder.append(String.format("%s%s.%s",
				name.getDomain().toUpperCase(),
				nameBuilder.toString().toUpperCase(),
				PluginUtil.toUpperUnderscore(info.getName(),METRIC_NAME_SEPARATOR)));
		return builder.toString();
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public E getExport() {
		return export;
	}
}
