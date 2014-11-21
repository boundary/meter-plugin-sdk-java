package com.boundary.plugin.sdk.jmx;


import java.io.IOException;
import java.util.List;

import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import com.sun.tools.attach.spi.AttachProvider;

public class ListVirtualMachines {

	public ListVirtualMachines() {
		// TODO Auto-generated constructor stub
	}
	
	public static void printVirtualMachineDescriptor(VirtualMachineDescriptor vmd) {
		
		System.out.println(vmd.displayName());
		AttachProvider provider = vmd.provider();
		
		try {
			VirtualMachine vm = provider.attachVirtualMachine(vmd);
		} catch (AttachNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch blockq
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
