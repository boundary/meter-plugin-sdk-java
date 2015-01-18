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
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import com.sun.tools.attach.spi.AttachProvider;

public class ListVirtualMachines {
	
	private static Logger LOG = LoggerFactory.getLogger(ListVirtualMachines.class);

	public ListVirtualMachines() {
	}
	
	public static void printVirtualMachineDescriptor(VirtualMachineDescriptor vmd) {
		
		System.out.println(vmd.displayName());
		AttachProvider provider = vmd.provider();
		
		try {
			VirtualMachine vm = provider.attachVirtualMachine(vmd);
			LOG.info("Virtual Machine Id: {}", vm.id());
		} catch (AttachNotSupportedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		List<VirtualMachineDescriptor> vms = VirtualMachine.list();
		
		for (VirtualMachineDescriptor vm :vms) {
			printVirtualMachineDescriptor(vm);
		}
	}
}
