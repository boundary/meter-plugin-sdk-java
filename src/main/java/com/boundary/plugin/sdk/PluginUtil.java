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

	/**
	 * Transform a camel case string into a upper case string with underscores
	 * 
	 * @param s
	 * @return
	 */
	public static String toUpperUnderscore(String s, Character d) {
		StringBuffer buffer = new StringBuffer();
		s = s.trim();

		boolean first = true;
		for (int i = 0; i < s.length(); i++) {
			if (Character.isWhitespace(s.charAt(i))) {
				buffer.append(d);
			} else {
				if (Character.isUpperCase(s.charAt(i))) {
					if (first == false) {
						if (i + 1 < s.length()
								&& Character.isLowerCase(s.charAt(i + 1))) {
							buffer.append(d);
						}
					}
				}
				buffer.append(Character.toUpperCase(s.charAt(i)));
			}
			first = false;
		}
		return buffer.toString();
	}

	public static void main(String[] args) {
		System.out.println(PluginUtil.getHostname());
	}

}
