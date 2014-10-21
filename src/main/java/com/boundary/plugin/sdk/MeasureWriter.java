package com.boundary.plugin.sdk;

import java.util.Date;

public class MeasureWriter implements MeasureOutput {

	@Override
	public synchronized void send(Measure m) {
		System.out.println(m);
	}

	public static void main(String args[]) {
		MeasureWriter writer = new MeasureWriter();
		Measure m = new Measure();
		
		m.setName("BOUNDARY_CPU");
		m.setValue("3.1459");
		m.setSource("great-white-north");
		m.setTimestamp(new Date());
		
	}
}


