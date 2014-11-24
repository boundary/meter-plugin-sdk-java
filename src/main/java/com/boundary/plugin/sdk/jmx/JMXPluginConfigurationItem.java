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

public class JMXPluginConfigurationItem {
	
	private String name;
	private String connectionMethod;
	private String attachExpression;
	private String host;
	private int port;
	private String password;
	private String pollInterval;
	private String source;
	
	public JMXPluginConfigurationItem() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConnectionMethod() {
		return connectionMethod;
	}

	public void setConnectionMethod(String connectionMethod) {
		this.connectionMethod = connectionMethod;
	}

	public String getAttachExpression() {
		return attachExpression;
	}

	public void setAttachExpression(String attachExpression) {
		this.attachExpression = attachExpression;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPollInterval() {
		return pollInterval;
	}

	public void setPollInterval(String pollInterval) {
		this.pollInterval = pollInterval;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
