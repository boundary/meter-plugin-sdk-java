
package com.boundary.plugin.sdk.jmx;

import java.net.MalformedURLException;

import javax.management.remote.JMXServiceURL;

/**
 * @author davidg
 *
 */
public class JMXConnection {
	
	private String host = null;
	private int port = -1;
	private JMXServiceURL jmxServiceURL;
	private String user;
	private String password;
	
	public JMXConnection() {
		host = null;
		port = -1;
		jmxServiceURL = null;
		user = null;
		password = null;
	}
	
	void connect() {
		try {
			JMXServiceURL jmxServiceURL = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + host + ":" + port + "/jmxrmi");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
}
