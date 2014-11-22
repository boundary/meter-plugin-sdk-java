package com.boundary.plugin.sdk.jmx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.ReflectionException;

public class ListMBeans {

	private String vmDisplayName;
	private JMXClient jmxClient;
	private MBeanServerConnection connection;
	private String[] domains;

	public ListMBeans() {
		this.jmxClient = new JMXClient();
	}
	
	private void usage() {
		System.out.println("usage: " + this.getClass() + " <virtual machine display name>");
	}
	private void handleArguments(String []args) {
		if (args.length == 1) {
			this.vmDisplayName = args[0];
		}
		else {
			usage();
			System.exit(1);
		}
	}
	
	private boolean connect() {
		boolean connected = false;
		if (jmxClient.connect(this.vmDisplayName)) {
			this.connection = jmxClient.getMBeanServerConnection();
			connected = true;
		}
		else {
			System.err.println("Unable to attach to virtual machine: " + this.vmDisplayName);
		}
		return connected;
	}
	
	private void listMBeanAttributes(ObjectName name) {
		MBeanInfo info;
		try {
			info = this.connection.getMBeanInfo(name);
			MBeanAttributeInfo [] attributes = info.getAttributes();
			for (MBeanAttributeInfo i : attributes) {
				System.out.println("\t" + i.getName() + ":" + i.getType() + ":" + i.getDescription());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void listMBeansForDomain(String domain) {
		try {
			Set<ObjectName> mbeans = this.connection.queryNames(null, null);
			List<ObjectName> mbeanList = new ArrayList<ObjectName>(mbeans);
			Collections.sort(mbeanList);
			for (ObjectName n : mbeans) {
//				String beginMBeanBanner = "+++++ mbean - " + n.getCanonicalName() + " -----";
//				String endMBeanBanner = new String(new char[beginMBeanBanner.length()]).replace("\0","=");
//				System.out.println(beginMBeanBanner);
				System.out.println(n.getCanonicalName());
				listMBeanAttributes(n);
//				System.out.println(endMBeanBanner);

				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void listMBeansInDomains() {
		for (String domain : domains) {
			String beginDomainBanner = "+++++ domain - " + domain + " -----";
			String endDomainBanner = new String(new char[beginDomainBanner.length()]).replace("\0","=");
			System.out.println(beginDomainBanner);
			listMBeansForDomain(domain);
			System.out.println(endDomainBanner);
		}
	}

	
	private void getDomains() {
		try {
			this.domains = this.connection.getDomains();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void listMBeans(String[] args) {
		handleArguments(args);
		if (this.connect()) {
			this.getDomains();
			this.listMBeansInDomains();
		}
	}

	public static void main(String []args) {
		ListMBeans lmbeans = new ListMBeans();
		lmbeans.listMBeans(args);
	}
}
