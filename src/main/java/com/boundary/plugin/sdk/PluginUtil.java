// Copyright 2014 Boundary, Inc.
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

package com.boundary.plugin.sdk;

import java.net.InetAddress;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PluginUtil {

	private static Logger LOG = LoggerFactory.getLogger(PluginUtil.class);

	/**
	 * Returns the localhost name the JVM is running on
	 * 
	 * @return {@link String} local host name
	 */
	public static String getHostname() {
		String hostname = null;
		try {
			InetAddress addr;
			addr = InetAddress.getLocalHost();
			hostname = addr.getHostName();
		} catch (Exception e) {
			LOG.info(e.toString());
		}
		return hostname;
	}

	/**
	 * Transform a camel case string into a upper case string with underscores
	 * 
	 * @param s {@String} to transform
	 * @param d {@char} replacement character for white space
	 * @return {@link String}
	 */
	public static String toUpperUnderscore(String s, Character d) {
		StringBuilder builder = new StringBuilder();
		s = s.trim();

		boolean first = true;
		for (int i = 0; i < s.length(); i++) {
			if (Character.isWhitespace(s.charAt(i))) {
				builder.append(d);
			} else {
				if (Character.isUpperCase(s.charAt(i))) {
					if (first == false) {
						if (i + 1 < s.length()
								&& Character.isLowerCase(s.charAt(i + 1))) {
							builder.append(d);
						}
					}
				}
				builder.append(Character.toUpperCase(s.charAt(i)));
			}
			first = false;
		}
		StringBuilder r = new StringBuilder();
		r.append('\\').append(d).append('\\').append(d);
		
		return builder.toString().replaceAll(r.toString(),d.toString());
	}
	
	public static String camelCaseToSpaceSeparated(String s) {
		return StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(s),' ');
	}
	
	public static String splitCamelCase(String s) {
		   return s.replaceAll(
		      String.format("%s|%s|%s",
		         "(?<=[A-Z])(?=[A-Z][a-z])",
		         "(?<=[^A-Z])(?=[A-Z])",
		         "(?<=[A-Za-z])(?=[^A-Za-z])"
		      ),
		      " "
		   );
		}

}
