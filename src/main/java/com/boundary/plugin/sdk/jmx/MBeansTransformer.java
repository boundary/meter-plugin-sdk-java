package com.boundary.plugin.sdk.jmx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MBeansTransformer<E> {
	
	private JMXClient client;
	private MBeanTransform<E> transform;

	MBeansTransformer(JMXClient client,MBeanTransform<E> transform,String prefix) {
		this.client = client;
		this.transform = transform;
		transform.setPrefix(prefix);
	}
	
	/**
	 * Iterates over the attributes of an MBean
	 * @param name {@link ObjectName}
	 */
	private void traverseAttributes(ObjectName name) {
		MBeanServerConnection connection = this.client.getMBeanServerConnection();
		MBeanInfo info;
		HashSet<String> checkTypes = new HashSet<String>();
		checkTypes.add("long");
		checkTypes.add("int");
		checkTypes.add("javax.management.openmbean.CompositeData");
		checkTypes.add("[Ljavax.management.openmbean.CompositeData;");
		try {
			info = connection.getMBeanInfo(name);
			MBeanAttributeInfo[] attributes = info.getAttributes();
			for (MBeanAttributeInfo attrInfo : attributes) {
				if (checkTypes.contains(attrInfo.getType())) {
					transform.beginAttribute(name,attrInfo);
					transform.endAttribute();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void traverseMBeans() {
		MBeanServerConnection connection = this.client.getMBeanServerConnection();
		try {
			Set<ObjectName> mbeans = connection.queryNames(null, null);
			List<ObjectName> mbeanList = new ArrayList<ObjectName>(mbeans);
			Collections.sort(mbeanList);
			for (ObjectName obj : mbeans) {
				transform.beginMBean(obj);
				traverseAttributes(obj);
				transform.endMBean();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Transform MBeans data to a different by calling an implementation
	 * of {@link MBeanTransform}
	 */
	public void transform() {
		traverseMBeans();
	}
	
	public void convertToJson() {

		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(System.out,this.export());
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public E export() {
		E export = transform.getExport();
		return export;
	}
}
