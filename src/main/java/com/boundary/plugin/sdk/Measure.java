package com.boundary.plugin.sdk;

import java.util.Date;

public class Measure {
	
    private String name;
    private String value;
    private String source;
    private Date timestamp;
    
	public Measure() {
    	name = "";
    	value = "";
    	source = null;
    	timestamp = null;
    }
	
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(name);
		buf.append(" ");
		buf.append(value);
		if (source != null) {
			buf.append(" ");
			buf.append(source);
			if (timestamp != null) {
				buf.append(" ");
				buf.append(Long.toString(timestamp.getTime()));
			}
		}
		
		return buf.toString();
	}

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}


}

