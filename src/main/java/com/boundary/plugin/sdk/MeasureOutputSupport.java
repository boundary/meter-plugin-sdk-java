package com.boundary.plugin.sdk;

/**
 * Factory for {@link MeasureOutput} types.
 *
 */
public class MeasureOutputSupport {
	
	private static MeasureOutputStandardOut standardOut = new MeasureOutputStandardOut();
	
	public enum Output {
		STDOUT,
		API
	};
	
	public static MeasureOutput getInstance(Output out) {
		MeasureOutput instance = null;
		switch(out) {
			case STDOUT:
				instance = standardOut;
				break;
			default:
				instance = standardOut;
		}
		return instance;
	}
}
