package com.boundary.plugin.sdk;

public class CamelToUpperUnderscore {

	public static String toUpperUnderscore(String s,Character d) {
		StringBuffer buffer = new StringBuffer();

		boolean first = true;
		for (Character c : s.toCharArray()) {
			if (Character.isWhitespace(c)) {
			}
			else {
				if (Character.isUpperCase(c)) {
					if (first == false) {
						buffer.append(d);
					}
				}
				buffer.append(Character.toUpperCase(c));
			}
			first = false;
		}
		return buffer.toString();
	}

	public static void main(String[] args) {
		String s = "Thread Allocated MemorySupported";

		System.out.println(CamelToUpperUnderscore.toUpperUnderscore(s,'_'));

	}

}
