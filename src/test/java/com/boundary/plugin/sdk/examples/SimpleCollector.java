package com.boundary.plugin.sdk.examples;

import java.util.Random;

import com.boundary.plugin.sdk.Collector;
import com.boundary.plugin.sdk.Measure;
import com.boundary.plugin.sdk.MeasureOutput;
import com.boundary.plugin.sdk.MeasureOutputSupport;
import com.boundary.plugin.sdk.MeasureOutputSupport.Type;

public class SimpleCollector implements Collector {

	private String name;
	
	public SimpleCollector(String name) {
		this.name = name;
	}

	@Override
	public Measure[] getMeasures() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void run() {
		MeasureOutput output = MeasureOutputSupport.getInstance(Type.STDOUT);
		Random rand = new Random();
		
		while(true) {
			try {
				Measure m = new Measure();
				m.setName("SIMPLE_METRIC");
				m.setSource(name);

				int  n = rand.nextInt(50) + 1;
				m.setValue(Integer.toString(n));
				
				output.send(m);
				
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
