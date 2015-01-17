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

import javax.management.MBeanAttributeInfo;
import javax.management.ObjectName;

public interface MBeanTransform <E> {
	
	public void beginDomain(String domain);
	public void endDomain();
	
	public void beginMBean(ObjectName name);
	public void endMBean();
	
	public void beginAttribute(ObjectName name,MBeanAttributeInfo attribInfo);
	public void endAttribute();
	public void setPrefix(String prefix);
	
	public E getExport();

}
