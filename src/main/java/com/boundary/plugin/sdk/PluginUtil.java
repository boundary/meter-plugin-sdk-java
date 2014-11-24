package com.boundary.plugin.sdk;

import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PluginUtil {
	
	private static Logger LOG = LoggerFactory.getLogger(PluginUtil.class);

	public static String getHostname() {
		String hostname = null;
		try {
			InetAddress addr;
			addr = InetAddress.getLocalHost();
			hostname = addr.getHostName();
		} catch (Exception e) {
			LOG.info(e.toString());
		}
		return hostname;
	}
	
	public static void main(String []args) {
		System.out.println(PluginUtil.getHostname());
	}

}
