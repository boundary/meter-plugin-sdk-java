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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.plugin.sdk.jmx.MBeanAttribute;

/**
 * Handles the extraction of values from MBean attributes by casting to the appropriate type
 *
 */
public class AttributeValueExtractor {
	
	
    private static Logger LOG = LoggerFactory.getLogger(AttributeValueExtractor.class);

    /**
     * 
     * @param obj MBean attribute
     * @param attr {@link MBeanAttribute}
     * @return {@link Number}
     */
	public Number getValue(Object obj,MBeanAttribute attr) throws NullPointerException {
		Number value = 0;
		
		String type = attr.getDataType();
        switch (type) {
        case "int":
            value = ((int) obj) * attr.getScale();
            break;
        case "long":
            value = ((long) obj) * attr.getScale();
            break;
        case "double":
            value = ((double) obj) * attr.getScale();
            break;
        case "float":
            value = ((float) obj) * attr.getScale();
            break;
        case "java.lang.Object":
            value = ((double) obj) * attr.getScale();
            break;
        case "javax.management.openmbean.CompositeData":
            CompositeDataSupport cData = (CompositeDataSupport)obj;
            value = ((long) cData.get(attr.getKey())) * attr.getScale();
            break;
        default:
        	LOG.error("Unknown Type");
        }
        
        return value;
    }
}
