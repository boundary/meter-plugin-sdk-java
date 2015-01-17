/**
 * 
 */
package com.boundary.plugin.sdk.jmx.extractor;

import javax.management.openmbean.CompositeDataSupport;

import com.boundary.plugin.sdk.jmx.MBeanAttribute;

/**
 * Translates a {@link CompositeData} instance to a {@link Number}
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
