package com.boundary.plugin.sdk;

import java.util.Date;

/**
 * TODO: Provide for a separate thread to handle the output
 * @author davidg
 *
 */
public class MeasureWriter implements MeasureOutput {
	
	private static MeasureWriter instance = null;

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
		writer.send(m);
	}

	/**
	 * TODO: There is a better way to handle this pattern.
	 */
	@Override
	public MeasureOutput getInstance() {
		
		if (instance == null) {
			instance = new MeasureWriter();
		}

		return instance;
	}
}


