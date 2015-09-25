// Copyright 2014-2015 Boundary, Inc.
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

import java.util.List;

public class MBeanEntry {
	
	private String mbean;
	private List<MBeanAttribute> attributes;
	private boolean enabled = true;
	
	public String getMbean() {
		return mbean;
	}
	public void setMbean(String mbean) {
		this.mbean = mbean;
	}
	public List<MBeanAttribute> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<MBeanAttribute> attributes) {
		this.attributes = attributes;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	@Override
	public String toString() {
		return "MBeanEntry [mbean=" + mbean + ", attributes="
				+ attributes + ", enabled=" + enabled + "]";
	}

}
