package com.boundary.plugin.sdk.jmx;

import javax.management.MBeanAttributeInfo;
import javax.management.ObjectName;

public interface MBeanTransform {
	
	public void beginDomain(String domain);
	public void endDomain();
	
	public void beginMBean(ObjectName name);
	public void endMBean();
	
	public void beginAttribute(ObjectName name,MBeanAttributeInfo attribInfo);
	public void endAttribute();
	public void setPrefix(String prefix);

}
